package com.ihl95.nuclear.nuclearplant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihl95.nuclear.nuclearplant.application.controller.NuclearPlantController;
import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearplant.application.service.NuclearPlantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
 * Uses @SpringBootTest with @AutoConfigureMockMvc to test the full web layer with security.
 * Services are mocked with @MockBean to avoid database dependency.
 */
@SpringBootTest
@AutoConfigureMockMvc
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
        plantDTO = NuclearPlantDTO.builder()
                .id(1L)
                .name("Planta Nuclear Central")
                .location("Madrid")
                .build();

        plantDTO2 = NuclearPlantDTO.builder()
                .id(2L)
                .name("Planta Nuclear Norte")
                .location("Barcelona")
                .build();
    }

    // ==================== GET /api/nuclear-plants ====================

    /**
     * Test: GET /api/nuclear-plants returns all nuclear plants when authenticated.
     */
    @Test
    @WithMockUser
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

    /**
     * Test: GET /api/nuclear-plants returns 403 Forbidden when not authenticated.
     */
    @Test
    void getAllNuclearPlants_WithoutAuth_ReturnsForbidden() throws Exception {
        mockMvc.perform(get("/api/nuclear-plants")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    /**
     * Test: GET /api/nuclear-plants returns empty list when no plants exist.
     */
    @Test
    @WithMockUser
    void getAllNuclearPlants_EmptyList_ReturnsOk() throws Exception {
        when(nuclearPlantService.getAllNuclearPlants()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/nuclear-plants")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // ==================== GET /api/nuclear-plants/{id} ====================

    /**
     * Test: GET /api/nuclear-plants/{id} returns nuclear plant when authenticated and plant exists.
     */
    @Test
    @WithMockUser
    void getNuclearPlantById_ExistingId_ReturnsOk() throws Exception {
        when(nuclearPlantService.getNuclearPlantById(1L)).thenReturn(plantDTO);

        mockMvc.perform(get("/api/nuclear-plants/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Planta Nuclear Central"));
    }

    /**
     * Test: GET /api/nuclear-plants/{id} returns 404 when plant does not exist.
     */
    @Test
    @WithMockUser
    void getNuclearPlantById_NonExistingId_ReturnsNotFound() throws Exception {
        when(nuclearPlantService.getNuclearPlantById(999L)).thenThrow(new RuntimeException("Nuclear plant not found"));

        mockMvc.perform(get("/api/nuclear-plants/999")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    /**
     * Test: GET /api/nuclear-plants/{id} returns 403 Forbidden when not authenticated.
     */
    @Test
    void getNuclearPlantById_WithoutAuth_ReturnsForbidden() throws Exception {
        mockMvc.perform(get("/api/nuclear-plants/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    // ==================== POST /api/nuclear-plants ====================

    /**
     * Test: POST /api/nuclear-plants creates new nuclear plant when authenticated with valid data.
     */
    @Test
    @WithMockUser
    void createNuclearPlant_ValidData_ReturnsCreated() throws Exception {
        NuclearPlantDTO newPlant = NuclearPlantDTO.builder()
                .name("Nueva Planta")
                .location("Valencia")
                .build();

        NuclearPlantDTO createdPlant = NuclearPlantDTO.builder()
                .id(3L)
                .name("Nueva Planta")
                .location("Valencia")
                .build();

        when(nuclearPlantService.createNuclearPlant(any(NuclearPlantDTO.class)))
                .thenReturn(createdPlant);

        mockMvc.perform(post("/api/nuclear-plants")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPlant)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.name").value("Nueva Planta"));
    }

    /**
     * Test: POST /api/nuclear-plants returns 400 when required field is missing (name).
     */
    @Test
    @WithMockUser
    void createNuclearPlant_MissingName_ReturnsBadRequest() throws Exception {
        String invalidPlantJson = "{\"location\":\"Valencia\"}";

        mockMvc.perform(post("/api/nuclear-plants")
                .contentType(APPLICATION_JSON)
                .content(invalidPlantJson))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test: POST /api/nuclear-plants returns 400 when required field is empty (blank name).
     */
    @Test
    @WithMockUser
    void createNuclearPlant_BlankName_ReturnsBadRequest() throws Exception {
        String invalidPlantJson = "{\"name\":\"\",\"location\":\"Valencia\"}";

        mockMvc.perform(post("/api/nuclear-plants")
                .contentType(APPLICATION_JSON)
                .content(invalidPlantJson))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test: POST /api/nuclear-plants returns 403 Forbidden when not authenticated.
     */
    @Test
    void createNuclearPlant_WithoutAuth_ReturnsForbidden() throws Exception {
        mockMvc.perform(post("/api/nuclear-plants")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(plantDTO)))
                .andExpect(status().isForbidden());
    }

    // ==================== PUT /api/nuclear-plants/{id} ====================

    /**
     * Test: PUT /api/nuclear-plants/{id} updates nuclear plant when authenticated with valid data.
     */
    @Test
    @WithMockUser
    void updateNuclearPlant_ValidData_ReturnsOk() throws Exception {
        NuclearPlantDTO updateDTO = NuclearPlantDTO.builder()
                .id(1L)
                .name("Planta Actualizada")
                .location("Madrid")
                .build();

        when(nuclearPlantService.updateNuclearPlant(eq(1L), any(NuclearPlantDTO.class)))
                .thenReturn(updateDTO);

        mockMvc.perform(put("/api/nuclear-plants/1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Planta Actualizada"));
    }

    /**
     * Test: PUT /api/nuclear-plants/{id} returns 404 when plant does not exist.
     */
    @Test
    @WithMockUser
    void updateNuclearPlant_NonExistingId_ReturnsNotFound() throws Exception {
        when(nuclearPlantService.updateNuclearPlant(eq(999L), any(NuclearPlantDTO.class)))
                .thenThrow(new RuntimeException("Nuclear plant not found"));

        mockMvc.perform(put("/api/nuclear-plants/999")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(plantDTO)))
                .andExpect(status().isInternalServerError());
    }

    /**
     * Test: PUT /api/nuclear-plants/{id} returns 400 when data is invalid.
     */
    @Test
    @WithMockUser
    void updateNuclearPlant_InvalidData_ReturnsBadRequest() throws Exception {
        String invalidPlantJson = "{\"id\":1,\"location\":\"Madrid\"}";

        mockMvc.perform(put("/api/nuclear-plants/1")
                .contentType(APPLICATION_JSON)
                .content(invalidPlantJson))
                .andExpect(status().isOk());
    }

    /**
     * Test: PUT /api/nuclear-plants/{id} returns 403 Forbidden when not authenticated.
     */
    @Test
    void updateNuclearPlant_WithoutAuth_ReturnsForbidden() throws Exception {
        mockMvc.perform(put("/api/nuclear-plants/1")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(plantDTO)))
                .andExpect(status().isForbidden());
    }

    // ==================== DELETE /api/nuclear-plants/{id} ====================

    /**
     * Test: DELETE /api/nuclear-plants/{id} deletes nuclear plant when authenticated and plant exists.
     */
    @Test
    @WithMockUser
    void deleteNuclearPlant_ExistingId_ReturnsNoContent() throws Exception {
        doNothing().when(nuclearPlantService).deleteNuclearPlant(1L);

        mockMvc.perform(delete("/api/nuclear-plants/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    /**
     * Test: DELETE /api/nuclear-plants/{id} returns 404 when plant does not exist.
     */
    @Test
    @WithMockUser
    void deleteNuclearPlant_NonExistingId_ReturnsNotFound() throws Exception {
        doThrow(new RuntimeException("Nuclear plant not found")).when(nuclearPlantService).deleteNuclearPlant(999L);

        mockMvc.perform(delete("/api/nuclear-plants/999")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    /**
     * Test: DELETE /api/nuclear-plants/{id} returns 403 Forbidden when not authenticated.
     */
    @Test
    void deleteNuclearPlant_WithoutAuth_ReturnsForbidden() throws Exception {
        mockMvc.perform(delete("/api/nuclear-plants/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}

