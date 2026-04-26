package com.ihl95.nuclear.supplier.controller;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihl95.nuclear.common.mocks.SupplierTestData;
import com.ihl95.nuclear.supplier.application.dto.SupplierDTO;
import com.ihl95.nuclear.supplier.domain.Supplier;
import com.ihl95.nuclear.supplier.infraestructure.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for SupplierController.
 * Tests full request/response flow through all layers: Controller → Service → Repository.
 * Uses MockMvc with @SpringBootTest and H2 in-memory database.
 * Database is automatically rolled back after each test via @Transactional.
 *
 * 14 tests covering CRUD operations and validations:
 * - GET /api/suppliers (2 tests)
 * - GET /api/suppliers/{id} (2 tests)
 * - POST /api/suppliers (5 tests: creation + validations)
 * - PUT /api/suppliers/{id} (3 tests: update + validation)
 * - DELETE /api/suppliers/{id} (2 tests)
 *
 * Execution: mvn test -Dtest=SupplierControllerIntegrationTest
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@DisplayName("SupplierController Integration Tests")
class SupplierControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SupplierRepository supplierRepository;

    private Supplier existingSupplier;
    private static final String SUPPLIER_API = "/api/suppliers";

    @BeforeEach
    void setUp() {
        existingSupplier = supplierRepository.save(
            SupplierTestData.createSupplierEntity(
                null, "Existing Supplier", "existing@example.com", "+34912345678"
            )
        );
    }

    // ─────────────────────────────────────────────────────────────
    // GET ALL SUPPLIERS (2 tests)
    // ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/suppliers → 200 with supplier list when suppliers exist")
    void getAll_shouldReturn200_whenSuppliersExist() throws Exception {
        mockMvc.perform(get(SUPPLIER_API)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[0].name").value("Existing Supplier"))
            .andExpect(jsonPath("$[0].contact").value("existing@example.com"))
            .andExpect(jsonPath("$[0].phone").value("+34912345678"));
    }

    @Test
    @DisplayName("GET /api/suppliers → 200 with empty array when no suppliers exist")
    void getAll_shouldReturn200Empty_whenNoSuppliersExist() throws Exception {
        supplierRepository.deleteAll();

        mockMvc.perform(get(SUPPLIER_API)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    // ─────────────────────────────────────────────────────────────
    // GET SUPPLIER BY ID (2 tests)
    // ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/suppliers/{id} → 200 when supplier exists")
    void getById_shouldReturn200_whenSupplierExists() throws Exception {
        mockMvc.perform(get(SUPPLIER_API + "/{id}", existingSupplier.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(existingSupplier.getId()))
            .andExpect(jsonPath("$.name").value("Existing Supplier"))
            .andExpect(jsonPath("$.contact").value("existing@example.com"))
            .andExpect(jsonPath("$.phone").value("+34912345678"));
    }

    @Test
    @DisplayName("GET /api/suppliers/{id} → 404 when supplier not found")
    void getById_shouldReturn404_whenSupplierNotFound() throws Exception {
        mockMvc.perform(get(SUPPLIER_API + "/{id}", 9999L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    // ─────────────────────────────────────────────────────────────
    // CREATE SUPPLIER (5 tests: basic + validations)
    // ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/suppliers → 201 when valid supplier data")
    void create_shouldReturn201_whenValidData() throws Exception {
        SupplierDTO newSupplier = SupplierTestData.createSupplierDTO(
            null, "New Supplier", "new@example.com", "+34987654321"
        );

        mockMvc.perform(post(SUPPLIER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newSupplier)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name").value("New Supplier"))
            .andExpect(jsonPath("$.contact").value("new@example.com"))
            .andExpect(jsonPath("$.phone").value("+34987654321"));

        // Verify persistence in database
        assertThat(supplierRepository.findAll())
            .filteredOn(s -> s.getName().equals("New Supplier"))
            .hasSize(1);
    }

    @Test
    @DisplayName("POST /api/suppliers → 400 when name is blank")
    void create_shouldReturn400_whenNameBlank() throws Exception {
        SupplierDTO invalidSupplier = new SupplierDTO(
            null, "", "test@example.com", "+34912345678"
        );

        mockMvc.perform(post(SUPPLIER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSupplier)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/suppliers → 400 when email is invalid")
    void create_shouldReturn400_whenEmailInvalid() throws Exception {
        SupplierDTO invalidSupplier = new SupplierDTO(
            null, "Valid Name", "not-an-email", "+34912345678"
        );

        mockMvc.perform(post(SUPPLIER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSupplier)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/suppliers → 400 when phone format is invalid")
    void create_shouldReturn400_whenPhoneInvalid() throws Exception {
        SupplierDTO invalidSupplier = new SupplierDTO(
            null, "Valid Name", "test@example.com", "invalid-phone"
        );

        mockMvc.perform(post(SUPPLIER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSupplier)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/suppliers → 400 when contact is blank")
    void create_shouldReturn400_whenContactBlank() throws Exception {
        SupplierDTO invalidSupplier = new SupplierDTO(
            null, "Valid Name", "", "+34912345678"
        );

        mockMvc.perform(post(SUPPLIER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSupplier)))
            .andExpect(status().isBadRequest());
    }

    // ─────────────────────────────────────────────────────────────
    // UPDATE SUPPLIER (3 tests)
    // ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/suppliers/{id} → 200 when valid update data")
    void update_shouldReturn200_whenValidData() throws Exception {
        SupplierDTO updateDTO = SupplierTestData.createSupplierDTO(
            null, "Updated Supplier", "updated@example.com", "+34987654321"
        );

        mockMvc.perform(post(SUPPLIER_API + "/{id}", existingSupplier.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(existingSupplier.getId()))
            .andExpect(jsonPath("$.name").value("Updated Supplier"))
            .andExpect(jsonPath("$.contact").value("updated@example.com"))
            .andExpect(jsonPath("$.phone").value("+34987654321"));

        // Verify update in database
        Supplier updated = supplierRepository.findById(existingSupplier.getId()).orElseThrow();
        assertThat(updated.getName()).isEqualTo("Updated Supplier");
        assertThat(updated.getContact()).isEqualTo("updated@example.com");
    }

    @Test
    @DisplayName("POST /api/suppliers/{id} → 404 when supplier not found")
    void update_shouldReturn404_whenSupplierNotFound() throws Exception {
        SupplierDTO updateDTO = SupplierTestData.createSupplierDTO(
            null, "Ghost Supplier", "ghost@example.com", "+34912345678"
        );

        mockMvc.perform(post(SUPPLIER_API + "/{id}", 9999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/suppliers/{id} → 400 when invalid email in update")
    void update_shouldReturn400_whenInvalidEmail() throws Exception {
        SupplierDTO invalidUpdate = new SupplierDTO(
            null, "Updated Name", "not-an-email", "+34987654321"
        );

        mockMvc.perform(post(SUPPLIER_API + "/{id}", existingSupplier.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUpdate)))
            .andExpect(status().isBadRequest());
    }

    // ─────────────────────────────────────────────────────────────
    // DELETE SUPPLIER (2 tests)
    // ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/suppliers/{id}/delete → 204 when supplier exists")
    void delete_shouldReturn204_whenSupplierExists() throws Exception {
        mockMvc.perform(post(SUPPLIER_API + "/{id}/delete", existingSupplier.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Verify deletion in database
        assertThat(supplierRepository.findById(existingSupplier.getId())).isEmpty();
    }

    @Test
    @DisplayName("POST /api/suppliers/{id}/delete → 404 when supplier not found")
    void delete_shouldReturn404_whenSupplierNotFound() throws Exception {
        mockMvc.perform(post(SUPPLIER_API + "/{id}/delete", 9999L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }
}


