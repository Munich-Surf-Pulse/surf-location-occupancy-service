# Stage 1: Build the application
FROM --platform=linux/amd64 gradle:8.7-jdk17 AS build

WORKDIR /app

# Copy Gradle configuration files and project source
COPY build.gradle settings.gradle ./
COPY src ./src

# Build the application
RUN gradle clean build -x test

# Stage 2: Run the application
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=build /app/build/libs/surf-location-occupancy-service-0.0.1-SNAPSHOT.jar /app/surf-location-occupancy-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/surf-location-occupancy-service.jar"]