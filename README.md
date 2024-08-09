# Surf Location Occupancy Service

Surf Location Occupancy Service is a Spring Boot-based RESTful API that powers the backend of a river surfing occupancy monitoring application. This service handles user-generated reports, surf spot data management, and provides real-time and historical occupancy data to help surfers in Munich plan their surf sessions.

## Getting Started

### Prerequisites

To run the project locally, you'll need:

- Java Development Kit (JDK) 17 or higher
- Gradle 6.x or higher
- A MongoDB database

### Installation
Clone the repository:

```bash
git clone https://github.com/Munich-Surf-Pulse/surf-location-occupancy-service.git
```
Navigate to the project directory:

```bash
cd surf-location-occupancy-service
```

### Configure the database:

Update the [application-dev.yml](application-dev.yml) file with your database connection details:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/yourdatabase
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
```

### Build the project:

```bash
./gradlew build
```

### Run the application with dev profile:

```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```
The service will be available at http://localhost:8080.

## License
This project is licensed under the MIT License. See the [LICENSE](./LICENSE) file for details.

## Contact
If you have any questions, suggestions, or feedback, feel free to reach out:

Email: g.mahlknecht@gmail.com

Thank you for being a part of the Munich Surf Pulse community