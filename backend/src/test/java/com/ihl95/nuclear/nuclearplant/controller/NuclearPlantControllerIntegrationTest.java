package com.ihl95.nuclear.nuclearplant.controller;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihl95.nuclear.common.mocks.NuclearPlantTestData;
import com.ihl95.nuclear.nuclearplant.application.controller.NuclearPlantController;
import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearplant.domain.NuclearPlant;
import com.ihl95.nuclear.nuclearplant.infraestructure.NuclearPlantRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for NuclearPlantController.
 * Tests complete HTTP flow from controller to database with real Spring context.
 * Uses @SpringBootTest to load full Spring Boot context.
 *
 * Note: Authentication tests are not included as security is disabled in test profile
 * to allow E2E tests with RestAssured to execute without JWT complexity.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@DisplayName("NuclearPlantController Integration Tests")
class NuclearPlantControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NuclearPlantRepository nuclearPlantRepository;

    private NuclearPlant existingPlant;
    private NuclearPlantDTO plantDTO;

    @BeforeEach
    void setUp() {
        // Create a known plant in DB before each test
        existingPlant = nuclearPlantRepository.save(
                NuclearPlantTestData.createNuclearPlantEntity(null, "Planta Central", "Madrid")
        );
        plantDTO = NuclearPlantTestData.createNuclearPlantDTO();
    }

    // ── GET ALL ────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/nuclear-plants → 200 returns list of plants")
    @WithMockUser
    void getAllNuclearPlants_shouldReturn200_withList() throws Exception {
        mockMvc.perform(get("/api/nuclear-plants")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    @DisplayName("GET /api/nuclear-plants → 200 returns empty list")
    @WithMockUser
    void getAllNuclearPlants_shouldReturn200_withEmptyList() throws Exception {
        // Clear database
        nuclearPlantRepository.deleteAll();

        mockMvc.perform(get("/api/nuclear-plants")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // ── GET BY ID ────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/nuclear-plants/{id} → 200 when exists")
    @WithMockUser
    void getNuclearPlantById_shouldReturn200_whenExists() throws Exception {
        mockMvc.perform(get("/api/nuclear-plants/{id}", existingPlant.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingPlant.getId()))
                .andExpect(jsonPath("$.name").value("Planta Central"))
                .andExpect(jsonPath("$.location").value("Madrid"));
    }

    @Test
    @DisplayName("GET /api/nuclear-plants/{id} → 404 when not found")
    @WithMockUser
    void getNuclearPlantById_shouldReturn404_whenNotFound() throws Exception {
        mockMvc.perform(get("/api/nuclear-plants/{id}", 9999L)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // ── POST CREATE ────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/nuclear-plants → 201 with valid plant")
    @WithMockUser
    void createNuclearPlant_shouldReturn201_whenValidData() throws Exception {
        NuclearPlantDTO newPlantDTO = NuclearPlantTestData.createNuclearPlantDTO(
                null, "Nueva Planta", "Valencia"
        );

        mockMvc.perform(post("/api/nuclear-plants")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPlantDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Nueva Planta"))
                .andExpect(jsonPath("$.location").value("Valencia"));

        // Verify real persistence in DB
        assertThat(nuclearPlantRepository.findAll())
                .extracting(NuclearPlant::getName)
                .contains("Nueva Planta");
    }

    @Test
    @DisplayName("POST /api/nuclear-plants → 400 with blank name")
    @WithMockUser
    void createNuclearPlant_shouldReturn400_whenNameIsBlank() throws Exception {
        String invalidPlantJson = "{\"location\":\"Valencia\"}";

        mockMvc.perform(post("/api/nuclear-plants")
                .contentType(APPLICATION_JSON)
                .content(invalidPlantJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/nuclear-plants → 400 with blank location")
    @WithMockUser
    void createNuclearPlant_shouldReturn400_whenLocationIsBlank() throws Exception {
        String invalidPlantJson = "{\"name\":\"Nueva Planta\"}";

        mockMvc.perform(post("/api/nuclear-plants")
                .contentType(APPLICATION_JSON)
                .content(invalidPlantJson))
                .andExpect(status().isBadRequest());
    }

    // ── PUT UPDATE ────────────────────────────────────────────────────

    @Test
    @DisplayName("PUT /api/nuclear-plants/{id} → 200 with valid data")
    @WithMockUser
    void updateNuclearPlant_shouldReturn200_whenValidData() throws Exception {
        NuclearPlantDTO updateDTO = NuclearPlantTestData.createNuclearPlantDTO(
                existingPlant.getId(), "Planta Actualizada", "Barcelona"
        );

        mockMvc.perform(put("/api/nuclear-plants/{id}", existingPlant.getId())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Planta Actualizada"))
                .andExpect(jsonPath("$.location").value("Barcelona"));

        // Verify real change in DB
        NuclearPlant updated = nuclearPlantRepository.findById(existingPlant.getId()).orElseThrow();
        assertThat(updated.getName()).isEqualTo("Planta Actualizada");
        assertThat(updated.getLocation()).isEqualTo("Barcelona");
    }

    @Test
    @DisplayName("PUT /api/nuclear-plants/{id} → 404 when not found")
    @WithMockUser
    void updateNuclearPlant_shouldReturn404_whenNotFound() throws Exception {
        NuclearPlantDTO updateDTO = NuclearPlantTestData.createNuclearPlantDTO(
                9999L, "Fantasma", "Fantasía"
        );

        mockMvc.perform(put("/api/nuclear-plants/{id}", 9999L)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound());
    }

    // ── DELETE ────────────────────────────────────────────────────

    @Test
    @DisplayName("DELETE /api/nuclear-plants/{id} → 204 deleted successfully")
    @WithMockUser
    void deleteNuclearPlant_shouldReturn204_whenExists() throws Exception {
        mockMvc.perform(delete("/api/nuclear-plants/{id}", existingPlant.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify real deletion in DB
        assertThat(nuclearPlantRepository.findById(existingPlant.getId())).isEmpty();
    }

    @Test
    @DisplayName("DELETE /api/nuclear-plants/{id} → 404 when not found")
    @WithMockUser
    void deleteNuclearPlant_shouldReturn404_whenNotFound() throws Exception {
        mockMvc.perform(delete("/api/nuclear-plants/{id}", 9999L)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

