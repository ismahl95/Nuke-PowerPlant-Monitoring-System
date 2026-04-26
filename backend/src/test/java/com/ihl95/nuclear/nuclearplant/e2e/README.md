# NuclearPlant E2E Tests (End-to-End)

## Overview
End-to-end tests for the NuclearPlant module using **Cucumber** with **Gherkin** syntax and **RestAssured** for HTTP requests.

These tests simulate **realistic user workflows** with real HTTP calls against a running application context, verifying:
- Complete request-response cycles
- Database persistence
- Security (JWT authentication)
- Validation error handling
- API contract compliance

## Architecture

### Layer 3: E2E Tests
```
Feature File (Gherkin)
    ↓
Step Definitions (Cucumber)
    ↓
RestAssured HTTP Requests
    ↓
Real Database (H2 in-memory)
    ↓
Assertions (Behavioral validation)
```

## Files Structure

```
src/test/
├── resources/
│   └── features/
│       └── nuclearplant.feature          # Gherkin scenarios (user stories)
└── java/
    └── com/ihl95/nuclear/
        └── e2e/
            ├── NuclearPlantE2ETestSuite.java    # Test runner (Cucumber JUnit Platform)
            └── steps/
                └── NuclearPlantSteps.java        # Step definitions (Gherkin → Java)
```

## Feature File: `nuclearplant.feature`

**7 Business Scenarios:**

1. ✅ **Create a new nuclear plant** - POST with valid data
   - Expected: 201 Created, plant persisted in DB

2. ✅ **Retrieve all nuclear plants** - GET all
   - Expected: 200 OK, list of plants

3. ✅ **Retrieve a specific plant by ID** - GET /{id}
   - Expected: 200 OK, plant details

4. ✅ **Update an existing plant** - PUT /{id}
   - Expected: 200 OK, updated data persisted

5. ✅ **Delete a nuclear plant** - DELETE /{id}
   - Expected: 204 No Content, plant removed from DB

6. ✅ **Reject creation with missing name** - POST validation
   - Expected: 400 Bad Request, validation error

7. ✅ **Reject request without authentication** - No JWT
   - Expected: 403 Forbidden

## Step Definitions: `NuclearPlantSteps.java`

Each Gherkin step maps to a Java method:

### Setup Steps
- `Given the authentication server is available` → Verify server health
- `Given I have a valid JWT token` → Obtain valid JWT from auth endpoint
- `Given I do not have a valid JWT token` → Simulate unauthenticated requests

### Create Steps
- `Given I want to create a new nuclear plant` → Initialize DTO
- `When I send a POST request with plant name X and location Y` → Execute POST
- `When I send a POST request with missing name and location Y` → Execute invalid POST

### Retrieve Steps
- `Given there are nuclear plants in the system` → Seed test data
- `Given a nuclear plant exists with name X` → Create existing record
- `When I send a GET request to retrieve all plants` → Execute GET all
- `When I send a GET request for the plant by ID` → Execute GET /{id}

### Update Steps
- `When I update the plant name to X and location to Y` → Execute PUT with updates

### Delete Steps
- `When I send a DELETE request for the plant` → Execute DELETE /{id}

### Assertion Steps
- `Then the response status should be NNN` → HTTP status validation
- `Then the response should contain the plant name X` → JSON field validation
- `Then the new plant should be persisted in the database` → DB verification
- `Then the plant should be removed from the database` → DB deletion verification

## How RestAssured Works

```java
// Simple GET request with JWT
Response response = given()
    .header("Authorization", "Bearer " + jwtToken)
    .contentType("application/json")
    .when()
    .get("/api/nuclear-plants")
    .then()
    .extract()
    .response();

// JSON path extraction
String plantName = response.jsonPath().getString("name");

// Assertions
assertThat(response.getStatusCode()).isEqualTo(200);
```

## Running E2E Tests

### Option 1: Run entire E2E suite
```bash
mvn test -Dtest=NuclearPlantE2ETestSuite
```

### Option 2: Run specific feature
```bash
mvn test -Dtest=NuclearPlantE2ETestSuite -Dcucumber.filter.tags="@create"
```

### Option 3: Run all NuclearPlant tests (unit + integration + E2E)
```bash
mvn test -Dtest=NuclearPlant*
```

### Expected Output
```
Running com.ihl95.nuclear.e2e.NuclearPlantE2ETestSuite

7 scenarios (7 passed)
35 steps (35 passed)
```

## Key Differences: Unit vs Integration vs E2E

| Layer | Type | Speed | Context | Tool |
|-------|------|-------|---------|------|
| **Unit** | Fast tests | ms | No Spring | JUnit 5 + Mockito |
| **Integration** | Controller tests | seconds | Full Spring | MockMvc + @SpringBootTest |
| **E2E** | Scenario tests | seconds | Real HTTP | Cucumber + RestAssured |

## Test Data

Test data uses shared factories from `common/mocks/`:
- `NuclearPlantTestData.createNuclearPlantEntity()`
- `NuclearPlantTestData.createNuclearPlantDTO()`

Factory features:
- Supports **null IDs** for auto-generation (CREATE scenarios)
- **Reusable across all 3 test layers**
- Consistent default values

## Debugging E2E Tests

### View Cucumber HTML Report
```bash
# After running tests, open:
target/cucumber-report.html
```

### Print HTTP Requests/Responses
```java
given()
    .log().all()  // Log request
    .when()
    .get("/api/...")
    .then()
    .log().all()  // Log response
```

### Database State
```bash
# Start H2 Console while tests run:
http://localhost:8080/h2-console
# JDBC URL: jdbc:h2:mem:testdb
```

## Common Issues

### Issue: "No scenarios found"
**Cause**: Cucumber can't find feature files
**Solution**: Verify `@SelectClasspathResource("features")` points to correct directory

### Issue: "401 Unauthorized"
**Cause**: JWT token invalid or missing
**Solution**: Check `LoginRequest` credentials match test database users

### Issue: "NullPointerException on jwtToken"
**Cause**: Authentication endpoint failed
**Solution**: Mock auth in `@Before` or use static test token

## Best Practices

✅ **DO**
- Write scenarios in **business language**, not technical terms
- Test **end-to-end workflows**, not individual operations
- Verify **database state** after mutations
- Use **descriptive Gherkin steps** that non-technical people understand
- Keep steps **reusable** across multiple scenarios

❌ **DON'T**
- Test internals (use unit tests for that)
- Assume previous scenario state (each scenario should be independent)
- Mock HTTP calls (use real requests - that's the point of E2E)
- Write overly complex step definitions

## Future Enhancements

- [ ] Add scenario outlines for parameterized tests
- [ ] Add tagging system for test categorization (@smoke, @critical, @security)
- [ ] Add response time assertions
- [ ] Add API contract validation
- [ ] Generate Cucumber reports in CI/CD pipeline

---

**Testing Pyramid**: Many unit tests → Some integration tests → Few E2E tests

