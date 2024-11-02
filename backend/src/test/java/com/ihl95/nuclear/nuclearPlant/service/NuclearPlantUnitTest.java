package com.ihl95.nuclear.nuclearPlant.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearplant.application.exception.NuclearPlantException;
import com.ihl95.nuclear.nuclearplant.application.service.NuclearPlantServiceImpl;
import com.ihl95.nuclear.nuclearplant.domain.NuclearPlant;
import com.ihl95.nuclear.supplier.application.exception.SupplierException;

import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy; // Add this import statement

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class NuclearPlantUnitTest extends NuclearPlantServiceTestMocks {

  @InjectMocks
  private NuclearPlantServiceImpl nuclearPlantService;

  @Test
  void getAllNuclearPlants_shouldReturnListOfNuclearPlantDTOs() {
    List<NuclearPlant> nuclearPlants = Collections.singletonList(nuclearPlant);
    when(nuclearPlantRepository.findAll()).thenReturn(nuclearPlants);
    when(nuclearPlantCompleteMapper.toNuclearPlantDTO(nuclearPlant)).thenReturn(nuclearPlantDTO);

    List<NuclearPlantDTO> result = nuclearPlantService.getAllNuclearPlants();

    assertThat(result).hasSize(1); // Modify this assertion statement
    assertThat(result.get(0)).isEqualTo(nuclearPlantDTO);

    verify(nuclearPlantRepository, times(1)).findAll();
    verify(nuclearPlantCompleteMapper, times(1)).toNuclearPlantDTO(nuclearPlant);
  }

  @Test
  void getNuclearPlantById_shouldReturnNuclearPlantDTO_whenPlantExists() {
    when(nuclearPlantRepository.findById(1L)).thenReturn(Optional.of(nuclearPlant));
    when(nuclearPlantCompleteMapper.toNuclearPlantDTO(nuclearPlant)).thenReturn(nuclearPlantDTO);

    NuclearPlantDTO result = nuclearPlantService.getNuclearPlantById(1L);

    assertThat(result)
        .isNotNull()
        .isEqualTo(nuclearPlantDTO);

    verify(nuclearPlantRepository, times(1)).findById(1L);
    verify(nuclearPlantCompleteMapper, times(1)).toNuclearPlantDTO(nuclearPlant);
  }

  @Test
  void getNuclearPlantById_shouldThrowException_whenPlantNotFound() {
    when(nuclearPlantRepository.findById(999L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> nuclearPlantService.getNuclearPlantById(999L))
        .isInstanceOf(NuclearPlantException.class)
        .hasMessageContaining("Nuclear Plant not found with id: 999");

    verify(nuclearPlantRepository, times(1)).findById(999L);
  }

  @Test
  void getNuclearPlantbyId_shouldThrowException_whenIdIsNull() {
    assertThatThrownBy(() -> nuclearPlantService.getNuclearPlantById(null))
        .isInstanceOf(NuclearPlantException.class)
        .hasMessageContaining("The provided ID is not valid or null: null");

    verify(nuclearPlantRepository, never()).findById(any());
  }

  @Test
  void createNuclearPlant_shouldReturnCreatedNuclearPlantDTO() {
    when(nuclearPlantCompleteMapper.toNuclearPlant(nuclearPlantDTO)).thenReturn(nuclearPlant);
    when(nuclearPlantRepository.save(nuclearPlant)).thenReturn(nuclearPlant);
    when(nuclearPlantCompleteMapper.toNuclearPlantDTO(nuclearPlant)).thenReturn(nuclearPlantDTO);

    NuclearPlantDTO result = nuclearPlantService.createNuclearPlant(nuclearPlantDTO);

    assertThat(result)
        .isNotNull()
        .isEqualTo(nuclearPlantDTO);

    verify(nuclearPlantCompleteMapper, times(1)).toNuclearPlant(nuclearPlantDTO);
    verify(nuclearPlantRepository, times(1)).save(nuclearPlant);
    verify(nuclearPlantCompleteMapper, times(1)).toNuclearPlantDTO(nuclearPlant);
  }

  @Test
  void updateNuclearPlant_shouldReturnUpdatedNuclearPlantDTO() {
    when(nuclearPlantRepository.findById(1L)).thenReturn(Optional.of(nuclearPlant));

    when(nuclearPlantRepository.save(any(NuclearPlant.class))).thenAnswer(invocation -> {
      NuclearPlant savedPlant = invocation.getArgument(0);
      savedPlant.setName("Updated Nuclear Plant");
      savedPlant.setLocation("Updated Location");
      return savedPlant;
    });

    when(nuclearPlantCompleteMapper.toNuclearPlantDTO(any(NuclearPlant.class))).thenAnswer(invocation -> {
      NuclearPlant plant = invocation.getArgument(0);
      return new NuclearPlantDTO(plant.getId(), plant.getName(), plant.getLocation());
    });

    NuclearPlantDTO updatedNuclearPlantDTO = new NuclearPlantDTO(null, "Updated Nuclear Plant", "Updated Location");

    NuclearPlantDTO result = nuclearPlantService.updateNuclearPlant(1L, updatedNuclearPlantDTO);

    assertThat(result).isNotNull();
    assertThat(result.name()).isEqualTo("Updated Nuclear Plant");
    assertThat(result.location()).isEqualTo("Updated Location");

    verify(nuclearPlantRepository, times(1)).findById(1L);
    verify(nuclearPlantRepository, times(1)).save(any(NuclearPlant.class));
    verify(nuclearPlantCompleteMapper, times(1)).toNuclearPlantDTO(any(NuclearPlant.class));
  }

  @Test
  void deleteNuclearPlant_shouldDeleteNuclearPlant() {
    when(nuclearPlantRepository.findById(1L)).thenReturn(Optional.of(nuclearPlant));

    nuclearPlantService.deleteNuclearPlant(1L);

    verify(nuclearPlantRepository, times(1)).delete(nuclearPlant);
  }
}
