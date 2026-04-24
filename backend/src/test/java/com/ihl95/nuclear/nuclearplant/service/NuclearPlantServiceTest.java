package com.ihl95.nuclear.nuclearplant.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ihl95.nuclear.common.mocks.NuclearPlantTestData;
import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearplant.application.exception.NuclearPlantException;
import com.ihl95.nuclear.nuclearplant.application.mapper.NuclearPlantCompleteMapper;
import com.ihl95.nuclear.nuclearplant.application.service.NuclearPlantServiceImpl;
import com.ihl95.nuclear.nuclearplant.domain.NuclearPlant;
import com.ihl95.nuclear.nuclearplant.infraestructure.NuclearPlantRepository;

/**
 * Unit tests for NuclearPlantServiceImpl using Mockito.
 * Tests business logic in isolation without Spring context.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("NuclearPlantService Unit Tests")
class NuclearPlantServiceTest {

    @Mock
    private NuclearPlantRepository nuclearPlantRepository;

    @Mock
    private NuclearPlantCompleteMapper nuclearPlantMapper;

    @InjectMocks
    private NuclearPlantServiceImpl nuclearPlantService;

    private NuclearPlant existingPlant;
    private NuclearPlantDTO plantDTO;

    @BeforeEach
    void setUp() {
        existingPlant = NuclearPlantTestData.createNuclearPlantEntity();
        plantDTO = NuclearPlantTestData.createNuclearPlantDTO();
    }

    // ── GET ALL ────────────────────────────────────────────────────

    @Test
    @DisplayName("getAllNuclearPlants → retorna lista cuando existen plantas")
    void getAllNuclearPlants_shouldReturnList_whenPlantsExist() {
        // ARRANGE
        NuclearPlant plant2 = NuclearPlantTestData.createNuclearPlantEntity(2L, "Planta 2", "Barcelona");
        List<NuclearPlant> plants = List.of(existingPlant, plant2);
        List<NuclearPlantDTO> expectedDTOs = List.of(plantDTO, plantDTO);

        when(nuclearPlantRepository.findAll()).thenReturn(plants);
        when(nuclearPlantMapper.toNuclearPlantDTO(existingPlant)).thenReturn(plantDTO);
        when(nuclearPlantMapper.toNuclearPlantDTO(plant2)).thenReturn(plantDTO);

        // ACT
        List<NuclearPlantDTO> result = nuclearPlantService.getAllNuclearPlants();

        // ASSERT
        assertThat(result).isNotNull().hasSize(2);
        verify(nuclearPlantRepository, times(1)).findAll();
        verify(nuclearPlantMapper, times(2)).toNuclearPlantDTO(any());
    }

    @Test
    @DisplayName("getAllNuclearPlants → retorna lista vacía cuando no hay plantas")
    void getAllNuclearPlants_shouldReturnEmptyList_whenNoPlants() {
        // ARRANGE
        when(nuclearPlantRepository.findAll()).thenReturn(List.of());

        // ACT
        List<NuclearPlantDTO> result = nuclearPlantService.getAllNuclearPlants();

        // ASSERT
        assertThat(result).isEmpty();
        verify(nuclearPlantRepository).findAll();
        verify(nuclearPlantMapper, never()).toNuclearPlantDTO(any());
    }

    // ── GET BY ID ────────────────────────────────────────────────────

    @Test
    @DisplayName("getNuclearPlantById → retorna DTO cuando existe")
    void getNuclearPlantById_shouldReturnDTO_whenExists() {
        // ARRANGE
        when(nuclearPlantRepository.findById(1L)).thenReturn(Optional.of(existingPlant));
        when(nuclearPlantMapper.toNuclearPlantDTO(existingPlant)).thenReturn(plantDTO);

        // ACT
        NuclearPlantDTO result = nuclearPlantService.getNuclearPlantById(1L);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("Planta Nuclear Central");
        verify(nuclearPlantRepository).findById(1L);
        verify(nuclearPlantMapper).toNuclearPlantDTO(existingPlant);
    }

    @Test
    @DisplayName("getNuclearPlantById → lanza excepción cuando no existe")
    void getNuclearPlantById_shouldThrow_whenNotFound() {
        // ARRANGE
        when(nuclearPlantRepository.findById(999L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> nuclearPlantService.getNuclearPlantById(999L))
                .isInstanceOf(NuclearPlantException.class);
        verify(nuclearPlantRepository).findById(999L);
    }

    @Test
    @DisplayName("getNuclearPlantById → lanza excepción cuando ID es null")
    void getNuclearPlantById_shouldThrow_whenIdIsNull() {
        // ACT & ASSERT
        assertThatThrownBy(() -> nuclearPlantService.getNuclearPlantById(null))
                .isInstanceOf(NuclearPlantException.class);
        verify(nuclearPlantRepository, never()).findById(any());
    }

    // ── CREATE ────────────────────────────────────────────────────

    @Test
    @DisplayName("createNuclearPlant → guarda y retorna DTO")
    void createNuclearPlant_shouldSaveAndReturnDTO() {
        // ARRANGE
        NuclearPlantDTO newPlantDTO = NuclearPlantTestData.createNuclearPlantDTO(null, "Nueva Planta", "Valencia");
        NuclearPlant newPlant = NuclearPlantTestData.createNuclearPlantEntity(null, "Nueva Planta", "Valencia");
        NuclearPlant savedPlant = NuclearPlantTestData.createNuclearPlantEntity(3L, "Nueva Planta", "Valencia");
        NuclearPlantDTO savedDTO = NuclearPlantTestData.createNuclearPlantDTO(3L, "Nueva Planta", "Valencia");

        when(nuclearPlantMapper.toNuclearPlant(newPlantDTO)).thenReturn(newPlant);
        when(nuclearPlantRepository.save(newPlant)).thenReturn(savedPlant);
        when(nuclearPlantMapper.toNuclearPlantDTO(savedPlant)).thenReturn(savedDTO);

        // ACT
        NuclearPlantDTO result = nuclearPlantService.createNuclearPlant(newPlantDTO);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(3L);
        assertThat(result.name()).isEqualTo("Nueva Planta");
        verify(nuclearPlantRepository).save(newPlant);
    }

    @Test
    @DisplayName("createNuclearPlant → lanza excepción cuando DTO es null")
    void createNuclearPlant_shouldThrow_whenDTOIsNull() {
        // ACT & ASSERT
        assertThatThrownBy(() -> nuclearPlantService.createNuclearPlant(null))
                .isInstanceOf(NuclearPlantException.class);
        verify(nuclearPlantRepository, never()).save(any());
    }

    // ── UPDATE ────────────────────────────────────────────────────

    @Test
    @DisplayName("updateNuclearPlant → actualiza y retorna DTO")
    void updateNuclearPlant_shouldUpdateAndReturnDTO() {
        // ARRANGE
        NuclearPlantDTO updateDTO = NuclearPlantTestData.createNuclearPlantDTO(1L, "Planta Actualizada", "Madrid");
        NuclearPlant updatedPlant = NuclearPlantTestData.createNuclearPlantEntity(1L, "Planta Actualizada", "Madrid");
        NuclearPlantDTO updatedDTO = NuclearPlantTestData.createNuclearPlantDTO(1L, "Planta Actualizada", "Madrid");

        when(nuclearPlantRepository.findById(1L)).thenReturn(Optional.of(existingPlant));
        when(nuclearPlantRepository.save(any(NuclearPlant.class))).thenReturn(updatedPlant);
        when(nuclearPlantMapper.toNuclearPlantDTO(updatedPlant)).thenReturn(updatedDTO);

        // ACT
        NuclearPlantDTO result = nuclearPlantService.updateNuclearPlant(1L, updateDTO);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("Planta Actualizada");
        verify(nuclearPlantRepository).findById(1L);
        verify(nuclearPlantRepository).save(any(NuclearPlant.class));
    }

    @Test
    @DisplayName("updateNuclearPlant → lanza excepción cuando no existe")
    void updateNuclearPlant_shouldThrow_whenNotFound() {
        // ARRANGE
        when(nuclearPlantRepository.findById(999L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> nuclearPlantService.updateNuclearPlant(999L, plantDTO))
                .isInstanceOf(NuclearPlantException.class);
        verify(nuclearPlantRepository, never()).save(any());
    }

    // ── DELETE ────────────────────────────────────────────────────

    @Test
    @DisplayName("deleteNuclearPlant → elimina cuando existe")
    void deleteNuclearPlant_shouldDelete_whenExists() {
        // ARRANGE
        when(nuclearPlantRepository.findById(1L)).thenReturn(Optional.of(existingPlant));
        doNothing().when(nuclearPlantRepository).delete(existingPlant);

        // ACT
        nuclearPlantService.deleteNuclearPlant(1L);

        // ASSERT
        verify(nuclearPlantRepository).findById(1L);
        verify(nuclearPlantRepository).delete(existingPlant);
    }

    @Test
    @DisplayName("deleteNuclearPlant → lanza excepción cuando no existe")
    void deleteNuclearPlant_shouldThrow_whenNotFound() {
        // ARRANGE
        when(nuclearPlantRepository.findById(999L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> nuclearPlantService.deleteNuclearPlant(999L))
                .isInstanceOf(NuclearPlantException.class);
        verify(nuclearPlantRepository, never()).delete(any());
    }
}

