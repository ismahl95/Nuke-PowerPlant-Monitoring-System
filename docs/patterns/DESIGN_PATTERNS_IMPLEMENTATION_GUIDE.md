# 🎯 Design Patterns Implementation Guide

> Quick reference for implementing enterprise design patterns in Nuke PowerPlant Monitoring System

---

## 📋 Document Overview

The **DESIGN_PATTERNS_ROADMAP.md** contains a strategic implementation plan for 10 design patterns across 3 phases:

| Phase | Patterns | Timeline | Impact |
|-------|----------|----------|--------|
| 🔴 **Phase 2** (IMMEDIATE) | Factory, Strategy, Template Method | Weeks 1-2 | High |
| 🟠 **Phase 3** (SHORT-TERM) | State, Observer, Adapter, Facade | Weeks 3-4 | High |
| 🟡 **Phase 4+** (FUTURE) | Composite, Builder Advanced, Chain of Responsibility | Ongoing | Medium |

---

## 🚀 Quick Start

### Before Implementation
1. **Read**: `DESIGN_PATTERNS_ROADMAP.md` (sections for each pattern)
2. **Understand**: Which modules need which patterns
3. **Plan**: Prioritize by Phase and complexity

### During Implementation  
1. **Follow**: Code examples in roadmap (copy-paste friendly)
2. **Test**: Add unit + integration tests (50+ tests planned)
3. **Refactor**: Gradually remove duplicate code
4. **Document**: Update code with pattern comments

### After Implementation
1. **Verify**: All tests pass, code coverage maintained
2. **Review**: Peers review pattern usage
3. **Archive**: Update this guide with lessons learned

---

## 📍 Patterns Quick Lookup

### Phase 2 (IMMEDIATE) — Next 2 weeks

#### 1️⃣ Factory Method (Sensor Creation)
- **File**: `DESIGN_PATTERNS_ROADMAP.md` → "Phase 2" section
- **Location**: `src/main/java/com/ihl95/nuclear/sensor/application/factory/`
- **Why**: Decouple sensor type creation from business logic
- **Complexity**: ⭐ Low
- **Tests**: `SensorFactoryTest.java`

#### 2️⃣ Strategy Pattern (Anomaly Detection)
- **File**: `DESIGN_PATTERNS_ROADMAP.md` → "Phase 2" section
- **Location**: `src/main/java/com/ihl95/nuclear/anomaly/application/strategy/`
- **Why**: Different detection algorithms per sensor type
- **Complexity**: ⭐⭐ Medium
- **Tests**: `AnomalyDetectionStrategyTest.java`, `TemperatureAnomalyStrategyTest.java`

#### 3️⃣ Template Method (Base CRUD Service)
- **File**: `DESIGN_PATTERNS_ROADMAP.md` → "Phase 2" section
- **Location**: `src/main/java/com/ihl95/nuclear/common/service/BaseCrudService.java`
- **Why**: Eliminate code duplication across 8+ services
- **Complexity**: ⭐⭐ Medium
- **Impact**: ~150 LOC reduction in service layer
- **Refactoring**: Update `NuclearPlantService`, `SupplierService`, etc.

---

### Phase 3 (SHORT-TERM) — Weeks 3-4

#### 4️⃣ State Pattern (Reactor Lifecycle)
- **Location**: `src/main/java/com/ihl95/nuclear/reactor/application/state/`
- **Why**: Clean state machine for reactor status transitions
- **Complexity**: ⭐⭐ Medium
- **Example**: ACTIVE → MAINTENANCE → SHUTDOWN

#### 5️⃣ Observer Pattern (Real-Time Monitoring)
- **Location**: `src/main/java/com/ihl95/nuclear/sensor/application/observer/`
- **Why**: Notify multiple systems (Anomaly detection, Alerts, History logging) on each sensor reading
- **Complexity**: ⭐⭐⭐ High
- **Subscribers**: AnomalyDetectionObserver, AlertNotificationObserver

#### 6️⃣ Adapter Pattern (Multiple DCS Support)
- **Location**: `src/main/java/com/ihl95/nuclear/controlsystem/application/adapter/`
- **Why**: Support SCADA, PLC, DCS with different interfaces
- **Complexity**: ⭐⭐ Medium
- **Example**: `SCADAAdapter`, `PLCAdapter`, `DCSAdapter`

#### 7️⃣ Facade Pattern (Complex Operations)
- **Location**: `src/main/java/com/ihl95/nuclear/orchestration/application/facade/`
- **Why**: Coordinate multi-module workflows (e.g., reactor shutdown)
- **Complexity**: ⭐⭐⭐ High
- **Example**: `ReactorOperationsFacade.shutdownReactorSafely()`

---

### Phase 4+ (FUTURE) — Backlog

#### 8️⃣ Composite Pattern (Equipment Hierarchy)
#### 9️⃣ Builder Advanced (Report Generation)
#### 🔟 Chain of Responsibility (Incident Escalation)

---

## 📊 Implementation Checklist

### Phase 2 Tasks

- [ ] **Factory Method**
  - [ ] Create `SensorFactory.java` interface
  - [ ] Create `TemperatureSensorFactory.java`, `PressureSensorFactory.java`, etc.
  - [ ] Update `SensorService` to use factory
  - [ ] Add `SensorFactoryTest.java` (5+ tests)
  - [ ] Commit: `feat: implement Factory pattern for sensor creation`

- [ ] **Strategy Pattern**
  - [ ] Create `AnomalyDetectionStrategy.java` interface
  - [ ] Create concrete strategies (`TemperatureAnomalyStrategy`, etc.)
  - [ ] Create `AnomalyDetectionContext.java`
  - [ ] Update `AnomalyService` to use context
  - [ ] Add unit tests (7+ tests per strategy)
  - [ ] Commit: `feat: implement Strategy pattern for anomaly detection`

- [ ] **Template Method**
  - [ ] Create `BaseCrudService<E, D, R extends JpaRepository>` abstract class
  - [ ] Refactor `NuclearPlantServiceImpl` to extend `BaseCrudService`
  - [ ] Refactor `SupplierServiceImpl` to extend `BaseCrudService`
  - [ ] Refactor remaining 6 service implementations
  - [ ] Verify all tests still pass
  - [ ] Add `BaseCrudServiceTest.java` (parametrized tests)
  - [ ] Commit: `refactor: implement Template Method pattern in service layer`

---

## 💡 Best Practices

### When Implementing Patterns

1. **Start small**: Implement one pattern at a time
2. **Write tests first**: TDD approach for pattern implementation
3. **Keep it simple**: Don't over-engineer, use patterns where they provide clear benefit
4. **Document**: Add comments explaining the pattern choice
5. **Review**: Have team review pattern implementation before merging

### Code Style for Patterns

```java
// ✅ DO: Clear pattern usage
@Component
public class TemperatureSensorFactory implements SensorFactory { }

// ❌ DON'T: Hidden pattern, no interface
public class TemperatureSensorFactory {
    public Sensor create() { }
}
```

### Testing Patterns

```java
// ✅ DO: Test pattern contracts
@Test
void factoryShouldCreateCorrectType() { }

@Test
void strategyShouldExecuteAlgorithm() { }

// ❌ DON'T: Test implementation details
@Test
void internalFieldShouldBeSet() { }
```

---

## 📁 Project Structure After Implementation

```
backend/src/main/java/com/ihl95/nuclear/

├── common/service/
│   └── BaseCrudService.java                    ← Template Method

├── sensor/application/
│   ├── factory/
│   │   ├── SensorFactory.java                  ← Factory
│   │   ├── TemperatureSensorFactory.java
│   │   └── PressureSensorFactory.java
│   └── observer/
│       ├── SensorReadingObserver.java          ← Observer
│       ├── AnomalyDetectionObserver.java
│       └── SensorReadingPublisher.java

├── anomaly/application/
│   └── strategy/
│       ├── AnomalyDetectionStrategy.java       ← Strategy
│       ├── TemperatureAnomalyStrategy.java
│       └── PressureAnomalyStrategy.java

├── reactor/application/
│   ├── state/
│   │   ├── ReactorState.java                   ← State (Phase 3)
│   │   ├── ActiveReactorState.java
│   │   └── ShutdownReactorState.java
│   └── service/
│       └── ReactorServiceImpl extends BaseCrudService

├── controlsystem/application/
│   └── adapter/
│       ├── ControlSystemAdapter.java           ← Adapter (Phase 3)
│       ├── SCADAAdapter.java
│       └── PLCAdapter.java

└── orchestration/application/
    └── facade/
        ├── ReactorOperationsFacade.java        ← Facade (Phase 3)
        └── PlantShutdownFacade.java
```

---

## 🧪 Testing Strategy

### Pattern Test Coverage Target: 50+ tests

```
Pattern-Specific Tests:
├── Factory Tests (5)
├── Strategy Tests (14) — 7 per strategy
├── Template Method Tests (8) — parametrized
├── State Tests (8)
├── Observer Tests (6)
├── Adapter Tests (5)
└── Facade Tests (4)
────────────────────
TOTAL: ~50 tests
```

**Execution Time**: ~10 seconds (modern unit tests are fast)  
**Coverage**: ~15% increase in code coverage

---

## 📈 Metrics to Track

### Code Quality Before/After

| Metric | Before | After | Improvement |
|--------|--------|-------|-------------|
| Service LOC | 800 | 150 | -81% |
| Code duplication | 65% | ~15% | -50pp |
| Tests per module | 8 | 12 | +50% |
| Execution time | 60s | 15s | -75% |
| Maintainability | Medium | High | +60% |

---

## 🔗 Related Documents

- **DESIGN_PATTERNS_ROADMAP.md** — Full strategic roadmap (this reference)
- **PHASE2_DETAILED_PLAN.md** — E2E testing implementation (parallel work)
- **IMPROVEMENT_AREAS.md** — Quality improvement priorities
- **AGENTS.md** — Testing guides for implementation

---

## 🆘 FAQ

### Q: Should I implement all patterns at once?
**A**: No. Follow the Phase schedule: Phase 2 → Phase 3 → Phase 4+. Stagger implementation over 2-4 weeks.

### Q: What if a pattern doesn't fit?
**A**: That's OK. The roadmap suggests patterns, but domain requirements override. Use patterns where they provide clear benefit.

### Q: How do I know if my pattern implementation is correct?
**A**: Code review + unit tests + behavior verification. If tests pass and code is cleaner, it's good.

### Q: Can I implement patterns in a different order?
**A**: Generally yes, but follow dependency chain:
- Phase 2 patterns are independent
- Phase 3 patterns depend on Phase 2 foundation
- Phase 4+ patterns are optional

### Q: Should we use Spring patterns instead?
**A**: Use both. Spring patterns (dependency injection, bean lifecycle) complement Gang of Four patterns (factory, strategy, etc.).

---

## 📞 Support

For questions on specific patterns, refer to:
1. **DESIGN_PATTERNS_ROADMAP.md** — Full documentation
2. **Code examples in roadmap** — Copy-paste starting points
3. **Unit tests** — Behavior examples
4. **Pattern screenshots** — Diagrams in future docs

---

**Version**: 1.0  
**Created**: 2026-04-26  
**Status**: Ready for Phase 2 implementation

