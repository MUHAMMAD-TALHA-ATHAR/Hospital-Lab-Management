# Hospital Lab Management API

A REST API built with Spring Boot for managing laboratory operations in a healthcare environment.

Patients can browse and book laboratory tests, while administrators can manage test catalogs, bookings, and reports. The system helps track the complete laboratory workflow from test booking to report generation.

I built this project to practice backend development concepts such as Spring Boot, Spring Security, JWT authentication, JPA/Hibernate, PostgreSQL, and REST API design.

---

## Features

### Authentication & Authorization

* User registration and login
* JWT-based authentication
* Role-based access control
* Protected API endpoints using Spring Security

### Laboratory Test Management

* Create laboratory tests
* View available tests
* Update test details and pricing
* Deactivate unavailable tests
* Search and browse test catalogs

### Booking Management

* Book one or multiple laboratory tests
* Generate unique booking codes
* View booking history
* Track booking status
* Calculate total booking costs

### Report Management

* Create reports for completed tests
* Store result summaries
* Attach report file paths
* Retrieve reports for specific bookings

### API Documentation

* Swagger UI integration
* OpenAPI documentation

---

## Tech Stack

| Category      | Technology                 |
| ------------- | -------------------------- |
| Language      | Java                       |
| Framework     | Spring Boot                |
| Security      | Spring Security, JWT       |
| Database      | PostgreSQL                 |
| ORM           | Spring Data JPA, Hibernate |
| Validation    | Jakarta Validation         |
| Documentation | SpringDoc OpenAPI          |
| Build Tool    | Maven                      |
| Utilities     | Lombok, DevTools           |

---

## Architecture

The project follows a layered architecture.

### Controller Layer

Handles incoming HTTP requests and API endpoints.

### Service Layer

Contains the application's business logic, such as booking creation and report management.

### Repository Layer

Communicates with the PostgreSQL database using Spring Data JPA.

### DTO Layer

Uses DTOs to separate API models from database entities.

### Security Layer

Handles authentication, authorization, and JWT validation.

### Exception Handling

Provides centralized exception handling using `@ControllerAdvice`.

---

## Project Structure

```text
src/main/java/com/java/projects/labmanagement/
├── config/
├── controller/
├── dto/
├── entity/
├── enums/
├── exception/
├── mapper/
├── repository/
├── security/
└── service/
```

---

## Database Design

### Users

Stores user credentials and roles.

### Lab Tests

Stores information about available laboratory tests and pricing.

### Bookings

Stores patient bookings and booking details.

### Booking Items

Stores individual laboratory tests linked to a booking.

### Reports

Stores test results and report information.

### Relationships

```text
User
 │
 │ 1:N
 ▼
Booking
 │
 │ 1:N
 ▼
BookingItem
 │
 │ N:1
 ▼
LabTest

BookingItem
 │
 │ 1:1
 ▼
Report
```

---

## Getting Started

### Prerequisites

* JDK 25
* PostgreSQL
* Maven (optional)

### Installation

```bash
git clone <repository-url>
cd LabManagement
```

Create a database:

```sql
CREATE DATABASE lab_management_db;
```

---

## Configuration

| Variable          | Default Value                                      |
| ----------------- | -------------------------------------------------- |
| SERVER_PORT       | 8080                                               |
| DB_URL            | jdbc:postgresql://localhost:5432/lab_management_db |
| DB_USERNAME       | postgres                                           |
| DB_PASSWORD       | your_password                                      |
| JWT_SECRET        | your_secret_key                                    |
| JWT_EXPIRATION_MS | 86400000                                           |

---

## Running the Application

```bash
./mvnw spring-boot:run
```

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

---

## API Endpoints

### Authentication

| Method | Endpoint             |
| ------ | -------------------- |
| POST   | `/api/auth/register` |
| POST   | `/api/auth/login`    |

### Users

| Method | Endpoint        |
| ------ | --------------- |
| GET    | `/api/users/me` |

### Laboratory Tests

| Method | Endpoint                     |
| ------ | ---------------------------- |
| GET    | `/api/tests/active`          |
| POST   | `/api/tests`                 |
| PUT    | `/api/tests/{id}`            |
| PATCH  | `/api/tests/{id}/deactivate` |

### Bookings

| Method | Endpoint             |
| ------ | -------------------- |
| POST   | `/api/bookings`      |
| GET    | `/api/bookings/user` |
| GET    | `/api/bookings/{id}` |

### Reports

| Method | Endpoint                         |
| ------ | -------------------------------- |
| POST   | `/api/reports`                   |
| GET    | `/api/reports/booking-item/{id}` |

---

## Sample Booking Flow

### Create Booking

```http
POST /api/bookings
```

```json
{
  "testIds": [1, 2, 3]
}
```

### Response

```json
{
  "bookingCode": "LAB-001",
  "totalAmount": 4500,
  "status": "REQUESTED"
}
```

---

## Key Concepts Learned

* Spring Boot application development
* REST API design
* Spring Security and JWT authentication
* Role-based authorization
* PostgreSQL integration
* JPA/Hibernate entity relationships
* One-to-Many and One-to-One mappings
* DTO pattern
* Exception handling
* Swagger/OpenAPI documentation
* Booking and report workflow management

---

## Future Improvements

* File upload support for reports
* Cloud storage integration (AWS S3)
* Pagination and sorting
* Search and filtering
* Email notifications
* Docker support
* Unit and integration testing

---

## Author

**Muhammad Talha Athar**

Java Backend Developer

Interested in backend development using Java, Spring Boot, PostgreSQL, MySQL, and REST APIs.

---

⭐ If you found this project useful, consider giving it a star.
