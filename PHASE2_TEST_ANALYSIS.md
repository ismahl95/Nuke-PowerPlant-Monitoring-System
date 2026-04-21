# PHASE 2: Test Performance Refactoring - Detailed Analysis

## Current Test Architecture

### Supplier Module
```
supplier/
├── controller/
│   └── SupplierIntegrationTest.java       ❌ SLOW  -  30 tests, 50+ seconds
└── service/
    ├── SupplierUnitTest.java              ✅ FAST -  29 tests, 0.15 seconds
    └── SupplierServiceTestMocks.java      (test data fixtures)
```

### NuclearPlant Module
```
nuclearPlant/
├── controller/
│   └── NuclearPlantIntegrationTest.java   ❌ SLOW -  7 tests, 18 seconds
└── service/
    ├── NuclearPlantUnitTest.java          ✅ FAST -  15 tests, 0.28 seconds
    └── NuclearPlantServiceTestMocks.java  (test data fixtures)
```

### Incident Module (NEW - Reference Pattern)
```
incident/
├── mapper/
│   └── IncidentMapperTest.java            ✅ FAST -  4 tests, 0.1 seconds
├── service/
│   ├── IncidentServiceImplTest.java       ✅ FAST -  13 tests, 0.3 seconds
│   └── IncidentServiceTestMocks.java      (test data fixtures)
└── (NO controller integration tests - focuses on service logic)
```

## Performance Comparison

| Test Class | Type | Tests | Time | Pattern |
|-----------|------|-------|------|---------|
| SupplierUnitTest | Unit | 29 | 0.15s | ✅ FAST |
| SupplierIntegrationTest | Integration | 30 | 50s | ❌ SLOW |
| IncidentServiceImplTest | Unit | 13 | 0.3s | ✅ FAST |
| IncidentMapperTest | Unit | 4 | 0.1s | ✅ FAST |
| **TOTAL** | | **123** | **~70s** | **40% integration** |

## Problem Analysis

### Why SupplierIntegrationTest is Slow:
1. `@SpringBootTest` loads entire Spring context (10 seconds first time)
2. `@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)` recreates context for EVERY test
3. Tests use `@Transactional` + database operations
4. Data fixtures loaded from `data.sql` (creates test data in H2)
5. 30 tests × 1.5-2 seconds each = 45-60 seconds

### Redundancy Issue:
- SupplierIntegrationTest tests business logic already covered in SupplierUnitTest
- Example: "test getAllSuppliers returns list" tested twice
  - Once with real Spring beans (slow)
  - Once with mocks (fast) ← should be the only one
- Integration tests should focus on HTTP/security/validation only

## Solution Strategy

### For Supplier & NuclearPlant:
1. Keep existing unit tests (already following correct pattern)
2. Refactor integration tests to focus on:
   - HTTP status codes
   - JWT security validation
   - Request/response serialization
   - NOT business logic (that's unit test's job)
3. Reduce SupplierIntegrationTest from 30 tests to 8-10 critical HTTP scenarios
4. Same for NuclearPlantIntegrationTest

### For Incident Module:
- Continue pattern: No integration tests needed yet
- Focus on unit tests with mocks (fastest)
- Can add minimal integration tests later if needed

## Expected Results After Phase 2

BEFORE:
- 123 tests total
- 70 seconds total
- 30-50% of tests are redundant integration tests

AFTER:
- 120 tests total
- 10-15 seconds total ✅ 75% faster
- 10% integration tests (critical HTTP/security only)
- 90% unit tests with mocks (fast + maintainable)

## Action Plan

1. Analyze SupplierIntegrationTest - identify which tests are duplicate business logic
2. Refactor to critical HTTP tests only - keep security, validation, serialization tests
3. Create documentation - show which tests moved to unit layer
4. Repeat for NuclearPlant - same pattern
5. Measure improvement - run tests and compare timing

