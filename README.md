# Event Booking API

A RESTful API built with Spring Boot for managing events and seat bookings. The system allows organisers to create and manage events, and users to book seats, with real-time seat availability tracking and booking conflict prevention.

---

## Technology Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.x |
| Database | H2 (in-memory, embedded) |
| ORM | Spring Data JPA (Hibernate) |
| Validation | Jakarta Bean Validation |
| API Documentation | Springdoc OpenAPI (Swagger UI) |
| Build Tool | Maven |
| Date Serialisation | Jackson `@JsonFormat` |

---

## Project Structure

```
src/
├── main/
│   ├── java/com/yourpackage/
│   │   ├── controller/        # REST controllers
│   │   ├── service/           # Business logic
│   │   ├── repository/        # JPA repositories
│   │   ├── model/             # JPA entities
│   │   ├── dto/               # Request and response DTOs (records)
│   │   └── exception/         # Global exception handling
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/yourpackage/
```

---

## Prerequisites

Before running the application, ensure you have the following installed:

- **Java 21** or higher
- **Maven 3.9+**

No external database installation is required. The application uses an embedded H2 in-memory database that starts automatically with the application.

---

## How to Build the Project

**1. Clone the repository**

```bash
git clone https://github.com/yourusername/event-booking-api.git
cd event-booking-api
```

**2. Database configuration**

No setup needed. H2 is embedded and configured out of the box. The relevant properties in `src/main/resources/application.properties` are:

```properties
spring.datasource.url=jdbc:h2:mem:eventbookingdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

**3. Build the project**

```bash
mvn clean install
```

To skip tests during the build:

```bash
mvn clean install -DskipTests
```

---

## How to Run the Application

**Option 1 — Maven (recommended)**

```bash
mvn spring-boot:run
```

**Option 2 — JAR file**

```bash
java -jar target/event-booking-api-0.0.1-SNAPSHOT.jar
```

The application starts on **`http://localhost:8080`** by default.

To run on a different port, update `application.properties`:

```properties
server.port=8081
```

---

## How to Access Swagger UI

Once the application is running, open your browser and navigate to:

```
http://localhost:8080/swagger-ui/index.html
```

The OpenAPI JSON spec is available at:

```
http://localhost:8080/v3/api-docs
```

Swagger UI provides a full interactive interface to explore and test all endpoints without needing Postman.

---

## H2 Console

The H2 browser console is available while the application is running. Use it to inspect tables and run SQL queries directly against the in-memory database.

```
http://localhost:8080/h2-console
```

**Connection settings:**

| Field | Value |
|---|---|
| JDBC URL | `jdbc:h2:mem:eventbookingdb` |
| Username | `sa` |
| Password | *(leave blank)* |

> Note: All data is lost when the application stops. H2 is for development and testing only.

---

## API Endpoints

### Events

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/events` | Create a new event |
| `GET` | `/events` | Get all events |
| `GET` | `/events/{id}` | Get a single event by ID |


### Bookings

| Method | Endpoint                | Description                |
|---|-------------------------|----------------------------|
| `POST` | `/events/{id}/bookings` | Book a seat for an event   |
| `GET` | `/events/{id}/bookings` | Get a bookings by event ID |
| `DELETE` | `/bookings/{id}`        | Cancel a booking           |

---

## Sample Request — Create Event

**`POST /api/events`**

```json
{
  "title": "Tech Conference Lagos 2026",
  "description": "Annual technology conference bringing together developers across Nigeria",
  "date": "2026-08-15 10:00:00",
  "venue": "Eko Hotel & Suites, Victoria Island, Lagos",
  "totalSeats": 250
}
```

> Date must be in `yyyy-MM-dd HH:mm:ss` format and must be a future date.

---

## Validation Rules

| Field | Rule |
|---|---|
| `title` | Required, cannot be blank |
| `description` | Optional |
| `date` | Required, must be a future date, format `yyyy-MM-dd HH:mm:ss` |
| `venue` | Required, cannot be blank |
| `totalSeats` | Required, must be a positive integer greater than zero |

Validation errors return a `400 Bad Request` with a structured error body indicating which fields failed and why.

---

## Assumptions and Design Decisions

**H2 in-memory database**
H2 was chosen to eliminate any external database dependency, making the project straightforward to run without any local setup. The schema is created fresh on every startup via `ddl-auto=create-drop`. For a production environment, this would be replaced with PostgreSQL and a migration tool such as Flyway.

**Date format**
All dates are accepted and returned in `yyyy-MM-dd HH:mm:ss` format using Jackson's `@JsonFormat` annotation. This was chosen for readability over ISO 8601 with timezone offsets.

**Seat availability**
Available seats are calculated at query time as `totalSeats - confirmedBookings`. This avoids maintaining a separate counter that could drift out of sync. At the scale this API targets, this is the safer trade-off.

**Booking conflicts**
A user cannot book the same event twice. This is enforced at the service layer before any database write, returning a `409 Conflict` response if a duplicate booking is attempted.

**No authentication**
Authentication and user management are out of scope for this version. All endpoints are publicly accessible. Role-based access control (organiser vs attendee) is a planned addition for a future iteration.

**DTOs as Java Records**
Request DTOs use Java records with Bean Validation annotations. Records are immutable by design, which prevents accidental mutation of request data as it moves through the service layer.

**Global exception handling**
A `@ControllerAdvice` handles all validation exceptions (`MethodArgumentNotValidException`) and domain exceptions uniformly, returning a consistent JSON error structure across all endpoints.

---

## Running Tests

```bash
mvn test
```

---

## Author

Adedamola — [github.com/tylher](https://github.com/tylher)
