package com.ihl95.nuclear.nuclearPlant.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihl95.nuclear.nuclearPlant.application.dto.NuclearPlantDTO;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content; // Add this import statement
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // Add this import statement
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put; // Add this import statement
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete; // Add this import statement
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // Add this import statement

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath; // Add this import statement

/*
 * Esta clase se encarga de realizar las pruebas de integración de la clase NuclearPlantController.
 * Usa datos de prueba que se encuentran en el fichero test/resources/test-data.sql
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NuclearPlantIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * The function tests if all nuclear plants are returned when calling the endpoint to get all nuclear
     * plants.
     */
    @Test
    void ifGetAllNuclearPlants_thenAllNuclearPlantsAreReturned() throws Exception {
        mockMvc.perform(get("/api/nuclear-plants"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Nuclear Plant MK1"))
                .andExpect(jsonPath("$[0].location").value("Prueba, Testlandia"))
                .andExpect(jsonPath("$[3].id").value(4))
                .andExpect(jsonPath("$[3].name").value("Test Nuclear Plant MK4"))
                .andExpect(jsonPath("$[3].location").value("Verificacion, Verifilandia"));
    }

    /**
     * This test function verifies that calling the endpoint to get a nuclear plant
     * by ID 1 returns the
     * expected plant details in JSON format.
     */
    @Test
    void ifGetNuclearPlantById_thenNuclearPlantWithId1IsReturned() throws Exception {
        mockMvc.perform(get("/api/nuclear-plants/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Nuclear Plant MK1"))
                .andExpect(jsonPath("$.location").value("Prueba, Testlandia"));
    }

    /**
     * This test verifies that a "Not Found" status is returned when trying to get a
     * nuclear plant by an ID
     * that does not exist.
     */
    @Test
    void ifGetNuclearPlantById_whenIdNotFound_thenNotFoundStatusIsReturned() throws Exception {
        mockMvc.perform(get("/api/nuclear-plants/999"))
                .andExpect(status().isNotFound());
    }

    /**
     * This test function verifies the creation of a new nuclear plant by sending a POST request with the
     * plant details and checking the response.
     */
    @Test
    @Transactional
    void whenCreateNuclearPlant_thenCreatedNuclearPlantIsReturned() throws Exception {

        NuclearPlantDTO newNuclearPlant = new NuclearPlantDTO(null, "New Nuclear Plant", "New Location");
        String newNuclearPlantJson = objectMapper.writeValueAsString(newNuclearPlant);

        mockMvc.perform(post("/api/nuclear-plants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newNuclearPlantJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("New Nuclear Plant"))
                .andExpect(jsonPath("$.location").value("New Location"));
    }

    @Test
    @Transactional
    void whenUpdateNuclearPlant_thenUpdatedNuclearPlantIsReturned() throws Exception {
        NuclearPlantDTO updatedNuclearPlant = new NuclearPlantDTO(null, "Updated Nuclear Plant", "Updated Location");
        String updatedNuclearPlantJson = objectMapper.writeValueAsString(updatedNuclearPlant);

        mockMvc.perform(put("/api/nuclear-plants/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedNuclearPlantJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Nuclear Plant"))
                .andExpect(jsonPath("$.location").value("Updated Location"));
    }

    @Test
    @Transactional
    void whenDeleteNuclearPlant_thenNuclearPlantIsDeleted() throws Exception {
        mockMvc.perform(delete("/api/nuclear-plants/1"))
                .andExpect(status().isNoContent());
    }

}
