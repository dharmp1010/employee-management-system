# Employee Management System (Full-Stack)

A full-stack web application for managing employees. The backend is a secured Spring Boot REST API, and the front end is a React interface with role-based access for Admin and Employee users.

## Project Structure
```
employee-management-system/
├── backend/    # Java 21, Spring Boot, Spring Security (JWT), PostgreSQL
└── frontend/   # React.js (Vite)
```

## Features
- Secure login using JWT authentication
- Role-based access control (Admin / Employee)
- BCrypt password hashing
- REST API endpoints for employees and leave requests
- React front end connected to the secured API
- Unit tests with JUnit 5 and Mockito

## Tech Stack
**Backend:** Java 21, Spring Boot, Spring Security (JWT), Spring Data JPA, PostgreSQL, JUnit 5, Mockito
**Frontend:** React.js, Vite, JavaScript (ES6+), HTML5, CSS3

## How to Run Locally

### 1. Backend
1. Install Java 21 and PostgreSQL, and create a database named `ems_db`.
2. Set the environment variable `DB_PASSWORD` to your PostgreSQL password.
3. Run:
```
   cd backend
   ./mvnw spring-boot:run
```
   The API starts at http://localhost:8080

### 2. Frontend
```
cd frontend
npm install
npm run dev
```
Open http://localhost:5173

## Author
Dharm Patel
LinkedIn: https://linkedin.com/in/dharm-patel-395b31281