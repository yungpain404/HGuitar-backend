# HGuitar Learning Backend

> Backend API for the HGuitar guitar-learning platform. Built with Spring Boot 3 and Java 21.

[![Java 21](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/projects/jdk/21/)
[![Spring Boot 3.2.5](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Build-Maven-blue.svg)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

---

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Modules](#modules)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Environment Variables](#environment-variables)
- [API Documentation](#api-documentation)
- [Authentication & Authorization](#authentication--authorization)
- [Running Tests](#running-tests)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

---

## Overview

HGuitar Learning Backend is the server-side application for a guitar-learning website. It provides REST APIs for:

- **Authentication & Authorization** — JWT-based auth, refresh tokens, Firebase/Google sign-in
- **User Profiles** — registration, profile management, onboarding preferences
- **Learning Content** — courses, modules, lessons (localized EN/VI), lesson lifecycle (draft → published → archived)
- **Learning Progress** — track lesson completion per user with localized titles
- **Music Library** — chords and chord positions
- **Assessments** — quizzes, questions, answers, and quiz attempts
- **Content Blocks** — rich lesson content blocks (text, video, image, etc.)
- **Onboarding** — guitar type and preference selection for new users

The application follows a clean, layered architecture (presentation → application → domain → infrastructure) and uses Spring Security for endpoint protection.

---

## Architecture

The codebase is organized by **feature modules** under `com.hguitar`:

```
com.hguitar
├── auth             # Authentication, JWT, Firebase/Google sign-in
├── user             # User profiles and onboarding preferences
├── learning         # Courses, modules, lessons, localized content
├── progress         # User lesson progress tracking
├── music            # Chords and chord positions
├── assessment       # Quizzes, questions, answers, attempts
├── content          # Lesson content blocks
├── onboarding       # User onboarding flow
└── common           # Shared exceptions, domain types, web config
```

Each module follows a **hexagonal-ish layered** structure:

| Layer | Responsibility |
|-------|----------------|
| `presentation.controller` | REST controllers, request validation, Swagger docs |
| `application.service` | Use cases, orchestration, DTOs, request/response records |
| `domain.model` | Domain entities and business rules |
| `domain.repository` | Repository interfaces (ports) |
| `infrastructure.persistence` | JPA entities, repositories, mappers (adapters) |
| `infrastructure.security` | Security filters, JWT, token verification |

---

## Modules

| Module | Description |
|--------|-------------|
| `auth` | Login/register, JWT issue/refresh, Firebase/Google OAuth, password encoding |
| `user` | User profile CRUD, display name, avatar |
| `learning` | Course/module/lesson catalog with EN & VI translations, publish/archive lifecycle |
| `progress` | Mark lessons complete, retrieve user progress with localized titles |
| `music` | Chord library with fingering positions |
| `assessment` | Quizzes, multiple-choice questions, answer submission, attempt scoring |
| `content` | Lesson content blocks (text, video, image, etc.) |
| `onboarding` | Guitar type and preference selection for new users |
| `common` | Global exception handler, OpenAPI config, shared domain types |

---

## Tech Stack

| Layer | Technology | Version |
|-------|-----------|---------|
| Language | Java | 21 |
| Framework | Spring Boot | 3.2.5 |
| Build Tool | Maven | — |
| Security | Spring Security + JWT (jjwt) | 0.12.6 |
| API Docs | Springdoc OpenAPI / Swagger UI | 2.5.0 |
| Mapping | MapStruct | 1.5.5.Final |
| Boilerplate | Lombok | 1.18.32 |
| Testing | JUnit 5, Mockito, AssertJ, Spring Boot Test | — |
| Identity | Firebase / Google ID Token | — |

---

## Prerequisites

Before running the project, ensure you have:

- **JDK 21** (or newer)
- **Apache Maven 3.9+**

---

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/your-org/hguitar-backend.git
cd hguitar-backend/backend
```

### 2. Configure environment variables

Copy the example environment file and fill in your values:

```bash
cp .env.example .env
```

Edit `.env` with your JWT secret, CORS origins, and Firebase project ID.

### 3. Build the project

```bash
mvn clean install
```

### 4. Run the application

```bash
mvn spring-boot:run
```

Or run the packaged JAR:

```bash
java -jar target/guitar-learning-backend-0.0.1-SNAPSHOT.jar
```

The API will be available at `http://localhost:5501` (default).

---

## Environment Variables

Create a `.env` file (or set system environment variables) based on `.env.example`:

| Variable | Description | Default |
|----------|-------------|---------|
| `JWT_SECRET` | Secret key for signing JWTs (min 256 bits) | `changeme-...` |
| `JWT_ACCESS_EXPIRY_MS` | Access token expiry in milliseconds | `900000` (15 min) |
| `JWT_REFRESH_EXPIRY_MS` | Refresh token expiry in milliseconds | `604800000` (7 days) |
| `SERVER_PORT` | Application port | `5501` |
| `CORS_ALLOWED_ORIGINS` | Comma-separated allowed CORS origins | `http://localhost:5500,...` |
| `FIREBASE_PROJECT_ID` | Firebase project ID for Google ID token verification | *(empty)* |

> **Security:** Never commit real `.env` files. The `.env.example` is tracked, but `.env` is gitignored.

---

## API Documentation

Once the application is running, interactive API docs are available via Swagger UI:

```
http://localhost:5501/swagger-ui.html
```

OpenAPI JSON:

```
http://localhost:5501/api-docs
```

### Key Endpoints

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `POST` | `/api/v1/auth/register` | Register a new student account | Public |
| `POST` | `/api/v1/auth/login` | Login with email/password | Public |
| `POST` | `/api/v1/auth/google` | Sign in with Firebase/Google ID token | Public |
| `POST` | `/api/v1/auth/refresh` | Refresh access token | Public |
| `GET` | `/api/v1/courses` | List courses | Public |
| `GET` | `/api/v1/modules/{id}` | Get module by ID (localized) | Public |
| `GET` | `/api/v1/lessons` | List lessons | Public |
| `POST` | `/api/v1/lessons/{id}/complete` | Mark lesson as complete | Authenticated |
| `GET` | `/api/v1/me/progress` | Get current user progress | Authenticated |
| `GET` | `/api/v1/chords` | List chords | Public |
| `POST` | `/api/v1/quizzes/attempts` | Start a quiz attempt | Authenticated |
| `POST` | `/api/v1/quizzes/attempts/submit` | Submit quiz answers | Authenticated |

> Role-based access: `CONTENT_EDITOR` and `ADMIN` roles can create/update/delete lessons. `STUDENT` is the default role for registered users.

---

## Authentication & Authorization

- **JWT** access tokens (short-lived) and refresh tokens (long-lived) are issued on login.
- Protected endpoints require a valid `Authorization: Bearer <token>` header.
- Firebase/Google sign-in provisions an account on first sign-in and reuses it on subsequent sign-ins. OAuth-only accounts have no local password.
- `POST /api/v1/lessons/*/complete` requires authentication (any logged-in user).
- Lesson management endpoints (`POST/PUT/DELETE /api/v1/lessons/**`) require `CONTENT_EDITOR` or `ADMIN` roles.

---

## Running Tests

```bash
# Run all tests
mvn test

# Run tests for a specific module
mvn test -Dtest=com.hguitar.learning.**.*Test

# Generate test report
mvn surefire-report:report
```

Test suites are located under `src/test/java/com/hguitar/` and mirror the main source structure.

---

## Project Structure

```
backend/
├── .env.example
├── .gitignore
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   ├── java/com/hguitar/
│   │   │   ├── auth/
│   │   │   ├── common/
│   │   │   ├── user/
│   │   │   ├── learning/
│   │   │   ├── progress/
│   │   │   ├── music/
│   │   │   ├── assessment/
│   │   │   ├── content/
│   │   │   └── onboarding/
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       └── java/com/hguitar/
└── target/                  # Build output (gitignored)
```

---

## Contributing

1. Fork the repository.
2. Create a feature branch: `git checkout -b feature/your-feature-name`
3. Commit your changes: `git commit -m "feat: describe your change"`
4. Push to the branch: `git push origin feature/your-feature-name`
5. Open a Pull Request.

Commit message convention: `<type>: <description>` (e.g., `feat(auth): add Firebase sign-in`, `fix(learning): correct lesson sort order validation`).

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## Contact

For questions or support, please open an issue on GitHub or contact the maintainers.
