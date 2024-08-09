package com.knecht.surf_pulse_service.dto;

import com.knecht.surf_pulse_service.models.SurfLocation;
import lombok.Getter;

@Getter
public class CreateVoteDto {

    private SurfLocation location;
    private int count;
}
