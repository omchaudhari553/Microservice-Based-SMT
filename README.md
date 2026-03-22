 Microservices Student Management System
📌 Project Overview

This project is a Microservices-based Student Management System built using Spring Boot and Spring Cloud.
The system is designed using real-world microservices architecture including service discovery, API gateway, authentication, circuit breaker, centralized configuration, logging, and monitoring.

This project demonstrates distributed system architecture, secure authentication, inter-service communication, and fault tolerance, making it suitable for Java Developer interviews and resume projects.

🏗️ Microservices Architecture

The system consists of the following microservices:

Service	Description
Eureka Server	Service registry for service discovery
API Gateway	Single entry point for all client requests
Config Server	Centralized configuration management
Student Service	Manage student data
Course Service	Manage course data
Enrollment Service	Manage student course enrollment
User Service	Manage users
Auth Service	Authentication and JWT token generation
🛠️ Tech Stack
Backend

Java 17

Spring Boot

Spring Cloud

Spring Security

JWT Authentication

Spring Data JPA

Hibernate

MySQL

Microservices Tools

Eureka Service Discovery

Spring Cloud Gateway

OpenFeign (Inter-service communication)

Resilience4j Circuit Breaker

Spring Cloud Config

Logging & Monitoring

Spring Cloud Sleuth

Zipkin

Prometheus

Grafana

DevOps

Docker

Docker Compose

🔄 System Flow

Client sends request to API Gateway

API Gateway validates JWT Token

Request routed to appropriate microservice

Services communicate using Feign Client

If service fails → Circuit Breaker fallback

Logs and traces sent to Zipkin

Metrics monitored using Prometheus & Grafana

📂 Project Structure
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
🔐 Authentication Flow (JWT)

User logs in via Auth Service

Auth Service validates user via User Service

JWT token generated

Token sent in request header

API Gateway validates token

Request forwarded to microservices

⚡ Features

Microservices Architecture

Service Discovery (Eureka)

API Gateway Routing

JWT Authentication

Inter-service Communication (Feign)

Circuit Breaker (Resilience4j)

Centralized Configuration

Global Exception Handling

Logging & Distributed Tracing

Monitoring with Prometheus & Grafana

Dockerized Services

Clean Architecture (Controller → Service → Repository)

🚀 How to Run the Project
1. Start Config Server
2. Start Eureka Server
3. Start All Microservices
4. Start API Gateway
5. Run using Docker Compose
docker-compose up
📡 API Endpoints (Example)
Auth Service
POST /auth/login
POST /auth/register
Student Service
GET /students
GET /students/{id}
POST /students
PUT /students/{id}
DELETE /students/{id}
Course Service
GET /courses
POST /courses
Enrollment Service
POST /enrollments
GET /enrollments/student/{id}
💼 Resume Project Description

Microservices Student Management System

Developed a microservices-based system using Spring Boot and Spring Cloud

Implemented service discovery using Eureka and API Gateway routing

Secured APIs using JWT authentication and Spring Security

Enabled inter-service communication using OpenFeign

Implemented Circuit Breaker using Resilience4j for fault tolerance

Used centralized configuration with Spring Cloud Config

Implemented logging, distributed tracing, and monitoring

Dockerized all services using Docker Compose

👨‍💻 Author

Om Chaudhari
Java Developer | Spring Boot | Microservices | AWS | Docker

⭐ Conclusion

This project demonstrates microservices architecture, security, fault tolerance, distributed tracing, and monitoring, making it a production-style backend system suitable for learning and interviews.
