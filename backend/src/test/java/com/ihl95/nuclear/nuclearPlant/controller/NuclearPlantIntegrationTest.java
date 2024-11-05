package com.ihl95.nuclear.nuclearPlant.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.security.AuthenticationRequest;

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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class NuclearPlantIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String getJwtToken() throws Exception {
        // Realiza la autenticación para obtener el token JWT
        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setUsername("Admin");
        authRequest.setPassword("admin");
    
        return mockMvc.perform(post("/api/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void ifGetAllNuclearPlants_thenAllNuclearPlantsAreReturned() throws Exception {
        String token = getJwtToken(); // Obtener el token

        mockMvc.perform(get("/api/nuclear-plants")
                .header("Authorization", "Bearer " + token)) // Agregar el token al encabezado
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Nuclear Plant 1"))
                .andExpect(jsonPath("$[0].location").value("Location 1"))
                .andExpect(jsonPath("$[3].id").value(4))
                .andExpect(jsonPath("$[3].name").value("Nuclear Plant 4"))
                .andExpect(jsonPath("$[3].location").value("Location 4"));
    }

    @Test
    void ifGetNuclearPlantById_thenNuclearPlantWithId1IsReturned() throws Exception {
        String token = getJwtToken(); // Obtener el token

        mockMvc.perform(get("/api/nuclear-plants/1")
                .header("Authorization", "Bearer " + token)) // Agregar el token al encabezado
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Nuclear Plant 1"))
                .andExpect(jsonPath("$.location").value("Location 1"));
    }

    @Test
    void ifGetNuclearPlantById_whenIdNotFound_thenNotFoundStatusIsReturned() throws Exception {
        String token = getJwtToken(); // Obtener el token

        mockMvc.perform(get("/api/nuclear-plants/999")
                .header("Authorization", "Bearer " + token)) // Agregar el token al encabezado
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void whenCreateNuclearPlant_thenCreatedNuclearPlantIsReturned() throws Exception {
        String token = getJwtToken(); // Obtener el token

        NuclearPlantDTO newNuclearPlant = new NuclearPlantDTO(null, "Nuclear Plant 5", "Location 5");
        String newNuclearPlantJson = objectMapper.writeValueAsString(newNuclearPlant);

        mockMvc.perform(post("/api/nuclear-plants")
                .header("Authorization", "Bearer " + token) // Agregar el token al encabezado
                .contentType(MediaType.APPLICATION_JSON)
                .content(newNuclearPlantJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Nuclear Plant 5"))
                .andExpect(jsonPath("$.location").value("Location 5"));
    }

    @Test
    @Transactional
    void whenCreateNuclearPlantWithoutAttributes_thenValidationExceptionIsReturned() throws Exception {
        String token = getJwtToken(); // Obtener el token

        NuclearPlantDTO newNuclearPlant = new NuclearPlantDTO(null, " ", " ");
        String newNuclearPlantJson = objectMapper.writeValueAsString(newNuclearPlant);

        mockMvc.perform(post("/api/nuclear-plants")
                .header("Authorization", "Bearer " + token) // Agregar el token al encabezado
                .contentType(MediaType.APPLICATION_JSON)
                .content(newNuclearPlantJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("El nombre es obligatorio"))
                .andExpect(jsonPath("$.location").value("La localización es obligatoria"));
    }

    @Test
    @Transactional
    void whenUpdateNuclearPlant_thenUpdatedNuclearPlantIsReturned() throws Exception {
        String token = getJwtToken(); // Obtener el token

        NuclearPlantDTO updatedNuclearPlant = new NuclearPlantDTO(null, "Updated Nuclear Plant", "Updated Location");
        String updatedNuclearPlantJson = objectMapper.writeValueAsString(updatedNuclearPlant);

        mockMvc.perform(put("/api/nuclear-plants/1")
                .header("Authorization", "Bearer " + token) // Agregar el token al encabezado
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
        String token = getJwtToken(); // Obtener el token

        mockMvc.perform(delete("/api/nuclear-plants/1")
                .header("Authorization", "Bearer " + token)) // Agregar el token al encabezado
                .andExpect(status().isNoContent());
    }
}
