package com.knecht.surf_pulse_service.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DayOfWeekStats {
    private int dayOfWeek;
    private double average;
    private int max;
    private int min;
}