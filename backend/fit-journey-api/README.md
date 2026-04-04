# Fit Journey API

API for the FitnessJourney application.

## About

FitnessJourney is a comprehensive fitness tracking and social networking platform designed to help users achieve their health goals. The application combines personalized workout and diet planning with real-time social features, allowing users to connect, share progress, and support each other on their fitness journeys. This backend API serves as the core infrastructure, providing RESTful endpoints for all application features and enabling real-time communication through WebSocket-based chat functionality.

## Project Overview

This project is a Spring Boot-based REST + WebSocket backend that provides endpoints for user management, diet and fitness features, and chat communication.

- Framework: Spring Boot 3.5.5
- Language: Java 24
- Database: PostgreSQL
- Auth: JWT + Spring Security
- Real-time communication: STOMP over WebSocket
- API documentation: Swagger / OpenAPI

Frontend:

- https://martinezhrcz.github.io/FitnessJourneyUI/

## Prerequisites

For local run:

- Java 24
- Maven 3.8+
- PostgreSQL

For Docker run:

- Docker
- Docker Compose

## Environment Variables

The backend expects the following variables (for example from a `.env` file):

- `DB_URL` (for example `jdbc:postgresql://localhost:5432/fitjourney`)
- `DB_USER`
- `DB_PASSWORD`
- `JWT_SECRET_KEY`
- `FRONTEND_URL` (for example `http://localhost:5173` or the production origin)

Docker Compose note:

- The compose file uses the `DB` variable as the database name.
- Inside the API container, `DB_URL` is automatically built as `jdbc:postgresql://db:5432/${DB}`.

## Local Run (Maven)

1. Create a `.env` file or set the variables listed above.
2. Start PostgreSQL.
3. Run the application:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Default port: `8080`

## Docker Run

1. Create a `.env` file in the project root.
2. Start with build:

```bash
docker compose up -d --build
```

3. View logs:

```bash
docker compose logs -f api
```

## WebSocket / Chat

- Endpoint: `/ws-chat`
- App destination prefix: `/app`
- User queue: `/user/queue/messages`
- Client publish example: `/app/chat.sendMessage`

Chat communication is event-driven.

## Tests

```bash
./mvnw test
```

On Windows:

```powershell
.\mvnw.cmd test
```

## License
