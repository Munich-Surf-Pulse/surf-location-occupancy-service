package com.knecht.surf_pulse_service.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "votes")
@Builder
@Getter
@Setter
public class Vote {

    @Id
    private String id;
    private SurfLocation location;
    private int count;
    private Instant timestamp;
}
