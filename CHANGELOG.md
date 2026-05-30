# Changelog

All notable changes to **TaskFlow** will be documented here.

---

## [1.1.0] - 2026-05-30

### Added
- `TaskSearchController`: new endpoint `GET /api/v1/tasks/search?keyword=` for case-insensitive title search.
- `ResourceNotFoundException.forTask(Long id)`: static factory method for cleaner exception instantiation.
- `findByTitleContainingIgnoreCase` query method on `TaskRepository`.
- `IllegalArgumentException` handler in `GlobalExceptionHandler`.
- Javadoc on all public methods, interfaces, entities, and enums.

### Changed
- `TaskCreateDTO`: title now requires a minimum of 3 characters; priority and status are now `@NotNull`.
- `TaskUpdateDTO`: title now requires a minimum of 3 characters.
- `application.yml`: enabled error message details, disabled `open-in-view`, added package-level debug logging.
- Service layer now uses `ResourceNotFoundException.forTask()` factory instead of inline instantiation.

---

## [1.0.0] - 2026-05-29

### Added
- Initial project setup with Spring Boot 3.2.5 and Java 17.
- `Task` entity with `Priority` (LOW, MEDIUM, HIGH) and `Status` (TODO, IN_PROGRESS, DONE) enums.
- Full CRUD REST API at `/api/v1/tasks`.
- Priority-sorted listing endpoint `GET /api/v1/tasks/priority`.
- Input validation via JSR-380 (`@NotBlank`, `@Size`).
- Global exception handling (`GlobalExceptionHandler`) for 404, 400, and 500 responses.
- JUnit 5 unit tests for service and controller layers (12 tests, 100% passing).
- GitHub Actions CI pipeline (`.github/workflows/maven.yml`).
- H2 in-memory database with console at `/h2-console`.
