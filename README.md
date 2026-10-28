# Job Application Tracker API

A REST API for tracking job applications through every stage of a job search, from applying to receiving an offer. Each user registers, logs in with a JWT token, and manages only their own applications.

I built this to manage my own job search in Malaysia, including tracking which companies sponsor an Employment Pass.

## Features

- User registration and login with **JWT authentication** (Spring Security)
- Passwords hashed with **BCrypt**
- **Per-user data isolation**: users can only access their own applications
- Create and view job applications, and update their status as the process moves forward
- Track salary range, expected salary, offered salary and Employment Pass sponsorship
- Request validation, including custom validators (e.g. enum values, salary range)
- Consistent JSON response format with predefined error codes
- PostgreSQL running in Docker

## Tech Stack

| Area | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot, Spring Web, Spring Data JPA, Spring Security |
| Authentication | JWT (jjwt), BCrypt |
| Database | PostgreSQL 16 |
| Validation | Jakarta Bean Validation |
| Tools | Docker, Docker Compose, Maven, Lombok |

## Getting Started

### Prerequisites
- JDK 21
- Docker Desktop

### 1. Clone the repository
```bash
git clone https://github.com/aunghtookhine/job-application-tracker.git
cd job-application-tracker
```

### 2. Create your `.env` file
```bash
cp .env.example .env
```
Fill in the values. Generate a JWT secret with:
```bash
openssl rand -base64 32
```

### 3. Start PostgreSQL
```bash
docker compose up -d
```

### 4. Run the application
```bash
./mvnw spring-boot:run
```
The API runs at `http://localhost:8080`.

## Authentication

1. Register with `POST /api/auth/register`
2. Log in with `POST /api/auth/login` to receive an `accessToken`
3. Send the token with every other request:
```
Authorization: Bearer <accessToken>
```

Tokens expire after 60 minutes.

## API Endpoints

### Auth (public)
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Log in and receive a JWT |

### Job Applications (requires token)
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/applications` | List all your applications |
| GET | `/api/applications/{id}` | Get one application |
| POST | `/api/applications` | Create an application |
| PATCH | `/api/applications/{id}?status=INTERVIEW` | Update the status of an application |

**Application statuses:** `APPLIED`, `PHONE_SCREEN`, `INTERVIEW`, `OFFER`, `REJECTED`, `WITHDRAWN`

**Company countries:** `SINGAPORE`, `MALAYSIA`, `THAILAND`, `MYANMAR`

### Example: create an application
```json
POST /api/applications
{
  "companyName": "Example Sdn Bhd",
  "jobTitle": "Java Developer",
  "companyCountry": "MALAYSIA",
  "jobUrl": "https://example.com/jobs/123",
  "salaryMin": 4500,
  "salaryMax": 6000,
  "expectedSalary": 5500,
  "sponsorsEmploymentPass": true,
  "appliedDate": "2026-10-28",
  "notes": "Referred by a friend"
}
```

### Example: update the status
```
PATCH /api/applications/1?status=INTERVIEW
```

## Response Format

Every response uses the same structure.

**Success**
```json
{
  "errorCode": "0000",
  "message": "Success",
  "data": { "id": 1, "companyName": "Example Sdn Bhd", "...": "..." }
}
```

**Validation error**
```json
{
  "errorCode": "1002",
  "message": "Validation Error",
  "data": { "companyName": "must not be blank" }
}
```

**Authentication error**
```json
{
  "errorCode": "3003",
  "message": "Authentication Required"
}
```

### Error Codes
| Code | HTTP | Meaning |
|---|---|---|
| 0000 | 200 / 201 | Success |
| 1000 | 400 | General request error |
| 1001 | 400 | Invalid application ID (not found or not owned by the user) |
| 1002 | 400 | Validation error |
| 3001 | 409 | Email already registered |
| 3002 | 401 | Invalid email or password |
| 3003 | 401 | Authentication required (missing, invalid or expired token) |
| 3004 | 403 | Access denied |
| 9999 | 500 | Internal server error |

## Security Notes

- Passwords are stored as BCrypt hashes, never as plain text.
- The JWT secret and database credentials are loaded from environment variables, never committed to the repository.
- Each request's user is taken from the verified token, never from the request body or URL.
- Accessing another user's application returns the same error as a non-existent ID (`1001`), so the API does not reveal that the application exists.
- Login returns the same error for an unknown email and a wrong password, so registered emails cannot be discovered.