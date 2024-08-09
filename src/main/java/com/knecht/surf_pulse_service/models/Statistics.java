package com.knecht.surf_pulse_service.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Statistics {
    private long totalCount;
    private List<DayOfWeekStats> dayOfWeekStats;
    private double averageVotesPerDay;
}
