package com.knecht.surf_pulse_service.services;

import com.knecht.surf_pulse_service.models.AverageEntries;
import com.knecht.surf_pulse_service.models.DayOfWeekStats;
import com.knecht.surf_pulse_service.models.Statistics;
import com.knecht.surf_pulse_service.models.*;
import com.knecht.surf_pulse_service.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public OccupancyStats getOccupancyStats(Instant start, Instant end, SurfLocation location) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("timestamp").gte(start).lte(end).and("location").is(location)),
                group()
                        .avg("count").as("averageVote")
                        .max("count").as("maxVote")
                        .min("count").as("minVote")
                        .count().as("count")
        );

        AggregationResults<OccupancyStats> results = mongoTemplate.aggregate(aggregation, "votes", OccupancyStats.class);
        OccupancyStats result = results.getUniqueMappedResult();

        if (result != null) {
            result.setStart(start);
            result.setEnd(end);
            result.setLocation(location);
        }

        return results.getUniqueMappedResult();
    }

    public Statistics getStatistics(SurfLocation location) {
        var count = getTotalCount(location);
        var dayOfWeekStats = getDayOfWeekStatistics(location);
        var averageVotesPerDay = getAverageEntriesPerDay(location);

        return Statistics.builder()
                .totalCount(count)
                .dayOfWeekStats(dayOfWeekStats)
                .averageVotesPerDay(averageVotesPerDay)
                .build();
    }

    private long getTotalCount(SurfLocation location) {
        if (location != null) {
            return voteRepository.countByLocation(location);
        } else {
            return voteRepository.count();
        }
    }

    private List<DayOfWeekStats> getDayOfWeekStatistics(SurfLocation location) {
        List<AggregationOperation> operations = new ArrayList<>();

        if (location != null) {
            operations.add(Aggregation.match(Criteria.where("location").is(location)));
        }
        operations.add(Aggregation.project()
                .andExpression("dayOfWeek(timestamp)").as("dayOfWeek")
                .and("count").as("count"));

        operations.add(Aggregation.group("dayOfWeek")
                .avg("count").as("average")
                .max("count").as("max")
                .min("count").as("min"));

        operations.add(Aggregation.project("average", "max", "min").and("dayOfWeek").previousOperation());

        Aggregation aggregation = Aggregation.newAggregation(operations);

        AggregationResults<DayOfWeekStats> results = mongoTemplate.aggregate(aggregation, "votes", DayOfWeekStats.class);
        return results.getMappedResults();
    }

    private double getAverageEntriesPerDay(SurfLocation location) {
        List<AggregationOperation> operations = new ArrayList<>();

        if (location != null) {
            operations.add(Aggregation.match(Criteria.where("location").is(location)));
        }
        operations.add(Aggregation.project()
                .andExpression("dateToString('%Y-%m-%d', timestamp)").as("date"));

        operations.add(Aggregation.group("date")
                .count().as("entryCount"));

        operations.add(Aggregation.group()
                .avg("entryCount").as("averageEntriesPerDay"));

        Aggregation aggregation = Aggregation.newAggregation(operations);

        AggregationResults<AverageEntries> results = mongoTemplate.aggregate(aggregation, "votes", AverageEntries.class);
        return Objects.requireNonNull(results.getUniqueMappedResult()).getAverageEntriesPerDay();
    }
}