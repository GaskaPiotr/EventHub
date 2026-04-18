# EventHub - Modular Monolithic API

![Java](https://img.shields.io/badge/Java-25-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen.svg)
![Architecture](https://img.shields.io/badge/Architecture-Spring%20Modulith-blue.svg)

**EventHub** is a backend REST API for a tech meetup and workshop ticketing platform.

Instead of a traditional layered monolith or a premature microservices architecture, this project was intentionally designed as a **Modular Monolith** using **Domain-Driven Design (DDD)** principles and **Spring Modulith**. It demonstrates strict bounded contexts, internal API encapsulation, event-driven cross-module communication and a production-ready containerized deployment pipeline.

---

## Live Demo

The application is deployed on Render via a multi-stage Docker build, connected to a secure internal PostgreSQL database.

**Access the interactive Swagger UI [here](https://eventhub-g9u0.onrender.com/swagger-ui/index.html)**

> **Note:** As this is hosted on a free tier, it may take ~50 seconds for the container to wake from sleep on the first request.

---

## Architectural Highlights

* **Strict Encapsulation:** Modules interact exclusively through designated internal interface APIs (`CatalogInternalAPI`). Core domain logic and JPA repositories are `package-private` to prevent dependency leakage.
* **Event-Driven Architecture (EDA):** Cross-domain side effects (like processing payments and sending email notifications) are decoupled using Spring Application Events (`TicketPurchasedEvent`), preventing synchronous blocking.
* **Hexagonal Principles:** The `Gateway` module acts as the sole Web transport adapter. Web DTOs (JSON) are mapped to Domain DTOs within the Gateway, keeping the core domains completely ignorant of HTTP and REST.
* **Safe Financial Types:** Utilizes `BigDecimal` for all monetary transactions to prevent floating-point inaccuracies.
* **Architectural Testing:** Uses `spring-modulith-starter-test` and ArchUnit to statically verify that no module violates package visibility or creates circular dependencies.
* **Infrastructure as Code:** Fully Dockerized local development and production environments, with version-controlled database schemas using Flyway.

---

## Module Structure & C4 Architecture

![EventHub Architecture](docs/architecture.png)

*(Auto-generated via Spring Modulith Documenter & PlantUML)*

The application is strictly divided into 5 modules:

1. **`gateway`**: The REST Controller layer. Handles HTTP request routing, DTO translation, and global exception handling (`@RestControllerAdvice`).
2. **`catalog`**: Manages workshop inventory, descriptions, and capacity rules.
3. **`ticketing`**: The core business transaction layer. Verifies capacity with the catalog, generates tickets, and publishes domain events.
4. **`payment`**: An asynchronous event listener that simulates financial transactions.
5. **`notification`**: An asynchronous event listener that simulates email dispatch.

---

## Tech Stack

* **Java 25** (Records, modern Streams)
* **Spring Boot 4.0.5** (Web, Data JPA)
* **Spring Modulith** (Events, Architecture Verification)
* **PostgreSQL & Flyway** (Production relational database and schema migrations)
* **Docker & Docker Compose** (Multi-stage build and local infrastructure)
* **Testcontainers** (Ephemeral Docker containers for integration testing)
* **GitHub Actions** (Automated CI/CD pipeline enforcing code quality)
* **MapStruct & Lombok** (Boilerplate reduction and pure DTO mapping)
* **OpenAPI 3 / Swagger** (Interactive API documentation)

---

## Running Locally (Docker)

To run this application locally, you do not need to install PostgreSQL or Java on your machine, only **Docker** is required.

**1. Clone the repository and configure secrets**
```bash
git clone https://github.com/GaskaPiotr/EventHub.git
cd EventHub
```
Create a ```.env``` file in the root directory to hold your local database credentials:
```
DB_USERNAME=postgres
DB_PASSWORD=admin
```
**2. Boot the infrastructure and application**
```bash
docker-compose up --build
```
(Docker will download PostgreSQL, compile the Java application in a builder stage, run Flyway migrations, and start the API).

**3. Access the API**

Once you see ```Started EventHubApplication``` in the logs, navigate to http://localhost:8080/swagger-ui.html

**4. Dev Workflow Note**

If you want to actively write Java code with hot-reloading run `docker-compose up -d postgres` to start only the database in the background, and launch the Spring Boot app directly from your IDE or using `./mvnw spring-boot:run`

---

## Testing Strategy

This project uses a unified testing suite that verifies both the architectural boundaries and database integrations simultaneously.

**To run the entire test suite:**
```bash
./mvnw clean test
```
**What this command does:**

1. **Architecture Verification:** Uses ArchUnit and Spring Modulith to automatically enforce that no module violates package visibility or creates circular dependencies. (*e.g., If a developer accidentally imports a `ticketing` repository into the `catalog` module, this test will fail.*)

2. **Integration Testing:** Uses **Testcontainers** to automatically spin up a PostgreSQL Docker container, run Flyway migrations, and test cross-module event publishing.

(*Note: Because this suite uses Testcontainers, you must have the Docker Desktop running on your machine before executing the tests*)

