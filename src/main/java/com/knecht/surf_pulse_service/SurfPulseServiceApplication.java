package com.knecht.surf_pulse_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableMongoAuditing
@EnableMongoRepositories
public class SurfPulseServiceApplication {

	public static void main(String[] args) {
		 SpringApplication.run(SurfPulseServiceApplication.class, args);
	}
}
