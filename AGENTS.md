# Nuke PowerPlant Monitoring System - AI Agent Guide

## Project Overview
Enterprise Spring Boot backend for **real-time nuclear power plant monitoring**. Manages reactors, sensors, incidents, maintenance, and operator training across multiple nuclear plants. Runs on Java 17, Maven, Spring Boot 2.7.18, uses H2 in-memory DB with JPA/Hibernate.

## Architecture Layers

### Domain-Driven Design per Module
Each domain (reactor, sensor, maintenance, etc.) follows a **consistent 3-layer structure**:
- **`domain/`**: JPA entities + enums (e.g., `Reactor.java`, `ReactorStatus` enum)
- **`application/`**: Controllers, DTOs, Services, Mappers, Repositories
- **Module-scoped exceptions** in `application/exception/`

**Example tree**: `reactor/` → `domain/Reactor.java`, `application/{controller,service,dto,mapper,repository}`

### Data Flow Pattern
```
Controller (@RestController) → Service → Repository (Spring Data JPA) 
                    ↓                      ↓
                  DTO/Mapper → MapStruct → Entity
```

### MapStruct Integration
- **Mappers**: Interfaces annotated with `@Mapper(componentModel = "spring")`
- **Null handling**: `nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE`
- **Cross-domain mapping**: Uses `@Mapper(uses = {OtherMapperClass.class})`
- **Field mappings**: Explicit `@Mapping(source = "...", target = "...")` for non-standard names
- **Update method pattern**: `void updateEntityFromDto(DTO dto, @MappingTarget Entity entity)`

### Key Cross-Cutting Concerns

**JPA Auditing** (`config/JpaConfig.java`):
- Enabled via `@EnableJpaAuditing(auditorAwareRef = "auditorProvider")`
- Provides automatic `createdBy`, `createdDate`, `modifiedBy`, `modifiedDate` fields

**Entity Listener** (`components/AuditEntityListener.java`):
- `@PrePersist`, `@PreUpdate`, `@PreRemove` hooks log all entity changes
- Logs to separate "AuditLogger" (configured in logback-spring.xml)
- Check `audit.log` for debugging data mutations

**Security** (JWT-based):
- `JwtUtil`: Token generation (10-hour expiry), validation, username extraction
- `SecurityConfigurer`: Stateless session, BCrypt password encoding, CSRF disabled
- `JwtRequestFilter`: Validates token on every request
- Public endpoints: `/api/auth/authenticate`, `/swagger-ui.html`, `/v3/api-docs/**`
- ALL other endpoints require valid JWT

**Exception Handling** (`exception/GlobalExceptionHandler.java`):
- Centralized `@RestControllerAdvice` catches domain-specific exceptions
- Returns appropriate HTTP status from exception class
- Add new handlers here when creating new exception types

## Critical Workflows

### Build & Test
```bash
cd backend
mvn clean install           # Full build with JaCoCo coverage
mvn spring-boot:run        # Runs on http://localhost:8080
mvn test                   # Unit/integration tests
```

### Database & SQL
- **Schema**: `src/main/resources/schema.sql` (creates views for cross-domain queries)
- **H2 Console**: http://localhost:8080/h2-console (jdbc:h2:mem:testdb)
- **Data loading**: `data.sql` loaded after schema (via `defer-datasource-initialization=true`)
- **DDL mode**: `spring.jpa.hibernate.ddl-auto=update` (auto-creates tables)

### API Documentation
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- Powered by `springdoc-openapi-ui` (auto-scans Spring annotations)
- Add `@Operation`, `@Parameter` to controllers for docs

### SonarCloud Integration
- POM configured for SonarCloud (ismahl95 organization)
- JaCoCo generates test coverage reports
- Run: `mvn clean verify sonar:sonar` (needs sonar token)

## Code Conventions & Patterns

### Lombok Usage
- `@Data` for POJO boilerplate (getters, setters, equals, toString)
- `@Builder` for complex object creation (e.g., in tests)
- **Note**: MapStruct processor must run AFTER Lombok (order in pom.xml is critical)

### DTO Design
- Use **records** where possible (Java 16+): `record ControlSystemDTO(Long id, String name, ControlSystemType type) {}`
- Include only necessary fields (not full entity graphs)
- Separate DTOs for create/update/response if different validation/fields needed

### Repository Methods
- Extend `JpaRepository<Entity, Long>` (auto-provides CRUD + pagination)
- Add custom query methods as needed: `Optional<Entity> findByNameIgnoreCase(String name)`
- Use `@Query` annotation for complex JPQL/native queries

### Service Layer
- Inject repositories + mappers
- Handle business logic, validation, exception throwing
- Throw domain-specific exceptions (e.g., `NuclearPlantException`, `SupplierException`)

### Controller Endpoints
- RESTful naming: `/api/{resource}` (e.g., `/api/reactors`, `/api/sensors`)
- Use proper HTTP verbs: `GET` (read), `POST` (create), `PUT` (update), `DELETE` (delete)
- Include `@Authenticated` (via JWT filter) on protected endpoints
- Return DTOs, not entities

## Commit Message Convention
Use semantic prefixes (from README):
- `feat:` new feature
- `fix:` bug fix
- `db:` database/schema changes
- `security:` auth/JWT/encryption changes
- `test:` test additions
- `refactor:` code restructuring
- `config:` config changes
- `docs:` documentation updates

**Example**: `feat: add reactor temperature alerting system`

## Integration Points

### Database Views
Pre-built in `schema.sql`:
- `material_supplier_view` — cross-domain material-supplier data
- `reactor_overview` — reactor+plant info
- `plant_reactor_sensor_readings` — full sensor reading hierarchy
- `maintenance_overview` — maintenance+equipment+plan+reactor hierarchy

Use these for complex queries requiring data from multiple entities.

### Module Dependencies
- **Reactor** ← Sensor, ControlSystem, Maintenance, Anomaly, Incident (owns foreign keys)
- **NuclearPlant** ← Reactor, MaintenancePlan, EmergencyPlan
- **Operator** ← Training
- **Supplier** ← Material

When modifying an entity, check if other modules depend on it.

## Debugging Tips

1. **Logs**: Check both console (DEBUG level) and `audit.log` for entity changes
2. **H2 Console**: Query database directly for data verification
3. **JWT Issues**: Check token expiry (10 hours), secret key match (`jwt.secret` in properties)
4. **Circular References**: Allowed (via `spring.main.allow-circular-references=true`), but avoid infinite recursion in mappers
5. **MapStruct Errors**: Check `target/generated-sources/annotations/` for generated mapper implementations

## Key Files Reference
- `pom.xml` — Maven dependencies, build plugins (JaCoCo, SonarCloud)
- `application.properties` — Database, JWT, logging config
- `logback-spring.xml` — Log appenders (console + audit.log file)
- `schema.sql` — Database views
- `data.sql` — Initial test data
- `NuclearPowerplantBackendApplication.java` — Spring Boot entry point

