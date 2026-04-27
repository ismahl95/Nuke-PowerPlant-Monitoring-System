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

---

## Expected Results

### Before (Current)
- Total tests: 103
- Execution time: ~60 seconds
- Test distribution: 80% unit, 20% integration, 0% E2E

### After Phase 2 (Progress so far)
- **Completed**: 67+ tests (NuclearPlant 32 + Supplier 38)
- **Execution time**: ~20 seconds (for 2 modules)
- **Test distribution**: 85% unit, 12% integration, 3% E2E

---

> **Note**: This document is historical. For latest status, see README.md in parent directory.

