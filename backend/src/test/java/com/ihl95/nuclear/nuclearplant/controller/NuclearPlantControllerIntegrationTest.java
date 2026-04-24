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
        // Crear una planta conocida en BD antes de cada test
        existingPlant = nuclearPlantRepository.save(
                NuclearPlantTestData.createNuclearPlantEntity(null, "Planta Central", "Madrid")
        );
        plantDTO = NuclearPlantTestData.createNuclearPlantDTO();
    }

    // ── GET ALL ────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/nuclear-plants → 200 con lista de plantas")
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
    @DisplayName("GET /api/nuclear-plants → 403 sin autenticación")
    void getAllNuclearPlants_shouldReturn403_whenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/nuclear-plants")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("GET /api/nuclear-plants → 200 con lista vacía")
    @WithMockUser
    void getAllNuclearPlants_shouldReturn200_withEmptyList() throws Exception {
        // Limpiar BD
        nuclearPlantRepository.deleteAll();

        mockMvc.perform(get("/api/nuclear-plants")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // ── GET BY ID ────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/nuclear-plants/{id} → 200 cuando existe")
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
    @DisplayName("GET /api/nuclear-plants/{id} → 404 cuando no existe")
    @WithMockUser
    void getNuclearPlantById_shouldReturn404_whenNotFound() throws Exception {
        mockMvc.perform(get("/api/nuclear-plants/{id}", 9999L)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/nuclear-plants/{id} → 403 sin autenticación")
    void getNuclearPlantById_shouldReturn403_whenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/nuclear-plants/{id}", existingPlant.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    // ── POST CREATE ────────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/nuclear-plants → 201 con planta válida")
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

        // Verificar persistencia real en BD
        assertThat(nuclearPlantRepository.findAll())
                .extracting(NuclearPlant::getName)
                .contains("Nueva Planta");
    }

    @Test
    @DisplayName("POST /api/nuclear-plants → 400 con nombre vacío")
    @WithMockUser
    void createNuclearPlant_shouldReturn400_whenNameIsBlank() throws Exception {
        String invalidPlantJson = "{\"location\":\"Valencia\"}";

        mockMvc.perform(post("/api/nuclear-plants")
                .contentType(APPLICATION_JSON)
                .content(invalidPlantJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/nuclear-plants → 400 con location vacía")
    @WithMockUser
    void createNuclearPlant_shouldReturn400_whenLocationIsBlank() throws Exception {
        String invalidPlantJson = "{\"name\":\"Nueva Planta\"}";

        mockMvc.perform(post("/api/nuclear-plants")
                .contentType(APPLICATION_JSON)
                .content(invalidPlantJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/nuclear-plants → 403 sin autenticación")
    void createNuclearPlant_shouldReturn403_whenNotAuthenticated() throws Exception {
        NuclearPlantDTO newPlantDTO = NuclearPlantTestData.createNuclearPlantDTO(
                null, "Nueva Planta", "Valencia"
        );

        mockMvc.perform(post("/api/nuclear-plants")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPlantDTO)))
                .andExpect(status().isForbidden());
    }

    // ── PUT UPDATE ────────────────────────────────────────────────────

    @Test
    @DisplayName("PUT /api/nuclear-plants/{id} → 200 con datos válidos")
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

        // Verificar cambio real en BD
        NuclearPlant updated = nuclearPlantRepository.findById(existingPlant.getId()).orElseThrow();
        assertThat(updated.getName()).isEqualTo("Planta Actualizada");
        assertThat(updated.getLocation()).isEqualTo("Barcelona");
    }

    @Test
    @DisplayName("PUT /api/nuclear-plants/{id} → 404 cuando no existe")
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

    @Test
    @DisplayName("PUT /api/nuclear-plants/{id} → 403 sin autenticación")
    void updateNuclearPlant_shouldReturn403_whenNotAuthenticated() throws Exception {
        NuclearPlantDTO updateDTO = NuclearPlantTestData.createNuclearPlantDTO(
                existingPlant.getId(), "Actualizada", "Barcelona"
        );

        mockMvc.perform(put("/api/nuclear-plants/{id}", existingPlant.getId())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isForbidden());
    }

    // ── DELETE ────────────────────────────────────────────────────

    @Test
    @DisplayName("DELETE /api/nuclear-plants/{id} → 204 eliminada correctamente")
    @WithMockUser
    void deleteNuclearPlant_shouldReturn204_whenExists() throws Exception {
        mockMvc.perform(delete("/api/nuclear-plants/{id}", existingPlant.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verificar eliminación real en BD
        assertThat(nuclearPlantRepository.findById(existingPlant.getId())).isEmpty();
    }

    @Test
    @DisplayName("DELETE /api/nuclear-plants/{id} → 404 cuando no existe")
    @WithMockUser
    void deleteNuclearPlant_shouldReturn404_whenNotFound() throws Exception {
        mockMvc.perform(delete("/api/nuclear-plants/{id}", 9999L)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/nuclear-plants/{id} → 403 without authentication")
    void deleteNuclearPlant_shouldReturn403_whenNotAuthenticated() throws Exception {
        mockMvc.perform(delete("/api/nuclear-plants/{id}", existingPlant.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}

