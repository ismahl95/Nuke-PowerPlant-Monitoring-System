package com.ihl95.nuclear.nuclearplant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihl95.nuclear.nuclearplant.application.controller.NuclearPlantController;
import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearplant.application.service.NuclearPlantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for NuclearPlantController.
 *
 * Tests the HTTP endpoints of the NuclearPlantController with:
 * - Full Spring Boot context (@SpringBootTest)
 * - Mocked NuclearPlantService to isolate controller behavior
 * - Security testing with @WithMockUser and unauthorized requests
 * - All CRUD operations with success and error scenarios
 *
 * This test class uses the "test" profile which configures an H2 in-memory database.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NuclearPlantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NuclearPlantService nuclearPlantService;

    @Autowired
    private ObjectMapper objectMapper;

    private NuclearPlantDTO plantDTO;
    private NuclearPlantDTO plantDTO2;

    @BeforeEach
    void setUp() {
        plantDTO = createPlantDTO(1L, "Planta Nuclear Central", "Madrid");
        plantDTO2 = createPlantDTO(2L, "Planta Nuclear Norte", "Barcelona");
    }

    /**
     * Helper method to create a NuclearPlantDTO with specified values.
     *
     * @param id       the plant ID
     * @param name     the plant name
     * @param location the plant location
     * @return a configured NuclearPlantDTO
     */
    private NuclearPlantDTO createPlantDTO(Long id, String name, String location) {
        return NuclearPlantDTO.builder()
                .id(id)
                .name(name)
                .location(location)
                .build();
    }

    // ==================== GET /api/nuclear-plants ====================

    @Test
    @WithMockUser
    @DisplayName("GET /api/nuclear-plants - Should return all plants when authenticated")
    void getAllNuclearPlants_WithAuth_ReturnsOk() throws Exception {
        List<NuclearPlantDTO> plants = Arrays.asList(plantDTO, plantDTO2);
        when(nuclearPlantService.getAllNuclearPlants()).thenReturn(plants);

        mockMvc.perform(get("/api/nuclear-plants")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Planta Nuclear Central"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Planta Nuclear Norte"));
    }


    @Test
    @WithMockUser
    @DisplayName("GET /api/nuclear-plants - Should return empty list when no plants exist")
    void getAllNuclearPlants_EmptyList_ReturnsOk() throws Exception {
        when(nuclearPlantService.getAllNuclearPlants()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/nuclear-plants")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // ==================== GET /api/nuclear-plants/{id} ====================

    @Test
    @WithMockUser
    @DisplayName("GET /api/nuclear-plants/{id} - Should return plant when it exists")
    void getNuclearPlantById_ExistingId_ReturnsOk() throws Exception {
        when(nuclearPlantService.getNuclearPlantById(1L)).thenReturn(plantDTO);

        mockMvc.perform(get("/api/nuclear-plants/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Planta Nuclear Central"));
    }

    @Test
    @WithMockUser
    @DisplayName("GET /api/nuclear-plants/{id} - Should return 500 when plant not found")
    void getNuclearPlantById_NonExistingId_ReturnsInternalServerError() throws Exception {
        when(nuclearPlantService.getNuclearPlantById(999L))
                .thenThrow(new RuntimeException("Nuclear plant not found"));

        mockMvc.perform(get("/api/nuclear-plants/999")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }


    // ==================== POST /api/nuclear-plants ====================

    @Test
    @WithMockUser
    @DisplayName("POST /api/nuclear-plants - Should create plant with valid data")
    void createNuclearPlant_ValidData_ReturnsCreated() throws Exception {
        NuclearPlantDTO newPlant = createPlantDTO(null, "Nueva Planta", "Valencia");
        NuclearPlantDTO createdPlant = createPlantDTO(3L, "Nueva Planta", "Valencia");

        when(nuclearPlantService.createNuclearPlant(any(NuclearPlantDTO.class)))
                .thenReturn(createdPlant);

        mockMvc.perform(post("/api/nuclear-plants")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPlant)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.name").value("Nueva Planta"));
    }

    @Test
    @WithMockUser
    @DisplayName("POST /api/nuclear-plants - Should reject when name is missing")
    void createNuclearPlant_MissingName_ReturnsBadRequest() throws Exception {
        String invalidPlantJson = "{\"location\":\"Valencia\"}";

        mockMvc.perform(post("/api/nuclear-plants")
                .contentType(APPLICATION_JSON)
                .content(invalidPlantJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    @DisplayName("POST /api/nuclear-plants - Should reject when name is blank")
    void createNuclearPlant_BlankName_ReturnsBadRequest() throws Exception {
        String invalidPlantJson = "{\"name\":\"\",\"location\":\"Valencia\"}";

        mockMvc.perform(post("/api/nuclear-plants")
                .contentType(APPLICATION_JSON)
                .content(invalidPlantJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    @DisplayName("POST /api/nuclear-plants - Should reject when location is missing")
    void createNuclearPlant_MissingLocation_ReturnsBadRequest() throws Exception {
        String invalidPlantJson = "{\"name\":\"Nueva Planta\"}";

        mockMvc.perform(post("/api/nuclear-plants")
                .contentType(APPLICATION_JSON)
                .content(invalidPlantJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    @DisplayName("POST /api/nuclear-plants - Should reject when location is blank")
    void createNuclearPlant_BlankLocation_ReturnsBadRequest() throws Exception {
        String invalidPlantJson = "{\"name\":\"Nueva Planta\",\"location\":\"\"}";

        mockMvc.perform(post("/api/nuclear-plants")
                .contentType(APPLICATION_JSON)
                .content(invalidPlantJson))
                .andExpect(status().isBadRequest());
    }


    // ==================== PUT /api/nuclear-plants/{id} ====================

    @Test
    @WithMockUser
    @DisplayName("PUT /api/nuclear-plants/{id} - Should update plant with valid data")
    void updateNuclearPlant_ValidData_ReturnsOk() throws Exception {
        NuclearPlantDTO updateDTO = createPlantDTO(1L, "Planta Actualizada", "Madrid");

        when(nuclearPlantService.updateNuclearPlant(eq(1L), any(NuclearPlantDTO.class)))
                .thenReturn(updateDTO);

        mockMvc.perform(put("/api/nuclear-plants/1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Planta Actualizada"));
    }

    @Test
    @WithMockUser
    @DisplayName("PUT /api/nuclear-plants/{id} - Should return 500 when plant not found")
    void updateNuclearPlant_NonExistingId_ReturnsInternalServerError() throws Exception {
        when(nuclearPlantService.updateNuclearPlant(eq(999L), any(NuclearPlantDTO.class)))
                .thenThrow(new RuntimeException("Nuclear plant not found"));

        mockMvc.perform(put("/api/nuclear-plants/999")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(plantDTO)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser
    @DisplayName("PUT /api/nuclear-plants/{id} - Should handle partial data")
    void updateNuclearPlant_PartialData_ReturnsOk() throws Exception {
        String partialJson = "{\"location\":\"Madrid\"}";

        mockMvc.perform(put("/api/nuclear-plants/1")
                .contentType(APPLICATION_JSON)
                .content(partialJson))
                .andExpect(status().isOk());
    }


    // ==================== DELETE /api/nuclear-plants/{id} ====================

    @Test
    @WithMockUser
    @DisplayName("DELETE /api/nuclear-plants/{id} - Should delete plant successfully")
    void deleteNuclearPlant_ExistingId_ReturnsNoContent() throws Exception {
        doNothing().when(nuclearPlantService).deleteNuclearPlant(1L);

        mockMvc.perform(delete("/api/nuclear-plants/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    @DisplayName("DELETE /api/nuclear-plants/{id} - Should return 500 when plant not found")
    void deleteNuclearPlant_NonExistingId_ReturnsInternalServerError() throws Exception {
        doThrow(new RuntimeException("Nuclear plant not found"))
                .when(nuclearPlantService).deleteNuclearPlant(999L);

        mockMvc.perform(delete("/api/nuclear-plants/999")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    // ==================== Tests for unauthenticated requests ====================

    /**
     * Provides endpoint information for parameterized authentication tests.
     * Each test verifies that unauthenticated requests return 403 Forbidden.
     */
    static Stream<Object[]> provideUnauthenticatedEndpoints() {
        return Stream.of(
                new Object[]{"GET", "/api/nuclear-plants"},
                new Object[]{"GET", "/api/nuclear-plants/1"},
                new Object[]{"POST", "/api/nuclear-plants"},
                new Object[]{"PUT", "/api/nuclear-plants/1"},
                new Object[]{"DELETE", "/api/nuclear-plants/1"}
        );
    }

    @ParameterizedTest
    @MethodSource("provideUnauthenticatedEndpoints")
    @DisplayName("All endpoints - Should return 403 Forbidden when not authenticated")
    void allEndpoints_WithoutAuth_ReturnsForbidden(String method, String endpoint) throws Exception {
        var request = switch (method) {
            case "GET" -> get(endpoint);
            case "POST" -> post(endpoint).content(objectMapper.writeValueAsString(plantDTO));
            case "PUT" -> put(endpoint).content(objectMapper.writeValueAsString(plantDTO));
            case "DELETE" -> delete(endpoint);
            default -> throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        };

        mockMvc.perform(request.contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}

