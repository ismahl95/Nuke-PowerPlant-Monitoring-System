# Guía de Tests Unitarios — JUnit 5 + Mockito
> Agente GitHub Copilot · Proyecto: `nuke-powerplant-back`

---

## Dependencias necesarias

Todas ya incluidas en tu `pom.xml`. No necesitas añadir nada.

```xml
<!-- JUnit 5 + Mockito incluidos via: -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<!-- Mockito explícito (también ya en tu pom) -->
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <scope>test</scope>
</dependency>

<!-- AssertJ (también ya en tu pom) -->
<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-core</artifactId>
    <version>3.22.0</version>
    <scope>test</scope>
</dependency>
```

---

## ¿Qué son y cuándo usarlos?

Los tests unitarios prueban **una sola clase en completo aislamiento**.
Todas sus dependencias se sustituyen por **mocks** (objetos falsos controlados por ti).

- ✅ Son muy rápidos (milisegundos)
- ✅ No levantan Spring, ni BD, ni servidor
- ✅ Ideales para lógica de negocio en `Service` y transformaciones en `Mapper`
- ❌ No detectan problemas de integración entre capas

```
[Test] → [Clase bajo prueba] → [Mock del repositorio / dependencia]
                                        ↑
                             Devuelve lo que tú programas
```

---

## Estructura de carpetas

```
src/
└── test/
    └── java/com/ihl95/nukepowerplant/
        ├── service/
        │   └── ReactorServiceTest.java
        ├── controller/
        │   └── ReactorControllerTest.java   ← con MockMvc standalone
        └── mapper/
            └── ReactorMapperTest.java
```

---

## Anotaciones clave

| Anotación | Descripción |
|---|---|
| `@ExtendWith(MockitoExtension.class)` | Activa Mockito en JUnit 5 sin Spring |
| `@Mock` | Crea un mock de una dependencia |
| `@InjectMocks` | Crea la clase bajo prueba e inyecta los mocks |
| `@Spy` | Mock parcial: ejecuta código real salvo lo que stubees |
| `@Captor` | Captura argumentos pasados a un mock |

---

## Test de un Service (NuclearPlant - ejemplo real)

```java
@ExtendWith(MockitoExtension.class)
class NuclearPlantServiceTest {

    @Mock
    private NuclearPlantRepository nuclearPlantRepository;

    @Mock
    private NuclearPlantCompleteMapper nuclearPlantMapper;

    @InjectMocks
    private NuclearPlantServiceImpl nuclearPlantService;

    // ── GET BY ID ────────────────────────────────────────────────

    @Test
    @DisplayName("findById → devuelve DTO cuando existe")
    void findById_shouldReturnDto_whenExists() {
        // ARRANGE
        Reactor entity = Reactor.builder().id(1L).name("Reactor-A").power(1200).build();
        ReactorDTO dto = new ReactorDTO(1L, "Reactor-A", 1200);

        when(reactorRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(reactorMapper.toDto(entity)).thenReturn(dto);

        // ACT
        ReactorDTO result = reactorService.findById(1L);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Reactor-A");
        verify(reactorRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("findById → lanza excepción cuando no existe")
    void findById_shouldThrow_whenNotFound() {
        when(reactorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reactorService.findById(99L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("99");
    }

    // ── CREATE ───────────────────────────────────────────────────

    @Test
    @DisplayName("create → guarda y devuelve DTO")
    void create_shouldSaveAndReturnDto() {
        // ARRANGE
        ReactorRequest request  = new ReactorRequest("Reactor-B", 800);
        Reactor        entity   = Reactor.builder().name("Reactor-B").power(800).build();
        Reactor        saved    = Reactor.builder().id(2L).name("Reactor-B").power(800).build();
        ReactorDTO     dto      = new ReactorDTO(2L, "Reactor-B", 800);

        when(reactorMapper.toEntity(request)).thenReturn(entity);
        when(reactorRepository.save(entity)).thenReturn(saved);
        when(reactorMapper.toDto(saved)).thenReturn(dto);

        // ACT
        ReactorDTO result = reactorService.create(request);

        // ASSERT
        assertThat(result.getId()).isEqualTo(2L);
        verify(reactorRepository).save(entity);
    }

    // ── UPDATE ───────────────────────────────────────────────────

    @Test
    @DisplayName("update → modifica campos y devuelve DTO")
    void update_shouldUpdateFields() {
        Reactor existing = Reactor.builder().id(1L).name("Reactor-A").power(1200).build();
        ReactorRequest request = new ReactorRequest("Reactor-A v2", 1500);

        when(reactorRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(reactorRepository.save(any(Reactor.class))).thenAnswer(i -> i.getArgument(0));
        when(reactorMapper.toDto(any())).thenAnswer(i -> {
            Reactor r = i.getArgument(0);
            return new ReactorDTO(r.getId(), r.getName(), r.getPower());
        });

        ReactorDTO result = reactorService.update(1L, request);

        assertThat(result.getName()).isEqualTo("Reactor-A v2");
        assertThat(result.getPower()).isEqualTo(1500);
    }

    // ── DELETE ───────────────────────────────────────────────────

    @Test
    @DisplayName("delete → llama a deleteById cuando existe")
    void delete_shouldCallRepository_whenExists() {
        when(reactorRepository.existsById(1L)).thenReturn(true);

        reactorService.delete(1L);

        verify(reactorRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("delete → lanza excepción cuando no existe")
    void delete_shouldThrow_whenNotFound() {
        when(reactorRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> reactorService.delete(99L))
            .isInstanceOf(ResourceNotFoundException.class);

        verify(reactorRepository, never()).deleteById(any());
    }
}
```

---

## Test de un Controller en aislamiento (sin Spring)

Útil para probar solo la lógica del controlador sin levantar contexto.

```java
@ExtendWith(MockitoExtension.class)
class ReactorControllerTest {

    @Mock
    private ReactorService reactorService;

    @InjectMocks
    private ReactorController reactorController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // MockMvc standalone: sin Spring, sin seguridad, ultrarrápido
        mockMvc = MockMvcBuilders
            .standaloneSetup(reactorController)
            .build();
    }

    @Test
    @DisplayName("GET /api/reactors/{id} → 200 con DTO")
    void getById_shouldReturn200() throws Exception {
        ReactorDTO dto = new ReactorDTO(1L, "Reactor-A", 1200);
        when(reactorService.findById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/reactors/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Reactor-A"));
    }

    @Test
    @DisplayName("GET /api/reactors/{id} → 404 cuando el servicio lanza excepción")
    void getById_shouldReturn404_whenNotFound() throws Exception {
        when(reactorService.findById(99L))
            .thenThrow(new ResourceNotFoundException("No encontrado"));

        mockMvc.perform(get("/api/reactors/99"))
            .andExpect(status().isNotFound());
    }
}
```

---

## Patrones Mockito más usados

```java
// Stub de retorno
when(mock.metodo(arg)).thenReturn(valor);

// Stub con cualquier argumento
when(mock.metodo(any(Tipo.class))).thenReturn(valor);

// Stub que lanza excepción
when(mock.metodo(arg)).thenThrow(new MiExcepcion());

// Verificar que se llamó exactamente N veces
verify(mock, times(1)).metodo(arg);

// Verificar que NUNCA se llamó
verify(mock, never()).metodo(any());

// Capturar el argumento con el que se llamó al mock
ArgumentCaptor<Reactor> captor = ArgumentCaptor.forClass(Reactor.class);
verify(mock).save(captor.capture());
assertThat(captor.getValue().getName()).isEqualTo("Reactor-A");

// Stub que ejecuta lógica propia (útil para save que devuelve la entidad)
when(mock.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
```

---

## Estructura AAA (obligatoria)

Cada test debe seguir siempre este patrón:

```java
@Test
void nombre_descriptivo_del_escenario() {
    // ARRANGE — prepara datos y stubs
    ...

    // ACT — ejecuta la acción bajo prueba
    var result = servicio.metodo(...);

    // ASSERT — verifica el resultado
    assertThat(result)...;
    verify(mock)...;
}
```

---

## Buenas prácticas

| Práctica | Por qué |
|---|---|
| Un `@Test` = un comportamiento | Si falla, sabes exactamente qué |
| `@DisplayName` en español claro | Los reportes los entiende todo el equipo |
| No usar `@SpringBootTest` aquí | Triplicaría el tiempo de ejecución |
| Verificar tanto el resultado como las interacciones | `assertThat` + `verify` |
| Nombrar: `metodo_escenario_resultadoEsperado` | Hace los tests autodocumentados |