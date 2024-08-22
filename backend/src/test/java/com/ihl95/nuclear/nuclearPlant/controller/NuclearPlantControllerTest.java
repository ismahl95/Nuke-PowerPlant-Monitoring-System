package com.ihl95.nuclear.nuclearPlant.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content; // Add this import statement
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // Add this import statement
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // Add this import statement
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath; // Add this import statement

/*
 * Esta clase se encarga de realizar las pruebas de integración de la clase NuclearPlantController.
 * Usa datos de prueba que se encuentran en el fichero test/resources/test-data.sql
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NuclearPlantControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
/**
 * This test function verifies that calling the endpoint to get a nuclear plant by ID 1 returns the
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
 * This test verifies that a "Not Found" status is returned when trying to get a nuclear plant by an ID
 * that does not exist.
 */
    @Test
    void ifGetNuclearPlantById_whenIdNotFound_thenNotFoundStatusIsReturned() throws Exception {
        mockMvc.perform(get("/api/nuclear-plants/999"))
                .andExpect(status().isNotFound());
    }

}
