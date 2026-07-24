![CI](https://github.com/harshi2829/Banking-system-backend/actions/workflows/ci.yml/badge.svg)

# Auth Service

Handles user registration, login, and JWT token generation for the Banking System project.

Every request to the system goes through authentication. When a user registers, their password is encoded using BCrypt — never stored as plain text. When a user logs in, this service validates their credentials and returns a JWT token. That token must be sent with every future request to access the other services.

## Live Deployment

Base URL: https://banking-system-backend-5tz8.onrender.com

Swagger UI: https://banking-system-backend-5tz8.onrender.com/swagger-ui/index.html

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Security
- JWT (JSON Web Token)
- BCrypt Password Encoding
- MySQL 8.0
- Spring Cloud Eureka Client
- Docker
- GitHub Actions CI

## Port

8081

## Database

banking_auth

## Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | /auth/register | Register a new user |
| POST | /auth/login | Login and get JWT token |

## How JWT Works

1. User registers with username and password
2. Password is encoded with BCrypt and saved in database
3. User logs in — Auth Service verifies credentials
4. If valid, a JWT token is generated and returned
5. User sends this token in the header of every future request
6. JWT Filter validates the token before any request is processed

## Testing

Unit tests written using JUnit 5 and Mockito. Repositories are mocked so tests run without a real database connection.

Run tests with:
```bash
mvn clean test
```

## CI/CD

GitHub Actions automatically runs all unit tests on every push to main. If any test fails, the build fails and the broken code is flagged immediately.

## How to Run (with Docker)

This service is part of a larger Docker setup. Clone the main repo and follow the instructions there:

https://github.com/harshi2829/Banking-microservices-docker

## How to Run (standalone)

```bash
./mvnw spring-boot:run
```

Make sure MySQL is running locally and update application.properties with your database credentials.

## Part of

Banking Microservices project: https://github.com/harshi2829/Banking-microservices-docker
