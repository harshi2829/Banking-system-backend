# Auth Service

Handles user registration, login, and JWT token generation for the Banking System project.

Every request to the system goes through authentication. When a user logs in, this service validates their credentials and returns a JWT token. That token is then used to access the other services.

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Security
- JWT
- MySQL 8.0
- Spring Cloud Eureka Client

## Port

8081

## Database

banking_auth

## Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | /auth/register | Register a new user |
| POST | /auth/login | Login and get JWT token |

## How to Run (with Docker)

This service is part of a larger Docker setup. Clone the main repo and follow the instructions there:
https://github.com/harshi2829/Banking-microservices-docker

## How to Run (standalone)

./mvnw spring-boot:run

Make sure MySQL is running locally and update application.properties with your database credentials.

## Part of

Banking Microservices project: https://github.com/harshi2829/Banking-microservices-docker
