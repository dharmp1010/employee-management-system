# Employee Management System (Backend)
A backend REST API for managing employees and leave requests, built with Spring Boot. Supports JWT-based authentication and role-based access control (Admin/Employee).

## Tech Stack
- Java 21
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA
- PostgreSQL
- JUnit 5 + Mockito
- Lombok

## Features

### Authentication
- User registration and login
- JWT-based stateless authentication
- Passwords hashed using BCrypt

### Employee Management
- Admin can view/update any employee
- Employees can view/update their own profile only
- Role-based access control using Spring Security

### Leave Management
- Employees can apply for leave and view their own leave history
- Admins can view all leave requests and approve/reject them

### Other
- Global exception handling with clean error responses
- Input validation on all requests
- Unit tests for core service logic

## How to Run Locally

1. Clone the repository
2. Create a PostgreSQL database named `ems_db`
3. Update `src/main/resources/application.properties` with your database username and password
4. Run the application using `mvn spring-boot:run`
5. The API will be available at `http://localhost:8080`

## APIs

### Auth
- POST /api/auth/register — Register a new user (public)
- POST /api/auth/login — Login and get JWT token (public)

### Employee
- GET /api/employees — Get all employees (Admin only)
- GET /api/employees/{id} — Get employee by ID (Admin or self)
- PUT /api/employees/{id} — Update employee name (Admin or self)

### Leave
- POST /api/leaves — Apply for leave (any logged-in user)
- GET /api/leaves/my — View your own leave requests (any logged-in user)
- GET /api/leaves — View all leave requests (Admin only)
- PUT /api/leaves/{id}/approve — Approve a leave request (Admin only)
- PUT /api/leaves/{id}/reject — Reject a leave request (Admin only)