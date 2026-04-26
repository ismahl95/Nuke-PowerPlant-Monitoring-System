package com.ihl95.nuclear.nuclearplant.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearplant.application.mapper.NuclearPlantCompleteMapper;
import com.ihl95.nuclear.nuclearplant.application.service.NuclearPlantServiceImpl;
import com.ihl95.nuclear.nuclearplant.domain.NuclearPlant;
import com.ihl95.nuclear.nuclearplant.infraestructure.NuclearPlantRepository;
import com.ihl95.nuclear.common.mocks.NuclearPlantTestData;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

/**
 * Tests for NuclearPlantServiceImpl using Template Method pattern.
 * Validates that the service correctly implements abstract methods.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("NuclearPlantServiceImpl - Template Method Pattern Implementation")
class NuclearPlantServiceImplTest {

    @Mock
    private NuclearPlantRepository repository;

    @Mock
    private NuclearPlantCompleteMapper mapper;

    private NuclearPlantServiceImpl service;
    private NuclearPlant testEntity;
    private NuclearPlantDTO testDTO;

    @BeforeEach
    void setUp() {
        service = new NuclearPlantServiceImpl(repository, mapper);
        testEntity = NuclearPlantTestData.createNuclearPlantEntity(1L, "Planta Test", "Madrid");
        testDTO = NuclearPlantTestData.createNuclearPlantDTO(1L, "Planta Test", "Madrid");
    }

    // ── Test inherited getAll() template method ──

    @Test
    @DisplayName("getAllNuclearPlants should delegate to inherited getAll()")
    void getAllNuclearPlants_shouldDelegateToBaseTemplate() {
        // Arrange
        List<NuclearPlant> plants = List.of(testEntity);
        when(repository.findAll()).thenReturn(plants);
        when(mapper.toNuclearPlantDTO(testEntity)).thenReturn(testDTO);

        // Act
        List<NuclearPlantDTO> result = service.getAllNuclearPlants();

        // Assert
        assertThat(result).hasSize(1);
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toNuclearPlantDTO(testEntity);
    }

    // ── Test inherited getById() template method ──

    @Test
    @DisplayName("getNuclearPlantById should delegate to inherited getById()")
    void getNuclearPlantById_shouldDelegateToBaseTemplate() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(testEntity));
        when(mapper.toNuclearPlantDTO(testEntity)).thenReturn(testDTO);

        // Act
        NuclearPlantDTO result = service.getNuclearPlantById(1L);

        // Assert
        assertThat(result).isNotNull()
            .extracting(NuclearPlantDTO::name, NuclearPlantDTO::location)
            .containsExactly("Planta Test", "Madrid");
    }

    @Test
    @DisplayName("getNuclearPlantById should throw domain exception when not found")
    void getNuclearPlantById_shouldThrowDomainException_whenNotFound() {
        // Arrange
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> service.getNuclearPlantById(99L))
            .hasMessageContaining("not found");
    }

    // ── Test validation hook ──

    @Test
    @DisplayName("createNuclearPlant should validate DTO name is not blank")
    void createNuclearPlant_shouldValidateName() {
        // Arrange
        NuclearPlantDTO invalidDTO = NuclearPlantTestData.createNuclearPlantDTO(null, "", "Madrid");

        // Act & Assert
        assertThatThrownBy(() -> service.createNuclearPlant(invalidDTO))
            .hasMessageContaining("name")
            .hasMessageContaining("blank");
    }

    @Test
    @DisplayName("createNuclearPlant should validate DTO location is not blank")
    void createNuclearPlant_shouldValidateLocation() {
        // Arrange
        NuclearPlantDTO invalidDTO = NuclearPlantTestData.createNuclearPlantDTO(null, "Planta", "");

        // Act & Assert
        assertThatThrownBy(() -> service.createNuclearPlant(invalidDTO))
            .hasMessageContaining("location")
            .hasMessageContaining("blank");
    }

    // ── Test inherited create() template method ──

    @Test
    @DisplayName("createNuclearPlant should persist and return DTO")
    void createNuclearPlant_shouldPersistAndReturnDTO() {
        // Arrange
        NuclearPlant created = NuclearPlantTestData.createNuclearPlantEntity(5L, "Planta Test", "Madrid");
        when(mapper.toNuclearPlant(testDTO)).thenReturn(testEntity);
        when(repository.save(testEntity)).thenReturn(created);
        when(mapper.toNuclearPlantDTO(created)).thenReturn(
            NuclearPlantTestData.createNuclearPlantDTO(5L, "Planta Test", "Madrid")
        );

        // Act
        NuclearPlantDTO result = service.createNuclearPlant(testDTO);

        // Assert
        assertThat(result).isNotNull()
            .extracting(NuclearPlantDTO::id)
            .isEqualTo(5L);
        verify(repository, times(1)).save(any());
    }

    // ── Test inherited update() template method ──

    @Test
    @DisplayName("updateNuclearPlant should modify and persist entity")
    void updateNuclearPlant_shouldModifyAndPersist() {
        // Arrange
        NuclearPlantDTO updateDTO = NuclearPlantTestData.createNuclearPlantDTO(1L, "Updated", "Barcelona");
        NuclearPlant original = NuclearPlantTestData.createNuclearPlantEntity(1L, "Old", "Old");

        when(repository.findById(1L)).thenReturn(Optional.of(original));
        when(mapper.toNuclearPlantDTO(any())).thenReturn(updateDTO);
        when(repository.save(any())).thenReturn(original);

        // Act
        NuclearPlantDTO result = service.updateNuclearPlant(1L, updateDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(repository, times(1)).findById(1L);
        verify(mapper, times(1)).updateNuclearPlantFromDto(updateDTO, original);
        verify(repository, times(1)).save(original);
    }

    // ── Test inherited delete() template method ──

    @Test
    @DisplayName("deleteNuclearPlant should remove entity")
    void deleteNuclearPlant_shouldRemoveEntity() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(testEntity));

        // Act
        service.deleteNuclearPlant(1L);

        // Assert
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(testEntity);
    }

    @Test
    @DisplayName("deleteNuclearPlant should throw when not found")
    void deleteNuclearPlant_shouldThrow_whenNotFound() {
        // Arrange
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> service.deleteNuclearPlant(99L))
            .hasMessageContaining("not found");
        verify(repository, never()).delete(any());
    }
}

