package com.ihl95.nuclear.nuclearPlant.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content; // Add this import statement
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // Add this import statement
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // Add this import statement
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath; // Add this import statement

import com.ihl95.nuclear.nuclearPlant.application.controller.NuclearPlantController;
import com.ihl95.nuclear.nuclearPlant.application.dto.NuclearPlantDTO;

import static org.assertj.core.api.Assertions.assertThat; // Add this import statement

/*
 * Esta clase se encarga de realizar las pruebas de integración de la clase NuclearPlantController.
 * Usa datos de prueba que se encuentran en el fichero test/resources/test-data.sql
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NuclearPlantControllerTest {
    
    @Autowired
    private MockMvc mockMvc; // Add this line to declare the MockMvc variable
    
    @Test
    void ifGetNuclearPlantById_thenNuclearPlantWithId1IsReturned() throws Exception {
        // Llamar directamente al endpoint utilizando MockMvc
        mockMvc.perform(get("/api/nuclear-plants/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Nuclear Plant MK1"))
                .andExpect(jsonPath("$.location").value("Prueba, Testlandia"));
    }

}
