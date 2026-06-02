<div align="center">

# 🎓 Student Management System

### Microservices-Based Student Management Platform

A distributed student management application built using Spring Boot microservices architecture. The system manages student records, course enrollment, authentication, and inter-service communication with service discovery and fault tolerance support.

<br/>

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-Microservices-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Security-000000?style=for-the-badge&logo=jsonwebtokens)
![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED?style=for-the-badge&logo=docker&logoColor=white)

</div>

---

## 📌 Repository Description

Student Management System is a microservices-based backend application developed using Spring Boot and Spring Cloud. It enables secure student registration, course management, and service-to-service communication using Eureka discovery and OpenFeign. The project demonstrates enterprise-level microservice design with resilience and containerization.

---

## ✨ Features

| Module | Description |
|---|---|
| 🔐 Authentication | Secure login and registration using JWT |
| 👨‍🎓 Student Service | Manage student records and profiles |
| 📚 Course Service | Manage courses and enrollments |
| 🔍 Service Discovery | Eureka server for dynamic service registration |
| 🔗 Inter-Service Communication | OpenFeign client |
| 🛡 Fault Tolerance | Resilience4j circuit breaker |
| 🐳 Containerization | Dockerized microservices |
| 📖 API Documentation | Swagger UI |

---

## 🛠 Tech Stack

| Layer | Technology |
|------|------------|
| Core | Java 17 |
| Microservices | Spring Boot, Spring Cloud |
| Security | Spring Security, JWT |
| Database | MySQL / PostgreSQL |
| Discovery | Eureka Server (Spring Cloud Netflix) |
| Communication | OpenFeign |
| Resilience | Resilience4j |
| DevOps | Docker, Kubernetes |
| Build | Maven |

---

## 📦 Microservices

| Service | Port | Responsibility |
|---|---|---|
| API Gateway | 8080 | Entry point for all client requests |
| Eureka Server | 8761 | Service registry |
| Auth Service | 8081 | User authentication and JWT |
| Student Service | 8082 | Student CRUD operations |
| Course Service | 8083 | Course management |
| Enrollment Service | 8084 | Student-course enrollment |

---

## 🚀 Getting Started

### Clone Repository

```bash
git clone https://github.com/your-username/student-management-system.git
cd student-management-system
```

---

### Run Services

```bash
mvn clean install
docker-compose up
```

---

## 🌐 Service URLs

| Service | URL |
|---|---|
| API Gateway | http://localhost:8080 |
| Eureka Dashboard | http://localhost:8761 |
| Auth Service Swagger | http://localhost:8081/swagger-ui.html |
| Student Service Swagger | http://localhost:8082/swagger-ui.html |

---

## 🔐 Authentication

All APIs require JWT token except login/register.

```http
Authorization: Bearer <your_token>
```

### Endpoints

#### Auth Service

```bash
POST /api/auth/register
POST /api/auth/login
```

#### Student Service

```bash
GET    /api/students
GET    /api/students/{id}
POST   /api/students
PUT    /api/students/{id}
DELETE /api/students/{id}
```

#### Course Service

```bash
GET    /api/courses
POST   /api/courses
PUT    /api/courses/{id}
DELETE /api/courses/{id}
```

---

## 🏗 Architecture

- Microservices Architecture  
- API Gateway Pattern  
- Service Discovery Pattern  
- Circuit Breaker Pattern  
- REST Communication using OpenFeign  
- Docker Containerization  

---

## 🧪 Testing

```bash
mvn test
```

Includes:

- Unit Testing  
- Service Layer Testing  
- API Testing  

---

## 📌 Future Enhancements

- Kafka event-driven communication  
- Redis caching  
- Kubernetes deployment  
- CI/CD pipeline integration  
- Monitoring with Prometheus & Grafana  

---

## 👨‍💻 Author

Developed by **Om Chaudhari** as a backend microservices project to demonstrate distributed systems, Spring Cloud, and secure API development.

---
