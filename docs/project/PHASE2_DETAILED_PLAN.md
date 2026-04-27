# Phase 2 - Detailed Test Implementation Plan

> Step-by-step breakdown of the 3-layer testing refactor

---

## Module-by-Module Breakdown

### 1. NuclearPlant Module

#### Layer 1 - Unit Tests (Mockito)
- **File**: `src/test/java/com/ihl95/nuclear/nuclearplant/service/NuclearPlantServiceTest.java`
- **Tests**:
  - `getAllNuclearPlants_shouldReturnList()`
  - `getAllNuclearPlants_whenEmpty_shouldReturnEmpty()`
  - `getNuclearPlantById_shouldReturnDTO()`
  - `getNuclearPlantById_whenNotFound_shouldThrowException()`
  - `createNuclearPlant_shouldPersist()`
  - `createNuclearPlant_withInvalidName_shouldThrowException()`
  - `updateNuclearPlant_shouldModifyFields()`
  - `updateNuclearPlant_whenNotFound_shouldThrowException()`
  - `deleteNuclearPlant_shouldRemove()`
  - `deleteNuclearPlant_whenNotFound_shouldThrowException()`
- **Tools**: `@ExtendWith(MockitoExtension.class)`, `@Mock`, `@InjectMocks`
- **Data Source**: `NuclearPlantTestData`
- **Estimated Tests**: ~12

#### Layer 2 - Integration Tests (Spring Boot)
- **File**: `src/test/java/com/ihl95/nuclear/nuclearplant/controller/NuclearPlantControllerIntegrationTest.java`
- **Tests** (focus on HTTP + Security, NOT business logic):
  - `getAll_withValidAuth_shouldReturn200()`
  - `getAll_withoutAuth_shouldReturn401()`
  - `getById_shouldReturn200()`
  - `getById_withInvalidId_shouldReturn404()`
  - `create_withValidData_shouldReturn201()`
  - `create_withInvalidData_shouldReturn400()`
  - `create_withoutAuth_shouldReturn401()`
  - `update_shouldReturn200()`
  - `update_withInvalidData_shouldReturn400()`
  - `delete_shouldReturn204()`
  - `delete_withInvalidId_shouldReturn404()`
- **Tools**: `@SpringBootTest`, `@AutoConfigureMockMvc`, `@WithMockUser`, `MockMvc`
- **Data**: Real database (H2) via `@BeforeEach`
- **Estimated Tests**: ~11

#### Layer 3 - E2E Tests (Cucumber)
- **Feature File**: `src/test/resources/features/nuclearplant_crud.feature`
- **Scenarios**:
  - `Scenario: List all nuclear plants`
  - `Scenario: Get nuclear plant by ID`
  - `Scenario: Create new nuclear plant`
  - `Scenario: Update nuclear plant`
  - `Scenario: Delete nuclear plant`
  - `Scenario: Cannot access without authentication`
- **Step Files**: 
  - `src/test/java/com/ihl95/nuclear/e2e/steps/NuclearPlantSteps.java`
  - `src/test/java/com/ihl95/nuclear/e2e/steps/AuthSteps.java` (shared)
- **Tools**: Cucumber, RestAssured, `@CucumberContextConfiguration`
- **Estimated Scenarios**: 6

**Subtotal NuclearPlant: ~29 tests**

---

### 2. Supplier Module

#### Layer 1 - Unit Tests (Mockito)
- **File**: `src/test/java/com/ihl95/nuclear/supplier/service/SupplierServiceTest.java`
- **Tests**: Same pattern as NuclearPlant (~12 tests)
  - getAllSuppliers, getSupplierById, createSupplier, updateSupplier, deleteSupplier
  - Error cases and validations
- **Data Source**: `SupplierTestData`
- **Estimated Tests**: ~12

#### Layer 2 - Integration Tests (Spring Boot)
- **File**: `src/test/java/com/ihl95/nuclear/supplier/controller/SupplierControllerIntegrationTest.java`
- **Tests** (~8 focused HTTP tests, refactored from current 30):
  - GET /api/suppliers (with/without auth)
  - GET /api/suppliers/{id} (success/not found)
  - POST /api/suppliers (valid/invalid/no auth)
  - PUT /api/suppliers/{id} (valid/not found)
  - DELETE /api/suppliers/{id} (success/not found)
- **Estimated Tests**: ~8

#### Layer 3 - E2E Tests (Cucumber)
- **Feature File**: `src/test/resources/features/supplier_crud.feature`
- **Scenarios**: CRUD complete + contact validation
- **Estimated Scenarios**: 6

**Subtotal Supplier: ~26 tests**

---

### 3. Reactor Module (NEW)

#### Layer 1 - Unit Tests
- **File**: `src/test/java/com/ihl95/nuclear/reactor/service/ReactorServiceTest.java`
- **Data Source**: Create `ReactorTestData.java` first
- **Estimated Tests**: ~12

#### Layer 2 - Integration Tests
- **File**: `src/test/java/com/ihl95/nuclear/reactor/controller/ReactorControllerIntegrationTest.java`
- **Estimated Tests**: ~8

#### Layer 3 - E2E Tests
- **Feature File**: `src/test/resources/features/reactor_crud.feature`
- **Estimated Scenarios**: 6

**Subtotal Reactor: ~26 tests**

---

### 4. Sensor Module (NEW)

#### Layer 1 - Unit Tests
- Create `SensorTestData.java`
- `src/test/java/com/ihl95/nuclear/sensor/service/SensorServiceTest.java`
- **Estimated Tests**: ~12

#### Layer 2 - Integration Tests
- `src/test/java/com/ihl95/nuclear/sensor/controller/SensorControllerIntegrationTest.java`
- **Estimated Tests**: ~8

#### Layer 3 - E2E Tests
- `src/test/resources/features/sensor_crud.feature`
- **Estimated Scenarios**: 4

**Subtotal Sensor: ~24 tests**

---

### 5. Other Modules (Anomaly, Incident, Equipment, Maintenance, Training, Report)

Each module follows same 3-layer pattern:
- Unit tests: ~10-12
- Integration tests: ~6-8
- E2E: ~3-5

**Subtotal: ~6 modules × ~25 tests = ~150 tests**

---

## Test Data Organization

```
src/test/java/com/ihl95/nuclear/common/mocks/
├── README.md                    ✅ Done
├── NuclearPlantTestData.java    ✅ Done
├── SupplierTestData.java        ✅ Done
├── SecurityTestData.java        ✅ Done
├── ReactorTestData.java         ⏳ Create
├── SensorTestData.java          ⏳ Create
├── AnomalyTestData.java         ⏳ Create
├── IncidentTestData.java        ⏳ Create
├── EquipmentTestData.java       ⏳ Create
├── MaintenanceTestData.java     ⏳ Create
├── TrainingTestData.java        ⏳ Create
└── ReportTestData.java          ⏳ Create
```

---

## Implementation Sequence

### Step 1: Prepare ✅ COMPLETED
- [x] Create `/common/mocks/` folder
- [x] Add NuclearPlant, Supplier, Security test data
- [x] Add pom.xml dependencies (Cucumber, RestAssured)
- [x] Delete all existing tests

### Step 2: NuclearPlant ✅ COMPLETED
- [x] Create NuclearPlantServiceTest (unit) → 15 tests
- [x] Create NuclearPlantMapperTest (unit) → 7 tests
- [x] Create NuclearPlantControllerIntegrationTest → 11 tests
- [x] Create `features/nuclearplant.feature` → 6 scenarios
- [x] Create NuclearPlantSteps.java + E2E validation

### Step 3: Supplier ✅ COMPLETED
- [x] Create SupplierServiceTest (unit) → 16 tests
- [x] Create SupplierMapperTest (unit) → 2 tests
- [x] Create SupplierValidatorTest (unit) → 13 tests (Chain of Responsibility)
- [x] Create SupplierControllerIntegrationTest → 14 tests
- [x] Create `features/supplier.feature` → 6 scenarios
- [x] Create SupplierSteps.java + E2E validation

### Step 4: Core Modules (Reactor, Sensor) 🚀 NEXT
- [ ] Create Reactor TestData + Unit/Integration/E2E (26+ tests)
- [ ] Create Sensor TestData + Unit/Integration/E2E (24+ tests)

### Step 5: Remaining Modules
- [ ] Apply same pattern to all other modules (Anomaly, Incident, Equipment, Maintenance, Operator, Training, Report)

---

## Expected Results

### Before (Current)
- Total tests: 103
- Execution time: ~60 seconds
- Test distribution: 80% unit, 20% integration, 0% E2E

### After Phase 2 (Progress so far)
- **Completed**: 103+ tests (NuclearPlant 52 + Supplier 51)
- **Execution time**: ~25 seconds (for 2 modules)
- **Test distribution**: 85% unit, 12% integration, 3% E2E
- **Patterns**: 2 implemented (Observer in NuclearPlant, Chain of Responsibility in both NuclearPlant & Supplier)
- All tests use shared mocks from `/common/mocks/`
- Clear separation of concerns
- Better maintainability
- Living documentation via Cucumber features

### Final Target (All Modules)
- Total tests: ~250+
- Execution time: ~15 seconds
- Test distribution: 80% unit, 15% integration, 5% E2E
- All tests use shared mocks from `/common/mocks/`
- Clear separation of concerns
- Better maintainability
- Living documentation via Cucumber features
- ~60% faster execution despite 2x more tests

---

## Command Reference

```bash
# Run only unit tests (FAST)
mvn test -Dgroups="unit"

# Run integration tests (MEDIUM)
mvn test -Dgroups="integration"

# Run E2E tests (SLOW)
mvn test -Dgroups="e2e"

# Run all tests
mvn clean test

# Run with coverage report
mvn clean test jacoco:report
```

