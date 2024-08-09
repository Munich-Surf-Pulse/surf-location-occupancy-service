package com.knecht.surf_pulse_service.controllers;

import com.knecht.surf_pulse_service.models.Vote;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/vote")
    @SendTo("/topic/votes")
    public Vote sendVote(Vote vote) {
        return vote;
    }
}
