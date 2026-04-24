# Guía de Tests E2E — Cucumber + Selenium / REST
> Agente GitHub Copilot · Proyecto: `nuke-powerplant-back`

---

## Dependencias necesarias

Estas dependencias **NO están en tu `pom.xml` actual**. Añádelas según el tipo de E2E que necesites.

### Opción A: E2E de API REST con Cucumber (recomendado para backend)

```xml
<!-- Cucumber JUnit Platform -->
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

<!-- RestAssured — cliente HTTP para E2E de API -->
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.4.0</version>
    <scope>test</scope>
</dependency>
```

### Opción B: E2E con interfaz web (si hay frontend)

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
    ├── java/com/ihl95/nukepowerplant/
    │   ├── e2e/
    │   │   ├── CucumberE2ERunner.java         ← Runner de Cucumber
    │   │   ├── steps/
    │   │   │   ├── AuthSteps.java             ← Pasos de autenticación
    │   │   │   └── ReactorSteps.java          ← Pasos del dominio
    │   │   └── config/
    │   │       └── CucumberSpringConfig.java  ← Configuración Spring + Cucumber
    └── resources/
        └── features/
            ├── auth.feature                   ← Escenarios de login/JWT
            └── reactor_crud.feature           ← Escenarios CRUD
```

---

## Runner de Cucumber

```java
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameters({
    @ConfigurationParameter(
        key = GLUE_PROPERTY_NAME,
        value = "com.ihl95.nukepowerplant.e2e"
    ),
    @ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "pretty, html:target/cucumber-reports/report.html"
    ),
    @ConfigurationParameter(
        key = FILTER_TAGS_PROPERTY_NAME,
        value = "not @skip"
    )
})
public class CucumberE2ERunner {
    // Solo configuración
}
```

---

## Configuración Spring + Cucumber

```java
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CucumberSpringConfig {
    // Levanta Spring Boot en un puerto aleatorio real
    // Los steps pueden @Autowired cualquier bean
}
```

---

## Feature files (Gherkin)

### `auth.feature`

```gherkin
# language: es
Feature: Autenticación con JWT

  Scenario: Login exitoso con credenciales válidas
    Given el usuario "admin" con contraseña "admin123" existe en el sistema
    When envío POST a "/api/auth/login" con:
      """
      { "username": "admin", "password": "admin123" }
      """
    Then el status de respuesta es 200
    And la respuesta contiene un campo "token" no vacío

  Scenario: Login fallido con contraseña incorrecta
    Given el usuario "admin" con contraseña "admin123" existe en el sistema
    When envío POST a "/api/auth/login" con:
      """
      { "username": "admin", "password": "wrongpassword" }
      """
    Then el status de respuesta es 401
```

---

### `reactor_crud.feature`

```gherkin
# language: es
Feature: Gestión CRUD de Reactores

  Background:
    Given estoy autenticado como "ADMIN"

  Scenario: Listar todos los reactores
    Given existe un reactor con nombre "Reactor-A" y potencia 1200
    When envío GET a "/api/reactors"
    Then el status de respuesta es 200
    And la respuesta es una lista con al menos 1 elemento

  Scenario: Obtener un reactor por ID existente
    Given existe un reactor con nombre "Reactor-B" y potencia 800
    When envío GET al reactor recién creado
    Then el status de respuesta es 200
    And el campo "name" tiene el valor "Reactor-B"
    And el campo "power" tiene el valor 800

  Scenario: Obtener un reactor con ID inexistente
    When envío GET a "/api/reactors/9999"
    Then el status de respuesta es 404

  Scenario: Crear un reactor válido
    When envío POST a "/api/reactors" con:
      """
      { "name": "Reactor-Nuevo", "power": 600, "status": "INACTIVE" }
      """
    Then el status de respuesta es 201
    And el campo "id" existe en la respuesta
    And el campo "name" tiene el valor "Reactor-Nuevo"

  Scenario Outline: Crear reactor con datos inválidos
    When envío POST a "/api/reactors" con:
      """
      { "name": "<name>", "power": <power>, "status": "<status>" }
      """
    Then el status de respuesta es 400

    Examples:
      | name         | power | status   |
      |              | 600   | INACTIVE |
      | Reactor-Test | -100  | INACTIVE |
      | Reactor-Test | 600   |          |

  Scenario: Actualizar un reactor existente
    Given existe un reactor con nombre "Reactor-Viejo" y potencia 500
    When envío PUT al reactor recién creado con:
      """
      { "name": "Reactor-Actualizado", "power": 900, "status": "ACTIVE" }
      """
    Then el status de respuesta es 200
    And el campo "name" tiene el valor "Reactor-Actualizado"

  Scenario: Eliminar un reactor existente
    Given existe un reactor con nombre "Reactor-A-Borrar" y potencia 300
    When envío DELETE al reactor recién creado
    Then el status de respuesta es 204
    And el reactor ya no existe en la base de datos

  @skip
  Scenario: Escenario pendiente de implementar
    Given alguna condición futura
    When ocurre algo
    Then debería pasar algo
```

---

## Step Definitions

### `CucumberSpringConfig.java` (contexto compartido)

```java
// Clase de estado compartido entre steps
@Component
@ScenarioScope
public class ScenarioContext {
    public String authToken;
    public Long lastCreatedId;
    public Response lastResponse; // io.restassured.response.Response
}
```

---

### `AuthSteps.java`

```java
@Component
public class AuthSteps {

    @Autowired
    private ScenarioContext ctx;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${local.server.port}")
    private int port;

    @Given("el usuario {string} con contraseña {string} existe en el sistema")
    public void userExists(String username, String password) {
        userRepository.save(User.builder()
            .username(username)
            .password(passwordEncoder.encode(password))
            .role("ROLE_ADMIN")
            .build());
    }

    @Given("estoy autenticado como {string}")
    public void authenticatedAs(String role) {
        // Crea usuario y hace login para obtener token real
        String username = role.toLowerCase() + "_test";
        String password = "password123";

        userRepository.save(User.builder()
            .username(username)
            .password(passwordEncoder.encode(password))
            .role("ROLE_" + role)
            .build());

        Response response = RestAssured
            .given().contentType("application/json")
            .body(Map.of("username", username, "password", password))
            .when().post("http://localhost:" + port + "/api/auth/login");

        ctx.authToken = response.jsonPath().getString("token");
    }
}
```

---

### `ReactorSteps.java`

```java
@Component
public class ReactorSteps {

    @Autowired
    private ScenarioContext ctx;

    @Autowired
    private ReactorRepository reactorRepository;

    @Value("${local.server.port}")
    private int port;

    private String baseUrl() {
        return "http://localhost:" + port;
    }

    // ── GIVEN ────────────────────────────────────────────────────

    @Given("existe un reactor con nombre {string} y potencia {int}")
    public void reactorExists(String name, int power) {
        Reactor saved = reactorRepository.save(
            Reactor.builder().name(name).power(power).status(ReactorStatus.ACTIVE).build()
        );
        ctx.lastCreatedId = saved.getId();
    }

    // ── WHEN ─────────────────────────────────────────────────────

    @When("envío GET a {string}")
    public void sendGet(String path) {
        ctx.lastResponse = RestAssured
            .given()
            .header("Authorization", "Bearer " + ctx.authToken)
            .when().get(baseUrl() + path);
    }

    @When("envío GET al reactor recién creado")
    public void sendGetLastCreated() {
        sendGet("/api/reactors/" + ctx.lastCreatedId);
    }

    @When("envío POST a {string} con:")
    public void sendPost(String path, String body) {
        ctx.lastResponse = RestAssured
            .given()
            .header("Authorization", "Bearer " + ctx.authToken)
            .contentType("application/json")
            .body(body)
            .when().post(baseUrl() + path);
        
        // Guardar ID si la respuesta fue exitosa
        if (ctx.lastResponse.statusCode() == 201) {
            ctx.lastCreatedId = ctx.lastResponse.jsonPath().getLong("id");
        }
    }

    @When("envío PUT al reactor recién creado con:")
    public void sendPutLastCreated(String body) {
        ctx.lastResponse = RestAssured
            .given()
            .header("Authorization", "Bearer " + ctx.authToken)
            .contentType("application/json")
            .body(body)
            .when().put(baseUrl() + "/api/reactors/" + ctx.lastCreatedId);
    }

    @When("envío DELETE al reactor recién creado")
    public void sendDeleteLastCreated() {
        ctx.lastResponse = RestAssured
            .given()
            .header("Authorization", "Bearer " + ctx.authToken)
            .when().delete(baseUrl() + "/api/reactors/" + ctx.lastCreatedId);
    }

    // ── THEN ─────────────────────────────────────────────────────

    @Then("el status de respuesta es {int}")
    public void checkStatus(int status) {
        assertThat(ctx.lastResponse.statusCode()).isEqualTo(status);
    }

    @Then("el campo {string} tiene el valor {string}")
    public void checkFieldString(String field, String value) {
        assertThat(ctx.lastResponse.jsonPath().getString(field)).isEqualTo(value);
    }

    @Then("el campo {string} tiene el valor {int}")
    public void checkFieldInt(String field, int value) {
        assertThat(ctx.lastResponse.jsonPath().getInt(field)).isEqualTo(value);
    }

    @Then("el campo {string} existe en la respuesta")
    public void checkFieldExists(String field) {
        assertThat(ctx.lastResponse.jsonPath().get(field)).isNotNull();
    }

    @Then("la respuesta es una lista con al menos {int} elemento")
    public void checkListSize(int minSize) {
        assertThat(ctx.lastResponse.jsonPath().getList("$")).hasSizeGreaterThanOrEqualTo(minSize);
    }

    @Then("la respuesta contiene un campo {string} no vacío")
    public void checkFieldNotEmpty(String field) {
        assertThat(ctx.lastResponse.jsonPath().getString(field)).isNotBlank();
    }

    @Then("el reactor ya no existe en la base de datos")
    public void checkReactorDeleted() {
        assertThat(reactorRepository.findById(ctx.lastCreatedId)).isEmpty();
    }
}
```

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