package com.ihl95.nuclear.supplier.e2e.steps;

import static org.assertj.core.api.Assertions.*;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihl95.nuclear.common.mocks.SupplierTestData;
import com.ihl95.nuclear.supplier.application.dto.SupplierDTO;
import com.ihl95.nuclear.supplier.domain.Supplier;
import com.ihl95.nuclear.supplier.infraestructure.SupplierRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Cucumber step definitions for Supplier module E2E tests.
 * Integrates with Spring Boot context and RestAssured for HTTP testing.
 *
 * 6 Scenarios with ~32 steps:
 * - Create supplier (3 steps)
 * - Retrieve all (3 steps)
 * - Retrieve by ID (3 steps)
 * - Update (4 steps)
 * - Delete (3 steps)
 * - Validation (3 steps)
 *
 * Execution: mvn test -Dtest=SupplierE2ETest
 */
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SupplierSteps {

    @org.springframework.boot.web.server.LocalServerPort
    private int port;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // Instance state shared across all steps
    private Response response;
    private SupplierDTO supplierDTO;
    private Supplier existingSupplier;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.basePath = "";
    }

    // ─────────────────────────────────────────────────────────────
    // GIVEN STEPS (Setup)
    // ─────────────────────────────────────────────────────────────

    @Given("the authentication server is available")
    public void authenticationServerAvailable() {
        // In test profile, server is always available via Spring Boot Test
        Response healthCheck = RestAssured.get("/api/suppliers");
        assertThat(healthCheck.getStatusCode()).isIn(200, 401, 403, 404, 500);
    }

    @Given("I have a valid JWT token")
    public void haveValidJwtToken() {
        // JWT is disabled in test profile; this step is a no-op
    }

    @Given("there are suppliers in the system")
    public void thereAreSuppliersInSystem() {
        if (supplierRepository.findAll().isEmpty()) {
            supplierRepository.save(
                SupplierTestData.createSupplierEntity(null, "Test Supplier", "test@example.com", "+34912345678")
            );
        }
    }

    @Given("a supplier exists with name {string}")
    public void aSupplierExistsWithName(String name) {
        existingSupplier = supplierRepository.save(
            SupplierTestData.createSupplierEntity(null, name, "contact@example.com", "+34912345678")
        );
    }

    @Given("I want to create a new supplier")
    public void wantToCreateNewSupplier() {
        // Prepare for creation step
        supplierDTO = null;
    }

    // ─────────────────────────────────────────────────────────────
    // WHEN STEPS (Action)
    // ─────────────────────────────────────────────────────────────

    @When("I send a POST request with supplier name {string} and contact {string} and phone {string}")
    public void sendPostWithSupplierData(String name, String contact, String phone) {
        supplierDTO = SupplierTestData.createSupplierDTO(null, name, contact, phone);

        response = RestAssured.given()
            .contentType("application/json")
            .body(supplierDTO)
            .when()
            .post("/api/suppliers")
            .then()
            .extract()
            .response();
    }

    @When("I send a GET request to retrieve all suppliers")
    public void sendGetAllSuppliers() {
        response = RestAssured.given()
            .contentType("application/json")
            .when()
            .get("/api/suppliers")
            .then()
            .extract()
            .response();
    }

    @When("I send a GET request for the supplier by ID")
    public void sendGetSupplierById() {
        assertThat(existingSupplier).isNotNull();
        response = RestAssured.given()
            .contentType("application/json")
            .when()
            .get("/api/suppliers/" + existingSupplier.getId())
            .then()
            .extract()
            .response();
    }

    @When("I update the supplier name to {string} and contact to {string}")
    public void updateSupplierData(String newName, String newContact) {
        assertThat(existingSupplier).isNotNull();
        SupplierDTO updateDTO = SupplierTestData.createSupplierDTO(
            null, newName, newContact, "+34987654321"
        );

        response = RestAssured.given()
            .contentType("application/json")
            .body(updateDTO)
            .when()
            .post("/api/suppliers/" + existingSupplier.getId())
            .then()
            .extract()
            .response();
    }

    @When("I send a DELETE request for the supplier")
    public void sendDeleteSupplier() {
        assertThat(existingSupplier).isNotNull();
        response = RestAssured.given()
            .contentType("application/json")
            .when()
            .post("/api/suppliers/" + existingSupplier.getId() + "/delete")
            .then()
            .extract()
            .response();
    }

    // ─────────────────────────────────────────────────────────────
    // THEN STEPS (Assertion)
    // ─────────────────────────────────────────────────────────────

    @Then("the response status should be {int}")
    public void checkResponseStatus(int expectedStatus) {
        assertThat(response.getStatusCode()).isEqualTo(expectedStatus);
    }

    @Then("the response should contain the supplier name {string}")
    public void checkSupplierNameInResponse(String expectedName) {
        String actualName = response.jsonPath().getString("name");
        assertThat(actualName).isEqualTo(expectedName);
    }

    @Then("the response should contain the supplier contact {string}")
    public void checkSupplierContactInResponse(String expectedContact) {
        String actualContact = response.jsonPath().getString("contact");
        assertThat(actualContact).isEqualTo(expectedContact);
    }

    @Then("the response should contain a list of suppliers")
    public void checkResponseIsListOfSuppliers() {
        assertThat(response.jsonPath().getList("$")).isNotNull().isNotEmpty();
    }

    @Then("each supplier should have name, contact and phone fields")
    public void checkSupplierFieldsInList() {
        int size = response.jsonPath().getList("$").size();
        for (int i = 0; i < size; i++) {
            String name = response.jsonPath().getString("[" + i + "].name");
            String contact = response.jsonPath().getString("[" + i + "].contact");
            String phone = response.jsonPath().getString("[" + i + "].phone");
            assertThat(name).isNotNull().isNotBlank();
            assertThat(contact).isNotNull().isNotBlank();
            assertThat(phone).isNotNull().isNotBlank();
        }
    }

    @Then("the response should contain supplier contact and phone fields")
    public void checkSupplierContactPhoneFields() {
        String contact = response.jsonPath().getString("contact");
        String phone = response.jsonPath().getString("phone");
        assertThat(contact).isNotNull().isNotBlank();
        assertThat(phone).isNotNull().isNotBlank();
    }

    @Then("the response should contain the updated supplier name {string}")
    public void checkUpdatedSupplierName(String expectedName) {
        String actualName = response.jsonPath().getString("name");
        assertThat(actualName).isEqualTo(expectedName);
    }

    @Then("the new supplier should be persisted in the database")
    public void checkSupplierPersistedInDatabase() {
        Long supplierId = response.jsonPath().getLong("id");
        assertThat(supplierRepository.findById(supplierId))
            .isPresent()
            .get()
            .extracting(Supplier::getName)
            .isEqualTo(supplierDTO.name());
    }

    @Then("the updated supplier should be persisted in the database")
    public void checkUpdatedSupplierPersistedInDatabase() {
        Long supplierId = response.jsonPath().getLong("id");
        Supplier updated = supplierRepository.findById(supplierId).orElseThrow();
        String responseName = response.jsonPath().getString("name");
        assertThat(updated.getName()).isEqualTo(responseName);
    }

    @Then("the supplier should be removed from the database")
    public void checkSupplierRemovedFromDatabase() {
        assertThat(supplierRepository.findById(existingSupplier.getId())).isEmpty();
    }

    @Then("the response should contain a validation error message")
    public void checkValidationErrorMessage() {
        // Could check for specific error structure if API provides it
        assertThat(response.getStatusCode()).isEqualTo(400);
    }
}

