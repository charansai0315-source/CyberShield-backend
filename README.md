# CyberShield Backend

CyberShield Backend is a Spring Boot REST API for network traffic analysis and threat management.

## 🚀 Overview

The backend service ingests network traffic flow data, coordinates with the ML threat classification engine, and persists detection logs and analytics to a MySQL database.

## 🛠️ Tech Stack

- **Java 17+**
- **Spring Boot 3** (Web, Data JPA, Validation)
- **MySQL Database**
- **Maven**
- **Docker**

## 📁 Project Structure

```text
backend/
├── src/
│   ├── main/
│   │   ├── java/        # Controller, Service, Model, Repository layers
│   │   └── resources/   # Application properties & SQL schemas
│   └── test/            # Unit & Integration tests
├── pom.xml              # Maven dependencies & build config
└── Dockerfile           # Multi-stage Docker build
```

## 🔌 API Endpoints

- `GET /api/health` - Application and model readiness check
- `GET /api/dashboard/summary` - Aggregated threat metrics and category counts
- `GET /api/traffic/recent?limit=16` - Retrieve recent traffic analyses
- `POST /api/traffic/analyze` - Ingest, classify, and persist a single network flow
- `POST /api/traffic/demo` - Seed and analyze sample traffic scenarios

## 🏃 Getting Started

### Local Build & Run
```bash
# Build with Maven
mvn clean package -DskipTests

# Run the Spring Boot application
mvn spring-boot:run
```

### Docker
```bash
docker build -t cybershield-backend .
docker run -p 8080:8080 cybershield-backend
```
