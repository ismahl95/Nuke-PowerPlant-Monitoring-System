# 🤖 Design Patterns Agent — Quick Reference

> Specialized AI Agent for implementing 10 enterprise design patterns in the Nuke PowerPlant system

---

## 📍 Where to Find It

**Location**: `.github/copilot/agents/04-design-patterns-agent.md`

**How to use it**: Copy-paste tasks from the sections below into GitHub Copilot with this context

---

## 🎯 10 Patterns Overview

### Phase 2 (IMMEDIATE) — Start Here

**1️⃣ Factory Method** `sensor/`
```
Task: "Implement Factory pattern for sensor creation (TemperatureSensorFactory, 
PressureSensorFactory, RadiationSensorFactory). Follow templates in 04-design-patterns-agent.md. 
Add 8 unit tests + 2 integration tests."
```

**2️⃣ Strategy Pattern** `anomaly/`
```
Task: "Implement Strategy pattern for anomaly detection. Create 3 concrete strategies 
(Temperature, Pressure, Radiation). Use AnomalyDetectionContext to select strategy. 
Add 14 unit tests + tests for context."
```

**3️⃣ Template Method** `common/service/`
```
Task: "Create abstract BaseCrudService to eliminate duplication in 8+ services. 
Refactor NuclearPlantService first, then others. Add parametrized tests. 
Net code reduction should be ~150 LOC."
```

---

### Phase 3 (SHORT-TERM) — After Phase 2 Complete

**4️⃣ State Pattern** `reactor/`
```
Task: "Implement State pattern for reactor lifecycle (ACTIVE → MAINTENANCE → SHUTDOWN). 
Create ReactorState interface with 3 concrete state implementations. 
Add 8 unit tests + integration tests. Reference reactor module for transitions."
```

**5️⃣ Observer Pattern** `sensor/`
```
Task: "Implement Observer pattern for real-time sensor monitoring. Create 
AnomalyDetectionObserver, AlertNotificationObserver. Register in SensorReadingPublisher. 
Add 6 unit tests + 4 integration tests for multi-observer scenarios."
```

**6️⃣ Adapter Pattern** `controlsystem/`
```
Task: "Implement Adapter pattern to support SCADA/PLC/DCS. Create ControlSystemAdapter 
interface with SCADAAdapter, PLCAdapter, DCSAdapter implementations. 
Add 5 unit tests + integration tests for adapter registration."
```

**7️⃣ Facade Pattern** `orchestration/`
```
Task: "Implement Facade pattern for complex operations (e.g., shutdown reactor safely). 
Create ReactorOperationsFacade to coordinate Sensor, Incident, Alert, Operator services. 
Add 4 integration tests for multi-module workflows."
```

---

### Phase 4+ (FUTURE) — Backlog

**8️⃣ Composite Pattern** `equipment/`
**9️⃣ Builder (Advanced)** `report/`
**🔟 Chain of Responsibility** `incident/`

---

## 📋 Implementation Checklist Template

**For any pattern, copy this checklist:**

- [ ] **Discovery Phase**
  - [ ] Read pattern description in DESIGN_PATTERNS_ROADMAP.md
  - [ ] Review template in 04-design-patterns-agent.md
  - [ ] Identify exact ubicación y módulo

- [ ] **Design Phase**
  - [ ] Create interface with clear contract
  - [ ] Design 2-3 concrete implementations
  - [ ] Document responsibilities

- [ ] **Code Phase**
  - [ ] Create interface file
  - [ ] Create implementation files (Spring @Component)
  - [ ] Integrate into existing service
  - [ ] Follow naming conventions

- [ ] **Test Phase**
  - [ ] Write 5-8 unit tests (@ExtendWith)
  - [ ] Write 2-3 integration tests (@SpringBootTest)
  - [ ] Write 1-2 E2E scenarios (Gherkin)
  - [ ] Run: `mvn test` — all green

- [ ] **Refactor Phase**
  - [ ] Remove duplicate code
  - [ ] Update existing code to use pattern
  - [ ] Verify functionality unchanged

- [ ] **Review Phase**
  - [ ] Create PR with pattern + tests
  - [ ] Code review focused on pattern correctness
  - [ ] Merge when approved

- [ ] **Commit**
  ```bash
  git commit -m "feat: implement {Pattern} for {Domain} ({N} tests)"
  ```

---

## 🏃 Quick Start (30 minutes)

**Step 1**: Pick a Pattern (recommend Factory first)

**Step 2**: Copy task template above into Copilot

**Step 3**: Ask Copilot to:
```
"Using .github/copilot/agents/04-design-patterns-agent.md as reference, 
implement {Pattern} in {Module}. Follow the template, create Spring beans, 
add unit tests. Return file structure and implementation code."
```

**Step 4**: Review generated code against checklist

**Step 5**: Write integration tests using 02-integration-tests-guide.md

**Step 6**: Commit and create PR

---

## 📊 Metrics for Success

### Per Pattern
- ✅ Unit tests: 5-8
- ✅ Integration tests: 2-3  
- ✅ Code files: 3-5
- ✅ Lines of code: 100-300
- ✅ Code coverage: 90%+

### After All Phase 2 Patterns (3 patterns)
- ✅ Total tests: 25-30 new tests
- ✅ Service LOC: ~150 LOC reduction
- ✅ Code duplication: Reduced 15-20%
- ✅ Team velocity: +20%

---

## 🔗 Essential References

| Need | File | Section |
|------|------|---------|
| Strategic plan | DESIGN_PATTERNS_ROADMAP.md | Full document |
| Code templates | 04-design-patterns-agent.md | "Templates" section |
| Testing patterns | 01-03-tests-guides.md | Respective guides |
| Quick lookup | DESIGN_PATTERNS_IMPLEMENTATION_GUIDE.md | Matrix lookup |
| Architecture context | MODERN_ARCHITECTURE.md | Patterns section |

---

## 💡 Pro Tips

1. **Start with Factory**
   - Simplest pattern to understand
   - Clear benefit (no hidden dependencies)
   - Foundation for other patterns

2. **Test-Driven Implementation**
   - Write test cases first (what should interface do?)
   - Then implement interface
   - Then implement concrete classes

3. **Use Agent for Code Generation**
   - Ask agent to: "Generate unit test for TemperatureSensorFactory"
   - Ask agent to: "Refactor NuclearPlantService to extend BaseCrudService"
   - Ask agent to: "Create Spring configuration for observer pattern"

4. **Incremental Refactoring**
   - Don't refactor all 8 services at once for Template Method
   - Do 1-2 services per day, run tests, verify behavior

5. **Code Review Checklist**
   - Does it solve the stated problem?
   - Is the pattern applied correctly?
   - Are tests comprehensive?
   - Did duplicated code get removed?
   - Are Spring beans properly configured?

---

## 🚀 Ready to Start?

**Option 1: Ask the Agent Directly**
```
"Implement Factory pattern for sensor creation following 
.github/copilot/agents/04-design-patterns-agent.md. Include unit tests."
```

**Option 2: Use the Templates**
1. Open 04-design-patterns-agent.md
2. Copy template for chosen pattern
3. Customize for your module
4. Create PR

**Option 3: Full Guidance**
```
"I want to implement design patterns in my Spring Boot project. 
Use .github/copilot/agents/04-design-patterns-agent.md and 
DESIGN_PATTERNS_ROADMAP.md to create an implementation plan for Phase 2."
```

---

## 📞 Common Questions

**Q: Which pattern first?**  
A: Factory Method (simplest, Phase 2)

**Q: How long does each pattern take?**  
A: 1-2 days per pattern with full tests

**Q: Can I do patterns in parallel?**  
A: Yes, Phase 2 patterns (3) are independent

**Q: What if tests fail after refactoring?**  
A: Use `git reflog` to revert, debug, try again

**Q: How do tests validate pattern correctness?**  
A: Unit tests verify contract, integration tests verify bean registration

---

**Created**: 2026-04-26  
**Status**: ✅ Ready for Phase 2 implementation  
**Agent Status**: 🤖 Active and specialized for design patterns

