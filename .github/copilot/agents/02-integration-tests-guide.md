# Guía de Tests de Integración — JUnit 5 + @SpringBootTest
> Agente GitHub Copilot · Proyecto: `nuke-powerplant-back`

---

## Dependencias necesarias

La mayoría ya están en tu `pom.xml`. Solo verifica que tienes H2 y Spring Security Test.

```xml
<!-- Ya en tu pom — núcleo de tests de integración -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<!-- Ya en tu pom — BD en memoria para tests -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Ya en tu pom — para tests con seguridad JWT -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>

<!-- Ya en tu pom — aserciones fluidas -->
<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-core</artifactId>
    <version>3.22.0</version>
    <scope>test</scope>
</dependency>

<!-- Ya en tu pom — mocks para dependencias externas -->
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <scope>test</scope>
</dependency>
```

> **Para usar una BD real en tests (opcional):** añade Testcontainers si quieres
> probar contra PostgreSQL/MySQL real en lugar de H2.
> ```xml
> <dependency>
>     <groupId>org.testcontainers</groupId>
>     <artifactId>junit-jupiter</artifactId>
>     <version>1.19.3</version>
>     <scope>test</scope>
> </dependency>
> <dependency>
>     <groupId>org.testcontainers</groupId>
>     <artifactId>postgresql</artifactId>
>     <version>1.19.3</version>
>     <scope>test</scope>
> </dependency>
> ```

---

## ¿Qué son y cuándo usarlos?

Los tests de integración prueban **el flujo completo entre capas reales**:
Controller → Service → Repository → Base de datos.

- ✅ Detectan problemas reales de integración entre capas
- ✅ Prueban validaciones, mapeos, queries JPA y respuestas HTTP
- ✅ Usan H2 en memoria: rápidos y sin infraestructura externa
- ⚠️ Más lentos que los unitarios (levantan contexto Spring)
- ❌ No sustituyen a los tests unitarios para lógica de negocio compleja

```
MockMvc → Controller → Service → Repository → H2 (BD en memoria)
              ↑___________________________________________↑
                    Todo el stack real de Spring Boot
```

---

## Estructura de carpetas

```
src/
├── main/resources/
│   └── application.properties           ← configuración de producción
└── test/
    ├── java/com/ihl95/nuclear/
    │   ├── nuclearplant/
    │   │   └── controller/
    │   │       └── NuclearPlantControllerIntegrationTest.java  (11 tests)
    │   ├── reactor/ (similar)
    │   └── common/mocks/
    │       └── NuclearPlantTestData.java ← Factories reutilizables
    └── resources/
        └── application-test.properties  ← configuración de test con H2
```

---

## `application-test.properties`

```properties
# BD H2 en memoria
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# JWT — clave de test (no importa el valor, solo que exista)
jwt.secret=test-secret-key-for-integration-tests-only
jwt.expiration=86400000
```

---

## Anotaciones clave

| Anotación | Descripción |
|---|---|
| `@SpringBootTest` | Levanta el contexto completo de Spring Boot |
| `@AutoConfigureMockMvc` | Configura MockMvc automáticamente con el contexto |
| `@Transactional` | Rollback automático tras cada test: BD siempre limpia |
| `@ActiveProfiles("test")` | Activa `application-test.properties` con H2 |
| `@MockBean` | Reemplaza un bean de Spring por un mock de Mockito |
| `@WithMockUser` | Simula un usuario autenticado en tests con Spring Security |

---

## Configuración base del test

```java
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class NuclearPlantControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NuclearPlantRepository nuclearPlantRepository;

    private NuclearPlant existingPlant;

    @BeforeEach
    void setUp() {
        // Estado conocido y limpio antes de cada test
        existingPlant = nuclearPlantRepository.save(
            NuclearPlantTestData.createNuclearPlantEntity(null, "Planta Central", "Madrid")
        );
    }
}
```

---

## Desactivar autenticación en tests (patrón del proyecto)

En `SecurityConfigurer.java`, detectamos el perfil "test" para permitir acceso sin JWT:

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    boolean isTestProfile = isTestProfile();
    
    http.csrf().disable();
    
    if (isTestProfile) {
        // Test mode: allow all requests without authentication
        http.authorizeRequests().anyRequest().permitAll();
    } else {
        // Production mode: require JWT authentication
        http.authorizeRequests()
            .antMatchers("/api/auth/authenticate").permitAll()
            .antMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
    
    return http.build();
}
```

Esto permite que los tests de integración y E2E corran sin complicaciones de JWT.

---

## Tests CRUD completos

### GET ALL

```java
@Test
@DisplayName("GET /api/reactors → 200 con lista de reactores")
@WithMockUser(roles = "ADMIN")
void getAll_shouldReturnList() throws Exception {
    mockMvc.perform(get("/api/reactors")
            .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
        .andExpect(jsonPath("$[0].name").value("Reactor-A"));
}

@Test
@DisplayName("GET /api/reactors → 401 sin autenticación")
void getAll_shouldReturn401_whenNotAuthenticated() throws Exception {
    mockMvc.perform(get("/api/reactors"))
        .andExpect(status().isUnauthorized());
}
```

---

### GET BY ID

```java
@Test
@DisplayName("GET /api/reactors/{id} → 200 cuando existe")
@WithMockUser(roles = "ADMIN")
void getById_shouldReturnReactor_whenExists() throws Exception {
    mockMvc.perform(get("/api/reactors/{id}", existingReactor.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(existingReactor.getId()))
        .andExpect(jsonPath("$.name").value("Reactor-A"))
        .andExpect(jsonPath("$.power").value(1200));
}

@Test
@DisplayName("GET /api/reactors/{id} → 404 cuando no existe")
@WithMockUser(roles = "ADMIN")
void getById_shouldReturn404_whenNotFound() throws Exception {
    mockMvc.perform(get("/api/reactors/{id}", 9999L))
        .andExpect(status().isNotFound());
}
```

---

### POST — Crear

```java
@Test
@DisplayName("POST /api/reactors → 201 con reactor creado")
@WithMockUser(roles = "ADMIN")
void create_shouldPersistAndReturn201() throws Exception {
    ReactorRequest request = new ReactorRequest("Reactor-B", 800, ReactorStatus.INACTIVE);

    mockMvc.perform(post("/api/reactors")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").value("Reactor-B"));

    // Verificar persistencia real en BD
    assertThat(reactorRepository.findAll())
        .extracting(Reactor::getName)
        .contains("Reactor-B");
}

@Test
@DisplayName("POST /api/reactors → 400 con datos inválidos")
@WithMockUser(roles = "ADMIN")
void create_shouldReturn400_whenInvalidRequest() throws Exception {
    ReactorRequest invalid = new ReactorRequest("", null, null);

    mockMvc.perform(post("/api/reactors")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalid)))
        .andExpect(status().isBadRequest());
}

@Test
@DisplayName("POST /api/reactors → 403 sin rol ADMIN")
@WithMockUser(roles = "USER")
void create_shouldReturn403_whenNotAdmin() throws Exception {
    ReactorRequest request = new ReactorRequest("Reactor-B", 800, ReactorStatus.INACTIVE);

    mockMvc.perform(post("/api/reactors")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isForbidden());
}
```

---

### PUT — Actualizar

```java
@Test
@DisplayName("PUT /api/reactors/{id} → 200 con datos actualizados")
@WithMockUser(roles = "ADMIN")
void update_shouldModifyAndReturn200() throws Exception {
    ReactorRequest update = new ReactorRequest("Reactor-A v2", 1500, ReactorStatus.ACTIVE);

    mockMvc.perform(put("/api/reactors/{id}", existingReactor.getId())
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(update)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Reactor-A v2"))
        .andExpect(jsonPath("$.power").value(1500));

    // Confirmar cambio real en BD
    Reactor updated = reactorRepository.findById(existingReactor.getId()).orElseThrow();
    assertThat(updated.getName()).isEqualTo("Reactor-A v2");
    assertThat(updated.getPower()).isEqualTo(1500);
}

@Test
@DisplayName("PUT /api/reactors/{id} → 404 cuando no existe")
@WithMockUser(roles = "ADMIN")
void update_shouldReturn404_whenNotFound() throws Exception {
    ReactorRequest request = new ReactorRequest("Fantasma", 100, ReactorStatus.INACTIVE);

    mockMvc.perform(put("/api/reactors/{id}", 9999L)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isNotFound());
}
```

---

### DELETE

```java
@Test
@DisplayName("DELETE /api/reactors/{id} → 204 eliminado correctamente")
@WithMockUser(roles = "ADMIN")
void delete_shouldRemoveReactor() throws Exception {
    mockMvc.perform(delete("/api/reactors/{id}", existingReactor.getId()))
        .andExpect(status().isNoContent());

    // Confirmar eliminación real en BD
    assertThat(reactorRepository.findById(existingReactor.getId())).isEmpty();
}

@Test
@DisplayName("DELETE /api/reactors/{id} → 404 cuando no existe")
@WithMockUser(roles = "ADMIN")
void delete_shouldReturn404_whenNotFound() throws Exception {
    mockMvc.perform(delete("/api/reactors/{id}", 9999L))
        .andExpect(status().isNotFound());
}
```

---

## Tests con JWT real (sin @WithMockUser)

Si quieres probar con el token JWT real de tu aplicación:

```java
@Autowired
private JwtService jwtService; // o como se llame tu clase JWT

private String generateAdminToken() {
    return "Bearer " + jwtService.generateToken(
        User.builder()
            .username("admin")
            .roles(List.of("ROLE_ADMIN"))
            .build()
    );
}

@Test
@DisplayName("GET /api/reactors con JWT real → 200")
void getAll_withRealJwt_shouldReturn200() throws Exception {
    mockMvc.perform(get("/api/reactors")
            .header("Authorization", generateAdminToken()))
        .andExpect(status().isOk());
}
```

---

## Mockear dependencias externas con @MockBean

Cuando tu servicio llama a un sistema externo (email, mensajería, API externa):

```java
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class ReactorControllerIntegrationTest {

    @MockBean
    private NotificationService notificationService; // evita emails reales en tests

    @Test
    @DisplayName("POST /api/reactors → notifica al crear")
    @WithMockUser(roles = "ADMIN")
    void create_shouldTriggerNotification() throws Exception {
        ReactorRequest request = new ReactorRequest("Reactor-C", 600, ReactorStatus.ACTIVE);

        mockMvc.perform(post("/api/reactors")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());

        verify(notificationService, times(1)).notifyCreation(any());
    }
}
```

---

## Qué verificar en cada test

| Tipo de verificación | Cómo |
|---|---|
| Status HTTP | `.andExpect(status().isOk())` |
| Campo del JSON | `.andExpect(jsonPath("$.name").value("X"))` |
| Array no vacío | `.andExpect(jsonPath("$", hasSize(greaterThan(0))))` |
| Persistencia en BD | `assertThat(repository.findById(id)).isPresent()` |
| Eliminación en BD | `assertThat(repository.findById(id)).isEmpty()` |
| Llamada a dependencia | `verify(mockBean, times(1)).metodo(any())` |

---

## Buenas prácticas

| Práctica | Por qué |
|---|---|
| `@Transactional` en la clase | Rollback automático, BD limpia sin `deleteAll()` |
| `@BeforeEach` con datos conocidos | Tests independientes y predecibles |
| Verificar en el repositorio además del response | Confirmas persistencia real |
| Testear autenticación y autorización | Tu app tiene JWT: es parte del contrato |
| Un escenario por test | Si falla, sabes exactamente qué y por qué |