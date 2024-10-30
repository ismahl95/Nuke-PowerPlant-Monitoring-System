package com.ihl95.nuclear.nuclearPlant.service;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearplant.application.mapper.NuclearPlantCompleteMapper;
import com.ihl95.nuclear.nuclearplant.domain.NuclearPlant;
import com.ihl95.nuclear.nuclearplant.infraestructure.NuclearPlantRepository;

public class NuclearPlantServiceTestMocks {

    @Mock
    protected NuclearPlantRepository nuclearPlantRepository;

    @Mock
    protected NuclearPlantCompleteMapper nuclearPlantCompleteMapper;

    protected NuclearPlant nuclearPlant;
    protected NuclearPlantDTO nuclearPlantDTO;

    public NuclearPlantServiceTestMocks() {
        // Inicializar los mocks
        MockitoAnnotations.openMocks(this);
        // Configurar un objeto NuclearPlant y su DTO para las pruebas
        setUpTestEntities();
    }

    private void setUpTestEntities() {
        nuclearPlant = new NuclearPlant();
        nuclearPlant.setId(1L);
        nuclearPlant.setName("Nuclear Plant 1");
        nuclearPlant.setLocation("Location 1");

        nuclearPlantDTO = new NuclearPlantDTO(1L, "Nuclear Plant 1", "Location 1");
    }
}
