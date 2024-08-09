package com.knecht.surf_pulse_service.repositories;

import com.knecht.surf_pulse_service.models.SurfLocation;
import com.knecht.surf_pulse_service.models.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.Instant;
import java.util.List;

public interface VoteRepository extends MongoRepository<Vote, String> {
    @Query("{ 'timestamp' : { $gte: ?0, $lte: ?1 }, 'location': ?2 }")
    List<Vote> findByTimestampBetween(Instant start, Instant end, SurfLocation location);

    long countByLocation(SurfLocation location);

}
