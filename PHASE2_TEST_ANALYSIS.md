# PHASE 2: 3-Layer Testing Architecture Refactor

## Overview

Transitioning from mixed test strategy to clear 3-layer pyramid:
- **Layer 1**: Unit tests (Mockito) — 80% of tests, milliseconds
- **Layer 2**: Integration tests (@SpringBootTest) — 15% of tests, seconds
- **Layer 3**: E2E tests (Cucumber) — 5% of tests, business documentation

All layers share reutilizable test data in `/common/mocks/`

---

## Test Data Reusability Strategy

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
nuclearplant/
├── controller/
│   └── NuclearPlantControllerTest.java    ✅ IMPROVED -  20 tests, 9.75 seconds
│                                              (Uses @SpringBootTest + @ParameterizedTest)
│                                              (Consolidated auth tests, @DisplayName)
└── service/
    ├── NuclearPlantUnitTest.java          ✅ FAST -  15 tests, 0.24 seconds
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

| Test Class | Type | Tests | Time | Pattern | Status |
|-----------|------|-------|------|---------|--------|
| SupplierUnitTest | Unit | 29 | 0.15s | ✅ FAST | ✅ Complete |
| SupplierIntegrationTest | Integration | 30 | 50s | ❌ SLOW | ⏳ Pending refactor |
| IncidentServiceImplTest | Unit | 13 | 0.3s | ✅ FAST | ✅ Complete |
| IncidentMapperTest | Unit | 4 | 0.1s | ✅ FAST | ✅ Complete |
| NuclearPlantUnitTest | Unit | 15 | 0.24s | ✅ FAST | ✅ Complete |
| NuclearPlantControllerTest | Integration | 20 | 9.75s | ✅ IMPROVED | ✅ Complete |
| **TOTAL** | | **111** | **~60s** | **~30% integration** | 🚀 Improved |

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

BEFORE (Session Start):
- 84 unit tests total
- ~50+ seconds for integration tests
- No controller integration tests for NuclearPlant

CURRENT (After NuclearPlantControllerTest):
- 103 unit tests + 20 controller integration tests = **123 tests total**
- ~9.75 seconds for NuclearPlantControllerTest (optimized with @ParameterizedTest)
- Total execution: ~60 seconds
- **20 tests** for NuclearPlant controller endpoints (HTTP, security, validation)

AFTER (Target - Phase 2 Complete):
- 150+ tests total (add Supplier controller + Reactor/Sensor/Maintenance units)
- 10-15 seconds total ✅ 75% faster than original
- 10-15% integration tests (critical HTTP/security only)
- 85-90% unit tests with mocks (fast + maintainable)

## Action Plan

1. Analyze SupplierIntegrationTest - identify which tests are duplicate business logic
2. Refactor to critical HTTP tests only - keep security, validation, serialization tests
3. Create documentation - show which tests moved to unit layer
4. Repeat for NuclearPlant - same pattern
5. Measure improvement - run tests and compare timing

