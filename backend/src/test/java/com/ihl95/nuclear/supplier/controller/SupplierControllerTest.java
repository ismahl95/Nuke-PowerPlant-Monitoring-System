package com.ihl95.nuclear.supplier.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihl95.nuclear.supplier.application.dto.SupplierDTO;
import com.ihl95.nuclear.supplier.application.exception.SupplierException;
import com.ihl95.nuclear.supplier.application.service.SupplierServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for SupplierController.
 * Tests all endpoints with various scenarios: success, validation errors, and missing authentication.
 * Uses @SpringBootTest to load the full Spring context with H2 in-memory database.
 * Services are mocked with @MockBean to isolate the web layer.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("SupplierController Integration Tests")
class SupplierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SupplierServiceImpl supplierService;

    @Autowired
    private ObjectMapper objectMapper;

    private SupplierDTO supplierDTO;
    private SupplierDTO supplierDTO2;

    @BeforeEach
    void setUp() {
        supplierDTO = SupplierDTO.builder()
                .id(1L)
                .name("Proveedor A")
                .contact("proveedor.a@example.com")
                .phone("+34912345678")
                .build();

        supplierDTO2 = SupplierDTO.builder()
                .id(2L)
                .name("Proveedor B")
                .contact("proveedor.b@example.com")
                .phone("+34987654321")
                .build();
    }

    /**
     * Helper method to create a minimal SupplierDTO for testing.
     * Useful for reducing duplicated code in tests.
     */
    private SupplierDTO createSupplierDTO(String name, String contact, String phone) {
        return SupplierDTO.builder()
                .name(name)
                .contact(contact)
                .phone(phone)
                .build();
    }

    // ==================== GET /api/suppliers ====================

    @Test
    @WithMockUser
    @DisplayName("GET /api/suppliers - returns all suppliers when authenticated")
    void getAllSuppliers_WithAuth_ReturnsOk() throws Exception {
        List<SupplierDTO> suppliers = Arrays.asList(supplierDTO, supplierDTO2);
        when(supplierService.getAllSuppliers()).thenReturn(suppliers);

        mockMvc.perform(get("/api/suppliers")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Proveedor A"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Proveedor B"));
    }

    @Test
    @WithMockUser
    @DisplayName("GET /api/suppliers - returns empty list when no suppliers exist")
    void getAllSuppliers_EmptyList_ReturnsOk() throws Exception {
        when(supplierService.getAllSuppliers()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/suppliers")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @ParameterizedTest(name = "GET endpoint {0} without authentication returns 403")
    @ValueSource(strings = {"/api/suppliers", "/api/suppliers/1"})
    @DisplayName("GET /api/suppliers - requires authentication")
    void getAllEndpoints_WithoutAuth_ReturnsForbidden(String endpoint) throws Exception {
        mockMvc.perform(get(endpoint)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    // ==================== GET /api/suppliers/{id} ====================

    @Test
    @WithMockUser
    @DisplayName("GET /api/suppliers/{id} - returns supplier when authenticated and supplier exists")
    void getSupplierById_ExistingId_ReturnsOk() throws Exception {
        when(supplierService.getSupplierbyId(1L)).thenReturn(supplierDTO);

        mockMvc.perform(get("/api/suppliers/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Proveedor A"));
    }

    @Test
    @WithMockUser
    @DisplayName("GET /api/suppliers/{id} - returns 404 when supplier does not exist")
    void getSupplierById_NonExistingId_ReturnsNotFound() throws Exception {
        when(supplierService.getSupplierbyId(999L))
                .thenThrow(SupplierException.notFound("Supplier not found with id: 999"));

        mockMvc.perform(get("/api/suppliers/999")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // ==================== POST /api/suppliers ====================

    @Test
    @WithMockUser
    @DisplayName("POST /api/suppliers - creates new supplier with valid data")
    void createSupplier_ValidData_ReturnsCreated() throws Exception {
        SupplierDTO newSupplier = createSupplierDTO("Nuevo Proveedor", "nuevo@example.com", "+34912345678");
        SupplierDTO createdSupplier = SupplierDTO.builder()
                .id(3L)
                .name("Nuevo Proveedor")
                .contact("nuevo@example.com")
                .phone("+34912345678")
                .build();

        when(supplierService.createSupplier(any(SupplierDTO.class)))
                .thenReturn(createdSupplier);

        mockMvc.perform(post("/api/suppliers")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newSupplier)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.name").value("Nuevo Proveedor"));
    }

    @Test
    @WithMockUser
    @DisplayName("POST /api/suppliers - returns 400 when name is missing")
    void createSupplier_MissingName_ReturnsBadRequest() throws Exception {
        String invalidSupplierJson = "{\"contact\":\"test@example.com\",\"phone\":\"+34912345678\"}";

        mockMvc.perform(post("/api/suppliers")
                .contentType(APPLICATION_JSON)
                .content(invalidSupplierJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    @DisplayName("POST /api/suppliers - returns 400 when name is blank")
    void createSupplier_BlankName_ReturnsBadRequest() throws Exception {
        String invalidSupplierJson = "{\"name\":\"\",\"contact\":\"test@example.com\",\"phone\":\"+34912345678\"}";

        mockMvc.perform(post("/api/suppliers")
                .contentType(APPLICATION_JSON)
                .content(invalidSupplierJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    @DisplayName("POST /api/suppliers - returns 400 when contact is missing")
    void createSupplier_MissingContact_ReturnsBadRequest() throws Exception {
        String invalidSupplierJson = "{\"name\":\"Proveedor\",\"phone\":\"+34912345678\"}";

        mockMvc.perform(post("/api/suppliers")
                .contentType(APPLICATION_JSON)
                .content(invalidSupplierJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    @DisplayName("POST /api/suppliers - returns 400 when contact is not a valid email")
    void createSupplier_InvalidEmail_ReturnsBadRequest() throws Exception {
        String invalidSupplierJson = "{\"name\":\"Proveedor\",\"contact\":\"notanemail\",\"phone\":\"+34912345678\"}";

        mockMvc.perform(post("/api/suppliers")
                .contentType(APPLICATION_JSON)
                .content(invalidSupplierJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    @DisplayName("POST /api/suppliers - returns 400 when phone is missing")
    void createSupplier_MissingPhone_ReturnsBadRequest() throws Exception {
        String invalidSupplierJson = "{\"name\":\"Proveedor\",\"contact\":\"test@example.com\"}";

        mockMvc.perform(post("/api/suppliers")
                .contentType(APPLICATION_JSON)
                .content(invalidSupplierJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    @DisplayName("POST /api/suppliers - returns 400 when phone format is invalid")
    void createSupplier_InvalidPhoneFormat_ReturnsBadRequest() throws Exception {
        String invalidSupplierJson = "{\"name\":\"Proveedor\",\"contact\":\"test@example.com\",\"phone\":\"123\"}";

        mockMvc.perform(post("/api/suppliers")
                .contentType(APPLICATION_JSON)
                .content(invalidSupplierJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/suppliers - requires authentication")
    void createSupplier_WithoutAuth_ReturnsForbidden() throws Exception {
        mockMvc.perform(post("/api/suppliers")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(supplierDTO)))
                .andExpect(status().isForbidden());
    }

    // ==================== POST /api/suppliers/{id} (UPDATE) ====================

    @Test
    @WithMockUser
    @DisplayName("POST /api/suppliers/{id} - updates supplier with valid data")
    void updateSupplier_ValidData_ReturnsOk() throws Exception {
        SupplierDTO updateDTO = createSupplierDTO("Proveedor Actualizado", "actualizado@example.com", "+34987654321");

        SupplierDTO updatedSupplier = SupplierDTO.builder()
                .id(1L)
                .name("Proveedor Actualizado")
                .contact("actualizado@example.com")
                .phone("+34987654321")
                .build();

        when(supplierService.updateSupplier(eq(1L), any(SupplierDTO.class)))
                .thenReturn(updatedSupplier);

        mockMvc.perform(post("/api/suppliers/1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Proveedor Actualizado"));
    }

    @Test
    @WithMockUser
    @DisplayName("POST /api/suppliers/{id} - returns 404 when supplier does not exist")
    void updateSupplier_NonExistingId_ReturnsNotFound() throws Exception {
        when(supplierService.updateSupplier(eq(999L), any(SupplierDTO.class)))
                .thenThrow(SupplierException.notFound("Supplier not found with id: 999"));

        mockMvc.perform(post("/api/suppliers/999")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(supplierDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    @DisplayName("POST /api/suppliers/{id} - returns 400 when data is invalid")
    void updateSupplier_InvalidData_ReturnsBadRequest() throws Exception {
        String invalidSupplierJson = "{\"id\":1,\"name\":\"\",\"contact\":\"test@example.com\"}";

        mockMvc.perform(post("/api/suppliers/1")
                .contentType(APPLICATION_JSON)
                .content(invalidSupplierJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/suppliers/{id} - requires authentication")
    void updateSupplier_WithoutAuth_ReturnsForbidden() throws Exception {
        mockMvc.perform(post("/api/suppliers/1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(supplierDTO)))
                .andExpect(status().isForbidden());
    }

    // ==================== POST /api/suppliers/{id}/delete ====================

    @Test
    @WithMockUser
    @DisplayName("POST /api/suppliers/{id}/delete - deletes supplier when exists")
    void deleteSupplier_ExistingId_ReturnsNoContent() throws Exception {
        doNothing().when(supplierService).deleteSupplier(1L);

        mockMvc.perform(post("/api/suppliers/1/delete")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    @DisplayName("POST /api/suppliers/{id}/delete - returns 404 when supplier does not exist")
    void deleteSupplier_NonExistingId_ReturnsNotFound() throws Exception {
        doThrow(SupplierException.notFound("Supplier not found with id: 999"))
                .when(supplierService).deleteSupplier(999L);

        mockMvc.perform(post("/api/suppliers/999/delete")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/suppliers/{id}/delete - requires authentication")
    void deleteSupplier_WithoutAuth_ReturnsForbidden() throws Exception {
        mockMvc.perform(post("/api/suppliers/1/delete")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
