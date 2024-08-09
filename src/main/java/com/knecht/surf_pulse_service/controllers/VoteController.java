package com.knecht.surf_pulse_service.controllers;

import com.knecht.surf_pulse_service.dto.CreateVoteDto;
import com.knecht.surf_pulse_service.models.Statistics;
import com.knecht.surf_pulse_service.models.SurfLocation;
import com.knecht.surf_pulse_service.models.Vote;
import com.knecht.surf_pulse_service.repositories.VoteRepository;
import com.knecht.surf_pulse_service.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private VoteService voteService;

    @PostMapping("/vote")
    public ResponseEntity<?> createVote(@RequestBody CreateVoteDto createdVote) {

        if (createdVote.getLocation() == null) {
            return ResponseEntity.badRequest().body("Surf location must be provided");
        }

        var vote = Vote.builder()
                .count(createdVote.getCount())
                .location(createdVote.getLocation())
                .timestamp(Instant.now())
                .build();

        var savedVote = voteRepository.save(vote);
        template.convertAndSend("/topic/votes/" + createdVote.getLocation(), savedVote);
        template.convertAndSend("/topic/votes", savedVote);

        return ResponseEntity.ok(savedVote);
    }

    @GetMapping("/occupancy")
    public ResponseEntity<?> getOccupancy(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end,
            @RequestParam("location") SurfLocation location
    ) {
        if (end.isBefore(start)) {
            return ResponseEntity.badRequest().body("Start date must before end date");
        }

        return ResponseEntity.ok(voteRepository.findByTimestampBetween(start, end, location));
    }

    @GetMapping("/occupancy-stats")
    public ResponseEntity<?> getOccupancyStats(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end,
            @RequestParam("location") SurfLocation location
    ) {
        if (end.isBefore(start)) {
            return ResponseEntity.badRequest().body("Start date must before end date");
        }

        return ResponseEntity.ok(voteService.getOccupancyStats(start, end, location));
    }

    @GetMapping("/statistics")
    public ResponseEntity<Statistics> getStatistics(@RequestParam(value = "location", required = false) SurfLocation location) {

        return ResponseEntity.ok(voteService.getStatistics(location));
    }
}