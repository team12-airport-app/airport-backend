# Team12 (Stephen Morrison, Chris Morrison) Airport Backend — Flight Management API

Spring Boot (Java 21) + MySQL 8 backend for the Team12 airport app.
Supports read-only “boards” (Q1–Q4 endpoints from the assignment), plus an Admin surface to manage entities. Designed to be consumed by the Vite React frontend running on port 5137.

## Stack

- Java 21 (Temurin)
- Spring Boot + Spring Data JPA + Validation
- MySQL 8.0.42 (Dockerized locally)
- Maven Wrapper (`./mvnw`)
- Postman collection for manual/runner tests
- GitHub Actions CI (build + tests on PR)

---

## Local Dev Quickstart

### Prereqs
- Java 21 (Temurin recommended)
- Docker + Docker Compose
- MySQL runs via Docker (see below)
- Postman (recommended for assignment tests)

### Environment

The app reads DB config from OS environment variables (no hard-coded creds):

DB_HOST=localhost
DB_PORT=3307
DB_NAME=flight_management
DB_USER=airport_app
DB_PASSWORD=AirportApp2025!
SPRING_PROFILES_ACTIVE=local


**macOS/zsh note:** quote the bang when exporting
```bash
export DB_PASSWORD='AirportApp2025!'

Start DB (Docker)

We map host 3307 → 3306 in the container and set the timezone to America/St_Johns.

docker compose up -d
# or: docker-compose up -d

This creates:

    DB: flight_management

    User: airport_app / AirportApp2025!

    Root: random (unused)

Run the API

# *nix
./mvnw -q clean test
./mvnw spring-boot:run

# Windows PowerShell
.\mvnw.cmd -q clean test
.\mvnw.cmd spring-boot:run

The API will be at http://localhost:8080.

CORS is enabled for http://localhost:5137 (frontend).

    Windows UTF-8 tip (fix Montréal accents in output):

    chcp 65001 > $null
    [Console]::OutputEncoding = [System.Text.Encoding]::UTF8

Postman — Collection & Runner Order

Collection file: postman/FlightManagement.postman_collection.json
It includes the original Q1–Q4 plus Admin CRUD for Airports, Airlines, and Gates.
Import

    Open Postman → Import → select the JSON file above.

    In Collection Variables, ensure:

        baseUrl = http://localhost:8080

        airportCode = YYT (can be overridden by the Airports list step)

One-Click Runner Order (copy/paste for TAs)

Run these in this exact order (once). Everything is self-contained and uses dynamic variables; no manual edits needed.

    Q1 GET /cities → Captures testCityId.

    Q2 GET /aircraft-with-passengers → Validates 200 + array.

    Q3a GET /aircraft-with-airports → Captures aircraftId.

    Q3b GET /aircraft/{{aircraftId}}/flights → Validates flights list.

    Q4 GET /passengers-with-airports → Validates 200 + array.

Airports (Admin CRUD)
6. List Airports (/manage/airports) → Sets airportCode if empty.
7. Create Airport (POST /manage/airports) → Captures createdAirportId, airportCode.
8. Get Airport by ID (GET /manage/airports/{{createdAirportId}}).
9. Update Airport (PUT /manage/airports/{{createdAirportId}}).
10. Delete Airport (DELETE /manage/airports/{{createdAirportId}}).

Airlines (Admin CRUD)
11. List Airlines (/manage/airlines).
12. Create Airline (POST /manage/airlines) → Captures createdAirlineId, createdAirlineCode.
13. Get Airline by ID (GET /manage/airlines/{{createdAirlineId}}).
14. Update Airline (PUT /manage/airlines/{{createdAirlineId}}).
15. Delete Airline (DELETE /manage/airlines/{{createdAirlineId}}).

Gates (Admin CRUD + filter)

    Important: Gate uses description (not name).
    PUT requires all fields, including airportCode.

    List Gates (/manage/gates).

    Create Gate (POST /manage/gates) → Captures createdGateId, createdGateCode (uses airportCode).

    List Gates by Airport (GET /manage/gates?airport={{airportCode}}).

    Get Gate by ID (GET /manage/gates/{{createdGateId}}).

    Update Gate (PUT /manage/gates/{{createdGateId}}).

    Delete Gate (DELETE /manage/gates/{{createdGateId}}).

Expected statuses: 200 for GET/PUT, 201 for POST (204 or 200 for DELETE).
If a step fails: ensure the API is running on http://localhost:8080 and that you haven’t skipped earlier steps that set variables.
API Surface (current + PR A additions)
Assignment/Reports (existing)

    GET /cities

    GET /aircraft-with-passengers

    GET /aircraft-with-airports

    GET /aircraft/{id}/flights

    GET /passengers-with-airports

Admin — Airports (existing)

    GET /manage/airports

    GET /manage/airports/{id}

    POST /manage/airports

    PUT /manage/airports/{id}

    DELETE /manage/airports/{id}

Airport DTOs

    Create/Update:

    { "name": "Some Airport", "code": "ABC", "cityId": 1 }

Admin — Airlines (PR A)

    GET /manage/airlines

    GET /manage/airlines/{id}

    POST /manage/airlines

    PUT /manage/airlines/{id}

    DELETE /manage/airlines/{id}

Airline DTOs

    Create/Update:

    { "code": "AC", "name": "Air Canada" }

Admin — Gates (PR A)

    GET /manage/gates

    GET /manage/gates/{id}

    GET /manage/gates?airport={code}

    POST /manage/gates

    PUT /manage/gates/{id}

    DELETE /manage/gates/{id}

Gate DTOs

    Create:

{ "code": "A7", "description": "Gate A7", "airportCode": "YYT" }

Update (PUT requires all fields):

    { "code": "A7", "description": "Gate A7 (Updated)", "airportCode": "YYT" }

Notes

    Airline code is unique (e.g., AC, WS, PD).

    Gate code is unique per airport (e.g., YYT A7, A8, …).

Profiles & Data

    local: connects to the Dockerized MySQL (env-driven).
    Seeders may add baseline demo data for cities, airports, aircraft, and flights (conditional inserts; won’t overwrite manual entries).

    test: H2 in-memory; fast unit/smoke tests.
    DataLoader is disabled in test profile so tests start with a clean schema.

CI

    GitHub Actions build & test on PR to main.

    All PRs must be from a feature branch, reviewed by the other teammate, and pass CI before merge.

Branching & Workflow

    Short-lived feature branches (e.g., feat/airlines-gates).

    Open an Issue first so it auto-adds to the Org Project Board.

    Commit style: feat(api): …, fix(db): …, chore(ci): ….

    No direct pushes to main.

Frontend Integration Notes

    Frontend runs at http://localhost:5137.

    Make sure CORS allows this origin (already configured).

Frontend consumes:

    Airports list for the switcher

    (Soon) Arrivals/Departures board endpoints

    Admin: Airports / Airlines / Gates / Flights

Troubleshooting

    CORS: If browser blocks requests from 5137, confirm allowed origin in the global CORS config.

    MySQL auth: Ensure env vars are exported in the same terminal where you run spring-boot:run.

    Windows JAVA_HOME: If needed, set it with setx or configure via Adoptium installer.

    Ports: DB 3307 on host must be free. Stop local MySQL if it conflicts.