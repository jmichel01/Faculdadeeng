# TaskFlow - Agile Task Management System

TaskFlow is a robust, production-ready REST API backend designed for a fictional logistics startup. The system helps teams track and prioritize operational tasks, streamline logistics workflows, and implement Scrum practices. Built with **Spring Boot 3**, **Java 17**, and **H2 Database**, it serves as an academic showcase of Software Engineering excellence, Agile project planning, and Clean Architecture principles.

---

## 🛠️ Technology Stack

- **Core Language**: Java 17
- **Framework**: Spring Boot 3.2.5
- **Modules**: Spring Web, Spring Data JPA, Spring Validation (JSR-380)
- **Database**: In-Memory H2 Database
- **Build Tool**: Apache Maven
- **Testing**: JUnit 5, Mockito
- **CI/CD**: GitHub Actions

---

## 🚀 Agile Methodology & Kanban Board

We utilized Scrum and Kanban methodologies to manage the project lifecyle. Development was divided into incremental steps, represented on a Kanban board with three columns: **To Do**, **In Progress**, and **Done**.

### 📋 Initial Kanban Board Plan (10+ Cards)

| Task Card | Column | Description |
| :--- | :--- | :--- |
| **TF-01: Create Spring Boot Project** | Done | Setup Maven dependencies, folder structure, and properties. |
| **TF-02: Create Entity** | Done | Build the domain model (`Task`) with standard auditing. |
| **TF-03: Create Repository** | Done | Implement JPA interfaces and query methods. |
| **TF-04: Create Service Layer** | Done | Handle transactional business rules and mapping DTOs. |
| **TF-05: Create Controllers** | Done | Implement REST interfaces and map HTTP endpoints. |
| **TF-06: Implement Validation** | Done | Add JSR-380 validation rules to create/update payloads. |
| **TF-07: Create Unit Tests** | Done | Write JUnit 5 Mockito tests for services and WebMvc controllers. |
| **TF-08: Configure GitHub Actions** | Done | Configure continuous integration workflow pipeline. |
| **TF-09: Update Documentation** | In Progress | Write the technical README and academic reports. |
| **TF-10: Deploy Final Version** | To Do | Package the jar and prepare release artifacts. |

---

## 🔄 Change Management (Scope Simulation)

In the middle of the sprint, the logistics startup requested an immediate enhancement to the task entity.

### 📋 Scope Evolution
*   **Initial Scope**: A basic CRUD task system tracking task IDs, titles, descriptions, and statuses (TODO, IN_PROGRESS, DONE).
*   **New Scope**: The addition of **Task Priority** levels (`LOW`, `MEDIUM`, `HIGH`) and sorting capabilities to prioritize critical shipments.

### 🧠 Change Impact Analysis
1.  **Reason for Change**: Logistic operations suffer bottlenecks when urgent shipments (e.g. cold-chain cargo) are treated with the same priority as general shipments. Sorting by urgency is critical.
2.  **Impacted Artifacts**:
    *   **Domain Model**: Added `Priority` enum and mapped it to the `Task` entity.
    *   **Data Access**: Implemented `findAllByOrderByPriorityDesc()` query method.
    *   **Service Layer**: Updated mappings and added sorting method.
    *   **Controller**: Created `GET /api/v1/tasks/priority` endpoint.
    *   **Tests**: Updated DTO constraints and MockMvc assertions.
3.  **Kanban Updates**: Added a new task card: `TF-11: Implement Priority Filtering and Sorting`.
4.  **Business Benefits**: Faster delivery processing, reduced bottlenecks, and clear operational visibility for project managers.

---

## 🛣️ API Endpoints

The base URL is `http://localhost:8080/api/v1/tasks`.

| Method | Endpoint | Request Body | Response Status | Description |
| :--- | :--- | :--- | :--- | :--- |
| **POST** | `/` | `TaskCreateDTO` | `201 Created` | Creates a new logistics task. |
| **GET** | `/` | *None* | `200 OK` | Retrieves all tasks (optional status/priority filter). |
| **GET** | `/{id}` | *None* | `200 OK` / `404 Not Found` | Retrieves a single task by ID. |
| **GET** | `/priority` | *None* | `200 OK` | Retrieves all tasks ordered by high priority first. |
| **PUT** | `/{id}` | `TaskUpdateDTO` | `200 OK` / `404 Not Found` | Updates task details. |
| **DELETE** | `/{id}` | *None* | `204 No Content` / `404 Not Found` | Deletes a task. |

### 📝 Sample Payloads

#### Create Task Request (`POST /api/v1/tasks`)
```json
{
  "title": "Deliver Medical Cargo B",
  "description": "Temperature controlled transport from airport",
  "priority": "HIGH",
  "status": "TODO"
}
```

#### Response (`201 Created`)
```json
{
  "id": 1,
  "title": "Deliver Medical Cargo B",
  "description": "Temperature controlled transport from airport",
  "priority": "HIGH",
  "status": "TODO",
  "createdAt": "2026-05-30T10:00:00",
  "updatedAt": "2026-05-30T10:00:00"
}
```

---

## ⚙️ Setup & Execution Instructions

### Prerequisites
*   Java Development Kit (JDK) 17 or higher.
*   Maven 3.8+ installed (or configure via wrapper/IDE).

### Local Run
1. Clone the project and open the terminal in the root directory:
   ```bash
   mvn spring-boot:run
   ```
2. The server will start on port `8080`.
3. Open H2 In-Memory Database Console: `http://localhost:8080/h2-console`
   *   **JDBC URL**: `jdbc:h2:mem:taskflowdb`
   *   **User Name**: `sa`
   *   **Password**: *(leave blank)*

---

## 🧪 Testing and CI/CD

### Automated Tests
To execute unit and web slice tests:
```bash
mvn clean test
```
*   `TaskServiceTest`: Validates transaction services and mocked repository CRUD states under isolation.
*   `TaskControllerTest`: Verifies REST status codes, invalid validations (e.g. blank titles), and path parameter lookups using Spring Boot MockMvc.

### CI/CD Pipeline
Continuous Integration is configured via GitHub Actions under `.github/workflows/maven.yml`. On every `push` and `pull_request` to `master`/`main`, it spins up a runner environment, checks out the code, loads the Java 17 configuration, caches maven repositories, compiles sources, and validates that all JUnit 5 test cases pass successfully before merging.
