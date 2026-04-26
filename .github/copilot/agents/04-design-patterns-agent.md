# Agente Especializado en Patrones de Diseño — Design Patterns Implementation Agent
> Para GitHub Copilot · Proyecto: `nuke-powerplant-back`

---

## 🎯 Propósito del Agente

Este agente está especializado en **implementación estratégica de patrones de diseño** en el contexto del Sistema de Monitoreo de Plantas Nucleares. Se utiliza para:

✅ Implementar patrones de diseño con código production-ready  
✅ Crear tests para validar cada patrón  
✅ Refactorizar código existente para aplicar patrones  
✅ Documentar decisiones arquitectónicas  
✅ Asegurar consistencia en la aplicación de patrones  

---

## 📋 Patrones Disponibles (10 Total)

### Phase 2 (IMMEDIATE) — 3 Patrones

#### 1️⃣ Factory Method
**Módulo**: `sensor`  
**Ubicación**: `src/main/java/com/ihl95/nuclear/sensor/application/factory/`  
**Problema**: Crear sensores de diferentes tipos sin exponer la lógica de inicialización  
**Solución**: Interface `SensorFactory` con implementaciones por tipo sensor  

**Referencia**: `DESIGN_PATTERNS_ROADMAP.md` → Phase 2 section

**Archivo de configuración esperado**:
```
sensor/application/factory/
├── SensorFactory.java                    (interface)
├── TemperatureSensorFactory.java         (impl)
├── PressureSensorFactory.java            (impl)
├── RadiationSensorFactory.java           (impl)
└── SensorFactoryConfiguration.java       (Spring config)
```

#### 2️⃣ Strategy Pattern
**Módulo**: `anomaly`  
**Ubicación**: `src/main/java/com/ihl95/nuclear/anomaly/application/strategy/`  
**Problema**: Diferentes algoritmos de detección de anomalías por tipo sensor  
**Solución**: Interface `AnomalyDetectionStrategy` con estrategias específicas  

**Referencia**: `DESIGN_PATTERNS_ROADMAP.md` → Phase 2 section

**Archivo de configuración esperado**:
```
anomaly/application/strategy/
├── AnomalyDetectionStrategy.java         (interface)
├── TemperatureAnomalyStrategy.java       (impl)
├── PressureAnomalyStrategy.java          (impl)
├── RadiationAnomalyStrategy.java         (impl)
└── AnomalyDetectionContext.java          (context selector)
```

#### 3️⃣ Template Method
**Módulo**: `common/service`  
**Ubicación**: `src/main/java/com/ihl95/nuclear/common/service/`  
**Problema**: Duplicación de código CRUD en 8+ servicios  
**Solución**: Abstract base class `BaseCrudService<E, D, R>` con template methods  

**Referencia**: `DESIGN_PATTERNS_ROADMAP.md` → Phase 2 section

**Archivo de configuración esperado**:
```
common/service/
└── BaseCrudService.java                  (abstract base)

// Services extend:
├── NuclearPlantServiceImpl extends BaseCrudService
├── SupplierServiceImpl extends BaseCrudService
├── SensorServiceImpl extends BaseCrudService
└── ... (otros servicios)
```

---

### Phase 3 (SHORT-TERM) — 4 Patrones

#### 4️⃣ State Pattern
**Módulo**: `reactor`  
**Ubicación**: `src/main/java/com/ihl95/nuclear/reactor/application/state/`  
**Problema**: Gestionar transiciones de estado del reactor (ACTIVE → MAINTENANCE → SHUTDOWN)  
**Solución**: Interface `ReactorState` con implementaciones por estado  

#### 5️⃣ Observer Pattern
**Módulo**: `sensor`  
**Ubicación**: `src/main/java/com/ihl95/nuclear/sensor/application/observer/`  
**Problema**: Notificar múltiples sistemas (detección de anomalías, alertas, historial) en cada lectura  
**Solución**: Interface `SensorReadingObserver` con publicador/suscriptor  

#### 6️⃣ Adapter Pattern
**Módulo**: `controlsystem`  
**Ubicación**: `src/main/java/com/ihl95/nuclear/controlsystem/application/adapter/`  
**Problema**: Integrar múltiples tipos de DCS/SCADA/PLC con interfaces diferentes  
**Solución**: Interface `ControlSystemAdapter` con adaptadores específicos  

#### 7️⃣ Facade Pattern
**Módulo**: `orchestration`  
**Ubicación**: `src/main/java/com/ihl95/nuclear/orchestration/application/facade/`  
**Problema**: Orquestar operaciones complejas multi-módulo (apagar reactor, etc.)  
**Solución**: Clase `ReactorOperationsFacade` coordinadora  

---

### Phase 4+ (FUTURE) — 3 Patrones

#### 8️⃣ Composite Pattern
#### 9️⃣ Builder (Advanced)
#### 🔟 Chain of Responsibility

---

## 🔧 Convenciones para Implementación de Patrones

### Nomenclatura

| Patrón | Convención | Ejemplo |
|--------|-----------|---------|
| **Factory** | `{Entity}Factory` interface + `{Type}{Entity}Factory` impl | `SensorFactory`, `TemperatureSensorFactory` |
| **Strategy** | `{Domain}Strategy` interface + `{Type}{Domain}Strategy` impl | `AnomalyDetectionStrategy`, `TemperatureAnomalyStrategy` |
| **State** | `{Entity}State` interface + `{State}` impl | `ReactorState`, `ActiveReactorState` |
| **Observer** | `{DomainChange}Observer` interface + `{System}Observer` impl | `SensorReadingObserver`, `AnomalyDetectionObserver` |
| **Adapter** | `{ExternalSystem}Adapter` + `{Type}{System}Adapter` | `ControlSystemAdapter`, `SCADAAdapter` |
| **Facade** | `{Domain}OperationsFacade` | `ReactorOperationsFacade` |
| **Base Class** | `Base{Domain}Service<E, D, R>` | `BaseCrudService<E, D, R>` |

### Inyección en Spring

Todos los patrones deben ser beans gestionados por Spring:

```java
// Factory
@Component
public class TemperatureSensorFactory implements SensorFactory { }

// Strategy
@Component
public class TemperatureAnomalyStrategy implements AnomalyDetectionStrategy { }

// Adapter
@Component
public class SCADAAdapter implements ControlSystemAdapter { }

// Facade
@Service
public class ReactorOperationsFacade { }

// Base Service
@Service
public abstract class BaseCrudService<E, D, R extends JpaRepository<E, Long>> { }
```

### Mapeo Automático de Beans

Para patrones con múltiples implementaciones, usar `Map<String, Interface>`:

```java
@Autowired
private Map<SensorType, SensorFactory> factoryMap;

@Autowired
private Map<String, ControlSystemAdapter> adapterMap;

@Autowired
private Map<AnomalySeverity, AnomalyDetectionStrategy> strategyMap;
```

---

## 📂 Estructura de Archivos para Cada Patrón

### Template para Factory Pattern

```java
// Interface
public interface SensorFactory {
    Sensor createSensor(String sensorId, String location);
}

// Implementación
@Component
public class TemperatureSensorFactory implements SensorFactory {
    @Override
    public Sensor createSensor(String sensorId, String location) {
        return Sensor.builder()
            .id(sensorId)
            .type(SensorType.TEMPERATURE)
            .location(location)
            // ... otros campos
            .build();
    }
}

// Uso en Service
@Service
public class SensorService {
    @Autowired
    private Map<SensorType, SensorFactory> factoryMap;
    
    public Sensor createSensor(SensorDTO dto) {
        SensorFactory factory = factoryMap.get(dto.type());
        if (factory == null) throw new UnsupportedSensorTypeException();
        return factory.createSensor(dto.id(), dto.location());
    }
}
```

### Template para Strategy Pattern

```java
// Interface
public interface AnomalyDetectionStrategy {
    boolean detectAnomaly(SensorReading reading);
    AnomalySeverity getSeverity(SensorReading reading);
}

// Implementación
@Component
public class TemperatureAnomalyStrategy implements AnomalyDetectionStrategy {
    private static final double HIGH_TEMP = 350.0;
    private static final double CRITICAL_TEMP = 380.0;
    
    @Override
    public boolean detectAnomaly(SensorReading reading) {
        return reading.getValue() > HIGH_TEMP;
    }
    
    @Override
    public AnomalySeverity getSeverity(SensorReading reading) {
        return reading.getValue() > CRITICAL_TEMP 
            ? AnomalySeverity.CRITICAL 
            : AnomalySeverity.WARNING;
    }
}

// Context
@Service
public class AnomalyDetectionContext {
    @Autowired
    private Map<SensorType, AnomalyDetectionStrategy> strategies;
    
    public Anomaly detectAndCreate(SensorReading reading) {
        AnomalyDetectionStrategy strategy = strategies.get(reading.getSensor().getType());
        if (strategy.detectAnomaly(reading)) {
            return Anomaly.builder()
                .severity(strategy.getSeverity(reading))
                .sensorReading(reading)
                .build();
        }
        return null;
    }
}
```

### Template para Template Method Pattern

```java
@Service
@Transactional
public abstract class BaseCrudService<E, D, R extends JpaRepository<E, Long>> {
    
    protected abstract R getRepository();
    protected abstract Class<D> getDTOClass();
    protected abstract E createEntityFromDTO(D dto);
    protected abstract D mapEntityToDTO(E entity);
    protected abstract void updateEntityFromDTO(D dto, E entity);
    
    // TEMPLATE METHODS (final)
    public final List<D> getAll() {
        return getRepository().findAll().stream()
            .map(this::mapEntityToDTO)
            .collect(Collectors.toList());
    }
    
    public final D getById(Long id) {
        validateId(id);
        return getRepository().findById(id)
            .map(this::mapEntityToDTO)
            .orElseThrow(() -> new EntityNotFoundException(
                String.format("%s not found: %d", getEntityName(), id)));
    }
    
    public final D create(D dto) {
        validateDTO(dto);
        E entity = createEntityFromDTO(dto);
        E saved = getRepository().save(entity);
        return mapEntityToDTO(saved);
    }
    
    // HOOKS (override si es necesario)
    protected void validateDTO(D dto) { }
    protected void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("ID must be positive");
        }
    }
}

// Implementación en un servicio
@Service
public class NuclearPlantServiceImpl 
    extends BaseCrudService<NuclearPlant, NuclearPlantDTO, NuclearPlantRepository> {
    
    @Autowired private NuclearPlantRepository repository;
    @Autowired private NuclearPlantMapper mapper;
    
    @Override
    protected NuclearPlantRepository getRepository() { return repository; }
    
    @Override
    protected Class<NuclearPlantDTO> getDTOClass() { return NuclearPlantDTO.class; }
    
    @Override
    protected NuclearPlant createEntityFromDTO(NuclearPlantDTO dto) {
        return mapper.toNuclearPlant(dto);
    }
    
    @Override
    protected NuclearPlantDTO mapEntityToDTO(NuclearPlant entity) {
        return mapper.toNuclearPlantDTO(entity);
    }
    
    @Override
    protected void validateDTO(NuclearPlantDTO dto) {
        if (dto.name() == null || dto.name().isBlank()) {
            throw new InvalidRequestException("Name required");
        }
    }
}
```

---

## 🧪 Testing Strategy para Patrones

Cada patrón requiere tests siguiendo la pirámide 3-capas:

### Unit Tests (80%)

```java
@ExtendWith(MockitoExtension.class)
class SensorFactoryTest {
    
    @InjectMocks
    private TemperatureSensorFactory factory;
    
    @Test
    void shouldCreateTemperatureSensorWithCorrectType() {
        Sensor sensor = factory.createSensor("T001", "Reactor-1");
        
        assertThat(sensor.getType()).isEqualTo(SensorType.TEMPERATURE);
        assertThat(sensor.getId()).isEqualTo("T001");
    }
    
    @Test
    void shouldSetCorrectThresholds() {
        Sensor sensor = factory.createSensor("T001", "Reactor-1");
        
        assertThat(sensor.getMinValue()).isEqualTo(-50);
        assertThat(sensor.getMaxValue()).isEqualTo(400);
    }
}
```

### Integration Tests (15%)

```java
@SpringBootTest
@AutoConfigureMockMvc
class SensorFactoryIntegrationTest {
    
    @Autowired
    private ApplicationContext context;
    
    @Test
    void factoryShouldBeRegisteredAsBean() {
        TemperatureSensorFactory factory = context.getBean(TemperatureSensorFactory.class);
        assertThat(factory).isNotNull();
    }
    
    @Test
    void shouldMapFactoriesInServiceInjection() {
        SensorService service = context.getBean(SensorService.class);
        // Validar que el servicio puede acceder al mapa de factories
    }
}
```

### E2E Tests (5%)

```gherkin
Feature: Pattern-Based Sensor Creation
  
  Scenario: Create temperature sensor using factory
    Given a sensor factory for TEMPERATURE type
    When I create a sensor with ID "T001" and location "Reactor-1"
    Then the sensor should be created with type TEMPERATURE
    And the sensor should have temperature-specific thresholds
```

---

## ✅ Checklist de Implementación

### Por cada patrón, completar:

- [ ] **Análisis**
  - [ ] Confirmar módulo y ubicación
  - [ ] Validar que el problema existe en el código actual
  - [ ] Revisar `DESIGN_PATTERNS_ROADMAP.md` para contexto

- [ ] **Diseño**
  - [ ] Crear interfaces (Spring beans)
  - [ ] Diseñar implementaciones concretas
  - [ ] Documentar responsabilidades

- [ ] **Implementation**
  - [ ] Crear archivos base (interface + 2-3 impl)
  - [ ] Configurar inyección en Spring
  - [ ] Integrar en servicios existentes
  - [ ] Seguir nomenclatura conventions

- [ ] **Refactoring**
  - [ ] Actualizar código que usa el patrón
  - [ ] Remover código duplicado
  - [ ] Validar que funcionalidad no cambia

- [ ] **Testing**
  - [ ] Escribir unit tests para cada clase
  - [ ] Escribir integration tests para bean registration
  - [ ] Escribir E2E scenarios en Gherkin
  - [ ] Ejecutar: `mvn test`

- [ ] **Documentation**
  - [ ] Agregar comentarios en código
  - [ ] Actualizar AGENTS.md con nuevo patrón
  - [ ] Crear/actualizar diagrama si es complejo
  - [ ] Documentar decisiones arquitectónicas

- [ ] **Code Review**
  - [ ] Hacer PR con patrón implementado
  - [ ] Code review enfocado en pattern correctness
  - [ ] Validar que el patrón resuelve el problema
  - [ ] Validar que los tests pasan

- [ ] **Commit**
  ```bash
  git commit -m "feat: implement {Pattern} pattern for {Domain} ({{tests}} tests)"
  # Ejemplo: feat: implement Factory pattern for sensor creation (8 tests)
  ```

---

## 📊 Métricas Esperadas

### Por Patrón Implementado

| Métrica | Esperado |
|---------|----------|
| Unit Tests | 5-8 tests |
| Integration Tests | 2-3 tests |
| E2E Scenarios | 1-2 scenarios |
| Code Files | 3-5 archivos |
| Lines Changed | 100-300 LOC |
| Duplicate Code Removed | 20-50 LOC (Template Method > 100) |
| Code Coverage | 90%+ |

### Después de Todos los Patrones

| Métrica | Antes | Después |
|---------|-------|---------|
| Total Tests | 70 | 120+ |
| Service LOC | 8000+ | ~7850 (-2.5%) |
| Code Duplication | 65% | ~30% |
| Maintainability | Medium | High |
| Execution Time | ~15s | ~20s (but more tests) |

---

## 🎯 Guía Paso a Paso para Cada Fase

### Phase 2 Implementation (3 patrones, 2 semanas)

#### Semana 1: Factory + Strategy
1. **Monday-Tuesday**: Factory Method (Sensor)
   - [ ] Crear interface + 3 implementaciones
   - [ ] Configurar Spring bean mapping
   - [ ] Escribir 8 unit tests
   - [ ] Actualizar SensorService
   - [ ] Commit y code review

2. **Wednesday-Thursday**: Strategy Pattern (Anomaly)
   - [ ] Crear interface + 3 implementaciones
   - [ ] Crear AnomalyDetectionContext
   - [ ] Escribir 14 unit tests
   - [ ] Actualizar AnomalyService
   - [ ] Commit y code review

3. **Friday**: Template Method (Start)
   - [ ] Crear abstract BaseCrudService
   - [ ] Refactor NuclearPlantService
   - [ ] Escribir tests parametrizados

#### Semana 2: Template Method (Completion) + Phase 2 Testing
1. **Monday-Wednesday**: Template Method
   - [ ] Refactor remaining 7 services
   - [ ] Parametrized test coverage
   - [ ] Validar que todos tests pasan
   - [ ] Code review + merge

2. **Thursday-Friday**: E2E Testing for Reactor
   - [ ] Crear Reactor features file
   - [ ] Escribir steps para CRUD
   - [ ] Validar E2E scenarios pasan

---

## 🔗 Recursos Vinculados

### Documentación Principal
- **DESIGN_PATTERNS_ROADMAP.md** — Plan estratégico completo con ejemplos
- **DESIGN_PATTERNS_IMPLEMENTATION_GUIDE.md** — Quick reference para developers
- **MODERN_ARCHITECTURE.md** — Context arquitectónico del proyecto

### Guías de Testing
- **.github/copilot/agents/01-unit-tests-guide.md** — Unit testing pattern
- **.github/copilot/agents/02-integration-tests-guide.md** — Integration testing
- **.github/copilot/agents/03-e2e-tests-guide.md** — E2E/Cucumber patterns

### Convenciones del Proyecto
- **AGENTS.md** — General project guidelines (similar reference)
- **README.md** — Commit message conventions

---

## 🚀 Cómo Usar Este Agente

### Para Desarrolladores

1. **Identificar patrón a implementar**
   ```
   "Implementar Factory pattern para creación de sensores"
   ```

2. **El agente debe:**
   - Verificar ubicación correcta en codebase
   - Crear interface + implementaciones base
   - Generar unit tests completos
   - Refactorizar código existente
   - Crear commit semantic

### Para Arquitectos

1. **Validar decisión de patrón**
   ```
   "¿Es válido usar Observer pattern para monitoreo en tiempo real?"
   ```

2. **El agente debe:**
   - Verificar problema que resuelve
   - Revisar trade-offs
   - Sugerir alternativas
   - Validar que es el mejor fit

### Para Tech Leads

1. **Planificar implementación**
   ```
   "Crear plan de implementación para Phase 2 patterns (Factory, Strategy, Template)"
   ```

2. **El agente debe:**
   - Desglosar tareas por patrón
   - Estimar timeline
   - Identificar dependencias
   - Priorizar módulos

---

## ❓ FAQ - Preguntas Frecuentes del Agente

### P: ¿Qué patrón debo implementar primero?
**R:** Comienza con la Phase 2: Factory Method → Strategy Pattern → Template Method. 
Estos patrones son independientes y sientan la base para Phase 3.

### P: ¿Cómo valido que un patrón está bien implementado?
**R:** Verifica: (1) Todos los tests pasan, (2) No hay duplicación de código, 
(3) Spring beans registrados correctamente, (4) Code review aprobado.

### P: ¿Puedo implementar patrones en orden diferente?
**R:** Solo dentro de Phase 2 (los 3 patrones son independientes). 
Phase 3 patrones dependen de Phase 2 foundation.

### P: ¿Qué hacer si descubro que el patrón no fit?
**R:** Documental el descubrimiento, proponer alternativa, y hacer code review 
con arquitecto antes de proceder.

### P: ¿Cómo refactorizo código existente para usar el patrón?
**R:** Crear el patrón primero (con tests), luego refactorizar gradualmente, 
validar que tests siguen pasando. Usar `git reflog` para rollback si es necesario.

---

## 📞 Soporte

Si necesitas ayuda implementando un patrón:
1. Revisar ejemplos en `DESIGN_PATTERNS_ROADMAP.md`
2. Revisar templates en este documento
3. Consultar guías de testing (01-03-tests-guides.md)
4. Code review con equipo arquitectó

---

## 👥 Roles del Equipo

### Developer (Implementor)
- Usar este agente para: Crear archivos, escribir código, tests
- Documentación: Templates y ejemplos

### Tech Lead (Validator)
- Usar este agente para: Code reviews, validaciones arquitectónicas
- Documentación: Checklist y métricas

### Architect (Strategist)
- Usar este agente para: Decisiones de patrones, trade-offs
- Documentación: DESIGN_PATTERNS_ROADMAP.md

---

**Versión**: 1.0  
**Última actualización**: 2026-04-26  
**Estatus**: Ready for Phase 2 implementation  
**Owner**: GitHub Copilot Design Patterns Agent

