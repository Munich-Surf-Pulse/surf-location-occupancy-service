package com.knecht.surf_pulse_service.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AverageEntries {
    private double averageEntriesPerDay;
}