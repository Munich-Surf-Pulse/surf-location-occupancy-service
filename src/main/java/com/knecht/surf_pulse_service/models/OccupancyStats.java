package com.knecht.surf_pulse_service.models;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class OccupancyStats {
    private Instant start;
    private Instant end;
    private SurfLocation location;
    private double averageVote;
    private int maxVote;
    private int minVote;
    private int count;
}