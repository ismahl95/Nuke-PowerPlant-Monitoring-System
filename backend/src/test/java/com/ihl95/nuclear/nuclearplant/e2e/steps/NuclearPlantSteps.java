package com.ihl95.nuclear.nuclearplant.e2e.steps;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihl95.nuclear.common.mocks.NuclearPlantTestData;
import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearplant.domain.NuclearPlant;
import com.ihl95.nuclear.nuclearplant.infraestructure.NuclearPlantRepository;
import com.ihl95.nuclear.security.AuthenticationRequest;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;

import io.cucumber.spring.CucumberContextConfiguration;

/**
 * Cucumber step definitions for NuclearPlant E2E tests.
 * Tests complete workflows using RestAssured for real HTTP requests.
 * Uses @SpringBootTest to load full application context during E2E testing.
 */
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

    private Response response;
    private NuclearPlantDTO plantDTO;
    private NuclearPlant existingPlant;
    private String jwtToken;
    private String baseUrl;

    @Before
    public void setUp() {
        baseUrl = "http://localhost:" + port;
        RestAssured.baseURI = baseUrl;
        RestAssured.basePath = "";
        // En ambiente de test, SimpleName usa token dummy
        // Los tests E2E no requieren autenticación real
        jwtToken = "test-token-dummy";
    }

    // ── Setup Steps ─────────────────────────────────────────────

    @Given("the authentication server is available")
    public void verificarServidorAutenticacion() {
        Response healthResponse = given()
                .when()
                .get("/")
                .then()
                .extract()
                .response();

        assertThat(healthResponse.getStatusCode()).satisfiesAnyOf(
                status -> assertThat(status).isIn(200, 302, 403, 404)
        );
    }

    @Given("I have a valid JWT token")
    public void tengoTokenValido() {
        // In test profile, authentication is disabled
        // This step is a no-op but kept for Gherkin compatibility
    }

    // ── Create Steps ─────────────────────────────────────────────

    @Given("I want to create a new nuclear plant")
    public void quieroCrearPlanta() {
        plantDTO = NuclearPlantTestData.createNuclearPlantDTO(null, "", "");
    }

    @When("I send a POST request with plant name {string} and location {string}")
    public void enviarPOSTConDatos(String name, String location) {
        plantDTO = NuclearPlantTestData.createNuclearPlantDTO(null, name, location);

        response = given()
                .contentType("application/json")
                .body(plantDTO)
                .when()
                .post("/api/nuclear-plants")
                .then()
                .extract()
                .response();
    }

    @When("I send a POST request with missing name and location {string}")
    public void enviarPOSTSinNombre(String location) {
        String invalidPayload = String.format("{\"location\":\"%s\"}", location);

        response = given()
                .contentType("application/json")
                .body(invalidPayload)
                .when()
                .post("/api/nuclear-plants")
                .then()
                .extract()
                .response();
    }

    // ── Retrieve Steps ──────────────────────────────────────────

    @Given("there are nuclear plants in the system")
    public void hayPlantasEnSistema() {
        if (nuclearPlantRepository.findAll().isEmpty()) {
            nuclearPlantRepository.save(
                    NuclearPlantTestData.createNuclearPlantEntity(null, "Planta Test", "Location Test")
            );
        }
    }

    @When("I send a GET request to retrieve all plants")
    public void enviarGETObtenerTodas() {
        response = given()
                .contentType("application/json")
                .when()
                .get("/api/nuclear-plants")
                .then()
                .extract()
                .response();
    }

    @Given("a nuclear plant exists with name {string}")
    public void existePlantaConNombre(String name) {
        existingPlant = nuclearPlantRepository.save(
                NuclearPlantTestData.createNuclearPlantEntity(null, name, "Test Location")
        );
    }

    @When("I send a GET request for the plant by ID")
    public void enviarGETObtenerPorID() {
        response = given()
                .contentType("application/json")
                .when()
                .get("/api/nuclear-plants/{id}", existingPlant.getId())
                .then()
                .extract()
                .response();
    }

    // ── Update Steps ────────────────────────────────────────────

    @When("I update the plant name to {string} and location to {string}")
    public void actualizarPlanta(String newName, String newLocation) {
        plantDTO = NuclearPlantTestData.createNuclearPlantDTO(
                existingPlant.getId(), newName, newLocation
        );

        response = given()
                .contentType("application/json")
                .body(plantDTO)
                .when()
                .put("/api/nuclear-plants/{id}", existingPlant.getId())
                .then()
                .extract()
                .response();
    }

    // ── Delete Steps ────────────────────────────────────────────

    @When("I send a DELETE request for the plant")
    public void enviarDELETE() {
        response = given()
                .contentType("application/json")
                .when()
                .delete("/api/nuclear-plants/{id}", existingPlant.getId())
                .then()
                .extract()
                .response();
    }

    // ── Assertion Steps ─────────────────────────────────────────

    @Then("the response status should be {int}")
    public void validarStatusCode(int expectedStatus) {
        assertThat(response.getStatusCode()).isEqualTo(expectedStatus);
    }

    @Then("the response should contain the plant name {string}")
    public void validarNombrePlanta(String expectedName) {
        String actualName = response.jsonPath().getString("name");
        assertThat(actualName).isEqualTo(expectedName);
    }

    @Then("the response should contain the plant location {string}")
    public void validarUbicacionPlanta(String expectedLocation) {
        String actualLocation = response.jsonPath().getString("location");
        assertThat(actualLocation).isEqualTo(expectedLocation);
    }

    @Then("the response should contain the updated plant name {string}")
    public void validarNombreActualizado(String expectedName) {
        String actualName = response.jsonPath().getString("name");
        assertThat(actualName).isEqualTo(expectedName);
    }

    @Then("the response should contain a list of plants")
    public void validarListaPlantes() {
        assertThat(response.jsonPath().getList("$")).isNotEmpty();
    }

    @Then("each plant should have a name and location")
    public void validarCamposPlanta() {
        assertThat(response.jsonPath().getList("name")).isNotEmpty();
        assertThat(response.jsonPath().getList("location")).isNotEmpty();
    }

    @Then("the response should contain validation error for name field")
    public void validarErrorValidacion() {
        // Spring Boot retorna map de errores de validación
        String responseBody = response.asString();
        assertThat(responseBody).contains("name");
    }

    @Then("the new plant should be persisted in the database")
    public void validarPersistencia() {
        Long plantId = response.jsonPath().getLong("id");
        assertThat(nuclearPlantRepository.findById(plantId)).isPresent();
    }

    @Then("the updated plant should be persisted in the database")
    public void validarActualizacionEnBD() {
        NuclearPlant updatedPlant = nuclearPlantRepository.findById(existingPlant.getId()).orElseThrow();
        String updatedName = response.jsonPath().getString("name");
        String updatedLocation = response.jsonPath().getString("location");
        assertThat(updatedPlant.getName()).isEqualTo(updatedName);
        assertThat(updatedPlant.getLocation()).isEqualTo(updatedLocation);
    }

    @Then("the plant should be removed from the database")
    public void validarEliminacion() {
        assertThat(nuclearPlantRepository.findById(existingPlant.getId())).isEmpty();
    }

    // ── Helper Methods ──────────────────────────────────────────

}












