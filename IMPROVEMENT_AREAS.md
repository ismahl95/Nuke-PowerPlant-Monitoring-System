# Nuke PowerPlant - Areas for Improvement

## 1. Testing Strategy & Coverage

### ✅ **Current Strengths:**
- Good test organization: Unit tests (mocks) + Integration tests separated
- Comprehensive unit tests for Supplier service (401 lines, ~40 test cases)
- Test data mocks organized in parent classes (`SupplierServiceTestMocks`)
- Integration tests use real Spring context with `@SpringBootTest`, JWT token handling
- Proper use of `@ExtendWith(MockitoExtension.class)` and AssertJ fluent assertions

### 🔴 **Improvement Opportunities:**

#### A. **Incomplete Module Coverage**
- **Reactor module**: No tests found (`src/test/java/com/ihl95/nuclear/reactor/` is empty)
- **Sensor, ControlSystem, Anomaly, Incident, Equipment, Training, Report modules**: Missing entirely
- **Maintenance module**: Not tested

**Action Items:**
- Prioritize unit tests for critical modules (Reactor, Sensor, MaintenanceService)
- Use existing Supplier/NuclearPlant test templates as pattern

#### B. **Missing Controller Tests**
- Only `NuclearPlantIntegrationTest` exists
- Need `SupplierControllerIntegrationTest`, etc.
- JWT authentication should be tested for each protected endpoint

**Template to follow:**
```java
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ReactorControllerIntegrationTest {
    // Test GET, POST, PUT, DELETE with/without JWT
}
```

#### C. **Mapper Testing Gap**
- No tests for MapStruct mappers (especially cross-domain mappings)
- Update mappings (`@MappingTarget`) not tested
- Null handling strategy not verified

**Add mapper tests:**
```java
@ExtendWith(MockitoExtension.class)
class ReactorMapperTest {
    @InjectMocks
    private ReactorMapper reactorMapper;
    
    @Test
    void toReactorDTO_withValidEntity_shouldMapAllFields() { }
    
    @Test
    void updateReactorFromDto_withNullFields_shouldIgnore() { }
}
```

#### D. **Repository Query Testing**
- Custom repository methods (e.g., `findByNameIgnoreCase`) not tested
- Database view queries (`material_supplier_view`, `reactor_overview`) not validated

**Add repository tests:**
```java
@DataJpaTest
class ReactorRepositoryTest {
    @Autowired
    private ReactorRepository reactorRepository;
    
    @Test
    void findByNameIgnoreCase_shouldReturnReactor() { }
}
```

#### E. **Exception Handling Coverage**
- Exception classes created but not thoroughly tested
- Missing null exception handlers in GlobalExceptionHandler

**Current gaps:**
- `ReactorException`, `SensorException`, `MaintenanceException` not in GlobalExceptionHandler
- No test for `@RestControllerAdvice` error response format

**Action:**
```java
@Test
void handleValidationException_shouldReturnBadRequest() { }
```

---

## 2. Code Quality & Architecture

### 🔴 **Issues Found:**

#### A. **Inconsistent Repository Layer Naming**
- **Problem**: `SupplierRepository` in `infraestructure/` package (typo: "infraestructure" should be "infrastructure")
- **Impact**: Inconsistent with DDD pattern, package name misspelled

**Fix:**
```
supplier/infraestructure/ → supplier/application/repository/
nuclear/infraestructure/  → nuclear/application/repository/
```

#### B. **Missing Field Validation in DTOs**
- **Current**: DTOs use records (good!) but lack Bean Validation annotations
- **Example**: `SupplierDTO` should validate non-null fields, email format

**Add validation:**
```java
public record SupplierDTO(
    Long id,
    @NotBlank(message = "Supplier name is required")
    String name,
    @Email(message = "Contact must be valid email")
    String contact,
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone")
    String phone
) {}
```

#### C. **Incomplete Service Implementations**
- **IncidentServiceImpl**: Only returns empty object, not fully implemented
- **Other modules**: Need verification that all CRUD operations are complete

**Required for all services:**
```
- getAllEntities()
- getById(Long id)
- create(DTO dto)
- update(Long id, DTO dto)
- delete(Long id)
```

#### D. **Missing Transactional Management Details**
- All services have `@Transactional` (good for rollback on exception)
- But no `readOnly = true` on read operations (minor performance optimization)

**Better approach:**
```java
@Service
public class SupplierServiceImpl {
    
    @Transactional(readOnly = true)
    public List<SupplierDTO> getAllSuppliers() { }
    
    @Transactional
    public SupplierDTO createSupplier(SupplierDTO dto) { }
}
```

#### E. **GlobalExceptionHandler Incomplete**
- **Problem**: Only handles 3 exceptions (NuclearPlant, Supplier, Security)
- **Missing**: ReactorException, SensorException, MaintenanceException, etc.
- **No generic handler**: For unexpected `Exception` class

**Action:**
```java
@ExceptionHandler(Exception.class)
public ResponseEntity<Object> handleGenericException(Exception ex) {
    // Log and return generic error response
}
```

---

## 3. Cross-Cutting Concerns

### 🟡 **Potential Issues:**

#### A. **Logging Strategy**
- **Current**: Only audit log + console (DEBUG level)
- **Missing**: Business-level structured logging for tracking operations
- **Gap**: No correlation IDs for request tracing across services

**Recommendation**: Add MDC (Mapped Diagnostic Context) for tracing:
```java
// In filter or AOP
MDC.put("requestId", UUID.randomUUID().toString());
```

#### B. **Validation Framework Gap**
- **Utils.validateFields()** used but not consistently
- Controllers should validate DTOs with `@Valid` instead

**Current (suboptimal):**
```java
// In Utils
public static ResponseEntity<Object> validateFields(BindingResult result) { }
```

**Better approach:**
```java
@PostMapping
public ResponseEntity<SupplierDTO> create(@Valid @RequestBody SupplierDTO dto) {
    // Validation happens automatically via @Valid
}
```

#### C. **Missing Pagination in List Operations**
- Services return `List<T>` instead of `Page<T>`
- No sorting/filtering by field
- Risk of performance issues with large datasets

**Improve:**
```java
Page<SupplierDTO> getAllSuppliers(Pageable pageable);
// Usage: /api/suppliers?page=0&size=10&sort=name,asc
```

---

## 4. Documentation & Comments

### 🟡 **Gaps:**

- **No controller documentation**: Missing `@Operation`, `@Parameter` in some endpoints
- **Service comments**: Business logic not documented
- **Database views**: Not explained in code comments

---

## 5. Performance & Database

### 🟡 **Opportunities:**

#### A. **N+1 Query Problem**
- Entity relationships not using `@Lazy` vs `@Eager` fetch strategies
- Example: Fetching reactor with all sensors might cause multiple queries

**Review needed:**
- Reactor entity relationships with Sensor, ControlSystem, Maintenance
- Use `@Fetch(FetchMode.JOIN)` or `@Query` with `LEFT JOIN FETCH` for optimization

#### B. **Missing Database Indexes**
- Views exist but no indexes on foreign keys
- Composite keys for performance queries

---

## 6. Security Enhancements

### 🟢 **Good**: JWT + BCrypt implemented correctly

### 🟡 **Minor Improvements:**

1. **Token Refresh Mechanism**: Currently only 10-hour expiry, no refresh token
2. **Rate Limiting**: No protection against brute force authentication
3. **CORS**: Not configured (might be needed for frontend)
4. **Audit Trail**: Who performed what action (createdBy, modifiedBy tracked but not audited to dedicated log)

---

## Priority Roadmap

### **Phase 1 (Critical - Week 1)**
- [ ] Add mapper unit tests for all modules
- [ ] Complete IncidentServiceImpl and other stubs
- [ ] Add validation annotations to all DTOs
- [ ] Complete GlobalExceptionHandler with all exception types

### **Phase 2 (High - Week 2-3)**
- [ ] Add unit tests for Reactor, Sensor, Maintenance modules
- [ ] Add integration tests for all controllers
- [ ] Fix repository package naming (infrastructure typo)
- [ ] Implement pagination in list endpoints

### **Phase 3 (Medium - Week 4)**
- [ ] Add repository query tests (@DataJpaTest)
- [ ] Implement readOnly = true for read operations
- [ ] Add structured logging with correlation IDs
- [ ] Review and optimize N+1 queries

### **Phase 4 (Nice-to-have)**
- [ ] Add refresh token mechanism
- [ ] Configure CORS
- [ ] Implement rate limiting
- [ ] Database performance optimization

---

## Testing Metrics to Track

```
✅ Current: ~50 unit tests (Supplier + NuclearPlant)
📊 Target: 
  - Code coverage: 80%+ for service layer
  - Unit test ratio: 1 test per service method
  - Integration test ratio: 1 test per controller endpoint
```

---

## Quick Win Checklist

- [ ] Rename `infraestructure` → `infrastructure` (package & code)
- [ ] Add `@NotBlank`, `@Email`, `@Pattern` to DTOs
- [ ] Complete all service implementations (remove stubs)
- [ ] Add 10 exception handler types to GlobalExceptionHandler
- [ ] Add `readOnly = true` to read operation services

These changes will take ~3-5 hours and significantly improve code robustness.

