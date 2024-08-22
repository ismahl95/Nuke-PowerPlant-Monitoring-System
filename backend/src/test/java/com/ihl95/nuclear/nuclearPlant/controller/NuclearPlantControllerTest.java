package com.ihl95.nuclear.nuclearPlant.controller;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ihl95.nuclear.nuclearPlant.application.controller.NuclearPlantController;
import com.ihl95.nuclear.nuclearPlant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearPlant.application.service.NuclearPlantService;

@WebMvcTest(NuclearPlantController.class)
class NuclearPlantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NuclearPlantService nuclearPlantService;

    @Test
    void whenGetNuclearPlantById_thenReturnNuclearPlant() throws Exception {
        // Arrange
        NuclearPlantDTO mockPlant = new NuclearPlantDTO(1L, "Test Nuclear Plant", "Prueba, Testlandia");
        Mockito.when(nuclearPlantService.getNuclearPlantById(1L)).thenReturn(mockPlant);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/nuclear-plants/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Nuclear Plant"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("Prueba, Testlandia"));
    }

    @Test
    void whenGetNuclearPlantByIdNotFound_thenReturnNotFound() throws Exception {
        // Arrange
        Mockito.when(nuclearPlantService.getNuclearPlantById(anyLong())).thenReturn(null);
    
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/nuclear-plants/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
