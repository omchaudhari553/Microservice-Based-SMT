# 🎓 Student Management System (Spring Boot Microservices)

<!-- BADGES START -->
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-Microservices-green?style=for-the-badge)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-6DB33F?style=for-the-badge)
![JWT](https://img.shields.io/badge/JWT-Authentication-orange?style=for-the-badge)
![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Eureka](https://img.shields.io/badge/Eureka-Service%20Discovery-blue?style=for-the-badge)
![Feign](https://img.shields.io/badge/OpenFeign-InterService-red?style=for-the-badge)
![Resilience4j](https://img.shields.io/badge/Circuit%20Breaker-Resilience4j-yellow?style=for-the-badge)
![Docker](https://img.shields.io/badge/Docker-Containerization-2496ED?style=for-the-badge&logo=docker&logoColor=white)
<!-- BADGES END -->

---

# 📘 Project Overview

The **Student Management System** is a scalable backend application built using **Java + Spring Boot Microservices** with a clean layered architecture.  
This project demonstrates **service discovery, API gateway routing, inter-service communication, circuit breaker, centralized configuration, authentication, logging, and monitoring**.

This system simulates a **real-world distributed microservices architecture** and showcases **secure authentication, fault tolerance, and service communication**, making it suitable for **Java Developer interviews and resume projects**.

---

# 🧰 Technologies Used

## Backend
- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- REST APIs

## Microservices & Cloud
- Spring Cloud
- Eureka Service Discovery
- Spring Cloud Gateway
- OpenFeign (Inter-service communication)
- Resilience4j Circuit Breaker
- Spring Cloud Config Server

## Security
- Spring Security
- JWT Authentication
- Role-Based Access Control (ADMIN / STUDENT)

## Database
- MySQL

## Logging & Monitoring
- Spring Cloud Sleuth
- Zipkin
- Prometheus
- Grafana

## DevOps
- Docker
- Docker Compose

---

# 🎯 Key Highlights

- Built Microservices Architecture using Spring Boot & Spring Cloud
- Implemented Service Discovery using Eureka
- Configured API Gateway for routing
- Implemented JWT-based authentication and role-based authorization
- Used OpenFeign for inter-service communication
- Implemented Circuit Breaker using Resilience4j
- Used Centralized Configuration using Config Server
- Implemented Global Exception Handling
- Added Logging, Distributed Tracing, and Monitoring
- Dockerized all services using Docker Compose

---

# 🧩 Microservices / Modules

1. Eureka Server – Service Registry
2. API Gateway – Routing & Security
3. Config Server – Centralized Configuration
4. Student Service – Manage Students
5. Course Service – Manage Courses
6. Enrollment Service – Student Course Enrollment
7. User Service – Manage Users
8. Auth Service – Authentication & JWT
9. Global Exception Handler
10. Logging & Monitoring

---

# 👤 User Service

Handles user management and roles.

## Features
- Create User
- Get User
- Update User
- Delete User
- Role management (ADMIN / STUDENT)
- Password encryption using BCrypt

---

# 🔐 Auth Service (JWT Authentication)

Handles authentication and JWT token generation.

## Features
- Login API
- JWT Token Generation
- Token Validation
- Role-Based Authorization
- Secure APIs via API Gateway

---

# 🎓 Student Service

Manages student information.

## Features
- Add Student
- Update Student
- Delete Student
- Get Student Details
- Fetch Student with Enrolled Courses (Feign Client)

---

# 📚 Course Service

Manages course information.

## Features
- Add Course
- Update Course
- Delete Course
- Get Course Details

---

# 📝 Enrollment Service

Manages student course enrollment.

## Features
- Enroll Student in Course
- Get Student Enrollments
- Communicates with Student & Course services via Feign Client
- Circuit Breaker fallback if service unavailable

---

# 🔄 Microservices Communication Flow

1. Client sends request → API Gateway  
2. API Gateway validates JWT Token  
3. Request routed to respective microservice  
4. Services communicate using Feign Client  
5. If service fails → Circuit Breaker fallback  
6. Logs and traces sent to Zipkin  
7. Metrics monitored via Prometheus & Grafana  

---

# ⚡ Microservices Components

| Component | Purpose |
|----------|---------|
| Eureka Server | Service discovery |
| API Gateway | Routing and security |
| Config Server | Centralized configuration |
| Feign Client | Inter-service communication |
| Circuit Breaker | Fault tolerance |
| JWT | Authentication |
| Sleuth + Zipkin | Distributed tracing |
| Prometheus + Grafana | Monitoring |
| Docker | Containerization |

---

# 📂 Project Structure
student-management-microservices
│
├── eureka-server
├── api-gateway
├── config-server
├── student-service
├── course-service
├── enrollment-service
├── user-service
├── auth-service
├── docker-compose.yml
└── README.md

# 🚀 How to Run the Project

## Step 1 – Start Config Server
## Step 2 – Start Eureka Server
## Step 3 – Start All Microservices
## Step 4 – Start API Gateway
## Step 5 – Run Docker Compose

docker-compose up

---

# 📡 Sample API Endpoints

## Auth Service
POST /auth/login
POST /auth/register


## Student Service

GET /students
GET /students/{id}
POST /students
PUT /students/{id}
DELETE /students/{id}


## Course Service

GET /courses
POST /courses


## Enrollment Service

POST /enrollments
GET /enrollments/student/{id}


---

# ✅ Validation & Exception Handling

- Request validation using annotations
- Global exception handling using @ControllerAdvice
- Standard error response structure across all services

---


# 🙏 Thank You

Thank you for exploring this Student Management System Microservices Project.  
This project demonstrates Microservices Architecture, Security, Distributed Systems, and Fault Tolerance, making it a production-style backend project for learning and interviews.

Happy Coding! 🚀
