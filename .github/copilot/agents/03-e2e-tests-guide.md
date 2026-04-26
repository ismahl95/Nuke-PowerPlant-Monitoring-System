# Guía de Tests E2E — Cucumber + Selenium / REST
> Agente GitHub Copilot · Proyecto: `nuke-powerplant-back`

---

## Dependencias necesarias

✅ **Ya incluidas en tu pom.xml**. Verificadas en proyecto actual.

```xml
<!-- Cucumber JUnit Platform — OK en pom.xml -->
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-java</artifactId>
    <version>7.15.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-spring</artifactId>
    <version>7.15.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-junit-platform-engine</artifactId>
    <version>7.15.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.junit.platform</groupId>
    <artifactId>junit-platform-suite</artifactId>
    <scope>test</scope>
</dependency>

<!-- RestAssured — OK en pom.xml -->
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.4.0</version>
    <scope>test</scope>
</dependency>
```


```xml
<!-- Selenium WebDriver (solo si tienes UI web) -->
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.18.1</version>
    <scope>test</scope>
</dependency>

<!-- WebDriverManager — gestiona los drivers automáticamente -->
<dependency>
    <groupId>io.github.bonigarcia</groupId>
    <artifactId>webdrivermanager</artifactId>
    <version>5.7.0</version>
    <scope>test</scope>
</dependency>
```

---

## ¿Qué son y cuándo usarlos?

Los tests E2E prueban **flujos de negocio completos** desde el exterior de la aplicación,
exactamente como lo haría un usuario o cliente real de la API.

- ✅ Validan que todo el sistema funciona de extremo a extremo
- ✅ Escritos en lenguaje natural (Gherkin): entendibles por todo el equipo
- ✅ Sirven como **documentación viva** del comportamiento del sistema
- ⚠️ Son los más lentos (atacan la app real o levantada en test)
- ⚠️ Requieren más mantenimiento que los tests unitarios e integración
- ❌ No son adecuados para probar lógica de negocio detallada

```
[Feature .gherkin] → [Step Definitions] → [RestAssured / Selenium] → [App corriendo]
       ↑                                                                      ↑
  Lenguaje negocio                                               Servidor real o de test
```

---

## Cuándo usar cada tipo

| Escenario | Herramienta |
|---|---|
| Backend API sin UI | Cucumber + RestAssured |
| Backend API con Spring Boot Test | Cucumber + MockMvc |
| Frontend web con navegador | Cucumber + Selenium |
| Flujo crítico de negocio | Siempre con Cucumber (legibilidad) |

---

## Estructura de carpetas

```
src/
└── test/
    ├── java/com/ihl95/nuclear/
    │   ├── nuclearplant/
    │   │   ├── e2e/
    │   │   │   ├── NuclearPlantE2ETest.java              ← Runner de Cucumber (Maven auto-detect)
    │   │   │   ├── steps/
    │   │   │   │   └── NuclearPlantSteps.java            ← Pasos y configuración Spring (6 escenarios)
    │   │   │   └── README.md                             ← Documentación de E2E
    │   │   ├── service/ (11 unit tests)
    │   │   ├── mapper/ (7 mapper tests)
    │   │   └── controller/ (11 integration tests)
    │   ├── common/mocks/
    │   │   └── NuclearPlantTestData.java                 ← Factories compartidas
    │   └── reactor/ (similar estructura)
    └── resources/
        └── features/
            ├── nuclearplant.feature                      ← 6 escenarios CRUD (Gherkin)
            └── reactor.feature
```

**Nota**: 
- Cada módulo tiene su propia carpeta `/e2e` con Steps y Resource
- Una carpeta `/common/mocks` compartida con TestData factories
- El Runner DEBE terminar en `Test` para auto-detección Maven
- Los `.feature` files van en `src/test/resources/features/`

---

## Runner de Cucumber

```java
@Suite
@IncludeEngines("cucumber")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.ihl95.nuclear.nuclearplant.e2e.steps")
@ConfigurationParameter(key = Constants.FEATURES_PROPERTY_NAME, value = "classpath:features/nuclearplant.feature")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "progress,html:target/cucumber-report.html")
public class NuclearPlantE2ETest {
    // Suite configuration for running Cucumber features with Gherkin syntax
}
```

**Cómo ejecutar:**
```bash
# Todos los tests E2E de NuclearPlant
mvn test -Dtest=NuclearPlantE2ETest

# Solo tests unitarios + integration + E2E de NuclearPlant
mvn test -Dtest=NuclearPlant*
```

**Importante: El nombre de la clase DEBE terminar en "Test"** para que Maven Surefire la detecte automáticamente.

---

## Configuración Spring + Cucumber

```java
// En NuclearPlantSteps.java — MISMO ARCHIVO que los pasos (tanto @CucumberContextConfiguration como @Given/@When/@Then)
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class NuclearPlantSteps {
    
    @LocalServerPort
    private int port;

    @Autowired
    private NuclearPlantRepository nuclearPlantRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Response response;  // io.restassured.response.Response
    private NuclearPlant existingPlant;
    
    @Before  // Cucumber hook - ejecuta antes de CADA scenario
    public void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.basePath = "";
    }
    
    // ── GIVEN/WHEN/THEN steps aquí ──
    @Given("there are nuclear plants in the system")
    public void thereAreNuclearPlantsInSystem() { ... }
    
    @When("I send a POST request with plant name {string} and location {string}")
    public void sendPostWithPlantData(String name, String location) { ... }
    
    @Then("the response status should be {int}")
    public void checkResponseStatus(int expectedStatus) { ... }
}
```

**Ventajas de este patrón (Todo en UNA clase):**
- ✅ Levanta el servidor en un puerto real (`RANDOM_PORT`)
- ✅ Toda clase Spring injectable (`@Autowired`)
- ✅ Estado compartido eficiente entre steps con variables de instancia
- ✅ `@LocalServerPort` inyecta el puerto aleatorio
- ✅ RestAssured hace HTTP real contra servidor de test
- ✅ `@Before` hook se ejecuta automáticamente antes de cada scenario

**Configuración de seguridad en tests:**
En `application-test.properties`, Spring Boot detecta perfil "test" → `SecurityConfigurer` desactiva JWT
(ver `isTestProfile()` en SecurityConfigurer.java)

---

## Feature files (Gherkin)

### `nuclearplant.feature` — 6 Escenarios

```gherkin
Feature: NuclearPlant CRUD Operations
  As a system administrator
  I want to manage nuclear power plants
  So that I can keep track of multiple nuclear facilities

  Background:
    Given the authentication server is available
    And I have a valid JWT token

  Scenario: Create a new nuclear plant
    Given I want to create a new nuclear plant
    When I send a POST request with plant name "Planta Zaragoza" and location "Zaragoza"
    Then the response status should be 201
    And the response should contain the plant name "Planta Zaragoza"
    And the response should contain the plant location "Zaragoza"
    And the new plant should be persisted in the database

  Scenario: Retrieve all nuclear plants
    Given there are nuclear plants in the system
    When I send a GET request to retrieve all plants
    Then the response status should be 200
    And the response should contain a list of plants
    And each plant should have a name and location

  Scenario: Retrieve a specific nuclear plant by ID
    Given a nuclear plant exists with name "Planta Sevilla"
    When I send a GET request for the plant by ID
    Then the response status should be 200
    And the response should contain the plant name "Planta Sevilla"

  Scenario: Update an existing nuclear plant
    Given a nuclear plant exists with name "Planta Bilbao"
    When I update the plant name to "Planta Bilbao Updated" and location to "Bilbao Updated"
    Then the response status should be 200
    And the response should contain the updated plant name "Planta Bilbao Updated"
    And the updated plant should be persisted in the database

  Scenario: Delete a nuclear plant
    Given a nuclear plant exists with name "Planta Temporal"
    When I send a DELETE request for the plant
    Then the response status should be 204
    And the plant should be removed from the database

  Scenario: Reject creation with missing name
    Given I want to create a new nuclear plant
    When I send a POST request with missing name and location "Madrid"
    Then the response status should be 400
    And the response should contain validation error for name field
```

**Cada escenario:**
- ✅ Usa `Background:` para setup común (autenticación, servidor disponible)
- ✅ Prueba un flujo de negocio completo (no detalles de implementación)
- ✅ Es entendible por no-técnicos (product owners, QA)
- ✅ Vinculado con steps en `NuclearPlantSteps.java`

---

## Step Definitions

### Patrón en `NuclearPlantSteps.java` (Ejemplo real del proyecto)

**Estructura de la clase:**
```java
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class NuclearPlantSteps {
    
    // ── AUTOWIRED BEANS ──────────────────────────────────────
    @LocalServerPort
    private int port;

    @Autowired
    private NuclearPlantRepository nuclearPlantRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // ── INSTANCE STATE (compartido entre steps) ─────────────
    private Response response;
    private NuclearPlantDTO plantDTO;
    private NuclearPlant existingPlant;

    // ── LIFECYCLE HOOKS ──────────────────────────────────────
    @Before  // Cucumber hook: ejecuta antes de cada scenario
    public void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.basePath = "";
    }

    // ── GIVEN STEPS (Setup) ──────────────────────────────────
    @Given("the authentication server is available")
    public void authenticationServerAvailable() {
        // Verificar que el servidor está corriendo
        Response healthResponse = given()
            .when()
            .get("/")
            .then()
            .extract()
            .response();
        assertThat(healthResponse.getStatusCode())
            .satisfiesAnyOf(status -> assertThat(status).isIn(200, 302, 403, 404));
    }

    @Given("I have a valid JWT token")
    public void haveValidJwtToken() {
        // En test profile, JWT está desactivado: este paso es un no-op
    }

    @Given("there are nuclear plants in the system")
    public void thereAreNuclearPlantsInSystem() {
        if (nuclearPlantRepository.findAll().isEmpty()) {
            nuclearPlantRepository.save(
                NuclearPlantTestData.createNuclearPlantEntity(null, "Planta Test", "Location Test")
            );
        }
    }

    @Given("a nuclear plant exists with name {string}")
    public void aPlantExistsWithName(String name) {
        existingPlant = nuclearPlantRepository.save(
            NuclearPlantTestData.createNuclearPlantEntity(null, name, "Test Location")
        );
    }

    // ── WHEN STEPS (Action) ──────────────────────────────────
    @When("I send a POST request with plant name {string} and location {string}")
    public void sendPostWithPlantData(String name, String location) {
        plantDTO = NuclearPlantTestData.createNuclearPlantDTO(null, name, location);

        response = given()
            .contentType("application/json")
            .body(plantDTO)
            .when()
            .post("/api/nuclear-plants");
    }

    @When("I send a GET request to retrieve all plants")
    public void sendGetAll() {
        response = given()
            .contentType("application/json")
            .when()
            .get("/api/nuclear-plants");
    }

    // ── THEN STEPS (Assertion) ───────────────────────────────
    @Then("the response status should be {int}")
    public void checkResponseStatus(int expectedStatus) {
        assertThat(response.getStatusCode()).isEqualTo(expectedStatus);
    }

    @Then("the response should contain the plant name {string}")
    public void checkPlantNameInResponse(String expectedName) {
        assertThat(response.jsonPath().getString("name")).isEqualTo(expectedName);
    }

    @Then("the new plant should be persisted in the database")
    public void checkPlantPersisted() {
        Long plantId = response.jsonPath().getLong("id");
        assertThat(nuclearPlantRepository.findById(plantId)).isPresent();
    }
}
```

**Patrones clave:**
- `@Given` — Prepara el estado del sistema (datos en BD)
- `@When` — Ejecuta acciones HTTP reales (GET, POST, PUT, DELETE)
- `@Then` — Aserta sobre respuestas y verifica persistencia
- **Estado compartido** — Variables de instancia (`response`, `existingPlant`) accesibles entre todos los steps
- **Reflection de pasos** — El nombre del método y los parámetros {string} mapean automáticamente con Gherkin

---

## Diferencias clave con los tests de integración

| Aspecto | Tests de Integración | Tests E2E con Cucumber |
|---|---|---|
| Herramienta HTTP | MockMvc (simulado) | RestAssured (HTTP real) |
| Servidor | No levanta puerto real | Puerto real (`RANDOM_PORT`) |
| Lenguaje | Java puro | Gherkin + Java |
| Audiencia | Desarrolladores | Todo el equipo + negocio |
| Velocidad | Rápido | Más lento |
| Rollback BD | `@Transactional` automático | Manual (`@BeforeEach` / `deleteAll`) |
| Mejor para | Verificar capas | Documentar flujos de negocio |

---

## Generar reportes HTML

Con la configuración del Runner ya genera un reporte en `target/cucumber-reports/report.html`.
Para reportes más ricos, añade:

```xml
<dependency>
    <groupId>net.masterthought</groupId>
    <artifactId>cucumber-reporting</artifactId>
    <version>5.7.7</version>
    <scope>test</scope>
</dependency>
```

---

## Buenas prácticas

| Práctica | Por qué |
|---|---|
| `Background` para pasos comunes | Evita repetir "estoy autenticado" en cada escenario |
| `ScenarioContext` para estado compartido | Pasar datos entre steps sin variables globales |
| `@skip` para escenarios pendientes | El runner no falla, pero queda documentado |
| `Scenario Outline` + `Examples` para casos múltiples | DRY en validaciones de input |
| Un feature por agregado de dominio | `reactor.feature`, `sensor.feature`, `alert.feature` |
| Steps reutilizables y genéricos | `envío GET a {string}` vale para cualquier endpoint |