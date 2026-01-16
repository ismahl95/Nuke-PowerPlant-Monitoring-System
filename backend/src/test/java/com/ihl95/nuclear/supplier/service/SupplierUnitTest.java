package com.ihl95.nuclear.supplier.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ihl95.nuclear.supplier.application.dto.SupplierDTO;
import com.ihl95.nuclear.supplier.application.exception.SupplierException;
import com.ihl95.nuclear.supplier.application.service.SupplierServiceImpl;
import com.ihl95.nuclear.supplier.domain.Supplier;

@ExtendWith(MockitoExtension.class)
class SupplierUnitTest extends SupplierServiceTestMocks {

  @InjectMocks
  private SupplierServiceImpl supplierService;

  @Override
  protected void setUpTestEntities() {
    super.setUpTestEntities();
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getAllSuppliers_shouldReturnListOfSupplierDTOs() {
    List<Supplier> suppliers = Collections.singletonList(supplier);
    when(supplierRepository.findAll()).thenReturn(suppliers);
    when(supplierMapper.toSupplierDTO(supplier)).thenReturn(supplierDTO);

    List<SupplierDTO> result = supplierService.getAllSuppliers();

    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEqualTo(supplierDTO);

    verify(supplierRepository, times(1)).findAll();
    verify(supplierMapper, times(1)).toSupplierDTO(supplier);
  }

  @Test
  void getSupplierById_shouldReturnSupplierDTO_whenSupplierExists() {
    when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
    when(supplierMapper.toSupplierDTO(supplier)).thenReturn(supplierDTO);

    SupplierDTO result = supplierService.getSupplierbyId(1L);

    assertThat(result)
      .isNotNull()
      .isEqualTo(supplierDTO);

    verify(supplierRepository, times(1)).findById(1L);
    verify(supplierMapper, times(1)).toSupplierDTO(supplier);
  }

  @Test
  void getSupplierbyId_shouldThrowException_whenPlantNotFound() {
    when(supplierRepository.findById(999L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> supplierService.getSupplierbyId(999L))
        .isInstanceOf(SupplierException.class)
        .hasMessageContaining("Supplier not found with id: 999");

    verify(supplierRepository, times(1)).findById(999L);
  }

  @Test
  void getSupplierbyId_shouldThrowException_whenIdIsNull() {
    assertThatThrownBy(() -> supplierService.getSupplierbyId(null))
        .isInstanceOf(SupplierException.class)
        .hasMessageContaining("The provided ID is not valid or null: null");

    verify(supplierRepository, never()).findById(any());
  }

  @Test
  void createSupplier_shouldReturnCreatedSupplierDTO(){
    when(supplierMapper.toSupplier(supplierDTO)).thenReturn(supplier);
    when(supplierRepository.save(supplier)).thenReturn(supplier);
    when(supplierMapper.toSupplierDTO(supplier)).thenReturn(supplierDTO);

    SupplierDTO result = supplierService.createSupplier(supplierDTO);

    assertThat(result)
        .isNotNull()
        .isEqualTo(supplierDTO);

    verify(supplierMapper, times(1)).toSupplier(supplierDTO);
    verify(supplierRepository, times(1)).save(supplier);
    verify(supplierMapper, times(1)).toSupplierDTO(supplier);
  }

  @Test
  void createSupplier_shouldThrowException_whenDTOIsNull() {
    assertThatThrownBy(() -> supplierService.createSupplier(null))
        .isInstanceOf(SupplierException.class)
        .hasMessageContaining("An unexpected error occurred while saving Supplier");

    verify(supplierMapper, never()).toSupplier(any());
    verify(supplierRepository, never()).save(any());
  }

  @Test
  void createSupplier_shouldThrowException_whenRepositoryThrowsException() {
    when(supplierMapper.toSupplier(supplierDTO)).thenReturn(supplier);
    when(supplierRepository.save(supplier)).thenThrow(SupplierException.internalError(SupplierException.UNEXPECTING_ERROR_WHILE_SAVING));

    assertThatThrownBy(() -> supplierService.createSupplier(supplierDTO))
        .isInstanceOf(SupplierException.class)
        .hasMessageContaining("An unexpected error occurred while saving Supplier");

    verify(supplierMapper, times(1)).toSupplier(supplierDTO);
    verify(supplierRepository, times(1)).save(supplier);
  }

  @Test
  void getAllSuppliers_shouldReturnEmptyList_whenNoSuppliersExist() {
    when(supplierRepository.findAll()).thenReturn(Collections.emptyList());

    List<SupplierDTO> result = supplierService.getAllSuppliers();

    assertThat(result).isEmpty();
    verify(supplierRepository, times(1)).findAll();
    verify(supplierMapper, never()).toSupplierDTO(any());
  }

  @Test
  void getSupplierById_shouldThrowException_whenRepositoryThrowsException() {
    when(supplierRepository.findById(1L)).thenThrow(new RuntimeException("DB error"));

    assertThatThrownBy(() -> supplierService.getSupplierbyId(1L))
        .isInstanceOf(SupplierException.class)
        .hasMessageContaining("Error retrieving supplier");

    verify(supplierRepository, times(1)).findById(1L);
  }

  @Test
  void updateSupplier_shouldReturnUpdatedSupplierDTO() {
    SupplierDTO updatedDTO = new SupplierDTO(1L, "Updated Supplier", "Updated Contact", "+1-999-999-9999");
    Supplier updatedSupplier = Supplier.builder()
        .id(1L)
        .name("Updated Supplier")
        .contact("Updated Contact")
        .phone("+1-999-999-9999")
        .build();

    when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
    when(supplierMapper.toSupplier(updatedDTO)).thenReturn(updatedSupplier);
    when(supplierRepository.save(updatedSupplier)).thenReturn(updatedSupplier);
    when(supplierMapper.toSupplierDTO(updatedSupplier)).thenReturn(updatedDTO);

    SupplierDTO result = supplierService.updateSupplier(1L, updatedDTO);

    assertThat(result)
        .isNotNull()
        .isEqualTo(updatedDTO);

    verify(supplierRepository, times(1)).findById(1L);
    verify(supplierMapper, times(1)).toSupplier(updatedDTO);
    verify(supplierRepository, times(1)).save(updatedSupplier);
  }

  @Test
  void updateSupplier_shouldThrowException_whenSupplierNotFound() {
    when(supplierRepository.findById(999L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> supplierService.updateSupplier(999L, supplierDTO))
        .isInstanceOf(SupplierException.class)
        .hasMessageContaining("Supplier not found with id: 999");

    verify(supplierRepository, times(1)).findById(999L);
  }

  @Test
  void updateSupplier_shouldThrowException_whenIdIsNull() {
    assertThatThrownBy(() -> supplierService.updateSupplier(null, supplierDTO))
        .isInstanceOf(SupplierException.class)
        .hasMessageContaining("Supplier not found with id: null");

    verify(supplierRepository, never()).save(any());
  }

  @Test
  void deleteSupplier_shouldDeleteSupplierWhenExists() {
    when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));

    supplierService.deleteSupplier(1L);

    verify(supplierRepository, times(1)).findById(1L);
    verify(supplierRepository, times(1)).delete(supplier);
  }

  @Test
  void deleteSupplier_shouldThrowException_whenSupplierNotFound() {
    when(supplierRepository.findById(999L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> supplierService.deleteSupplier(999L))
        .isInstanceOf(SupplierException.class)
        .hasMessageContaining("Supplier not found with id: 999");

    verify(supplierRepository, times(1)).findById(999L);
    verify(supplierRepository, never()).delete(any());
  }

  @Test
  void deleteSupplier_shouldThrowException_whenIdIsNull() {
    assertThatThrownBy(() -> supplierService.deleteSupplier(null))
        .isInstanceOf(SupplierException.class)
        .hasMessageContaining("Supplier not found with id: null");

    verify(supplierRepository, never()).delete(any());
  }

  @Test
  void getAllSuppliers_shouldReturnMultipleSupplierDTOs() {
    Supplier supplier2 = Supplier.builder()
        .id(2L)
        .name("Supplier 2")
        .contact("Contact 2")
        .phone("+2-222-222-2222")
        .build();
    
    SupplierDTO supplierDTO2 = new SupplierDTO(2L, "Supplier 2", "Contact 2", "+2-222-222-2222");

    List<Supplier> suppliers = List.of(supplier, supplier2);
    when(supplierRepository.findAll()).thenReturn(suppliers);
    when(supplierMapper.toSupplierDTO(supplier)).thenReturn(supplierDTO);
    when(supplierMapper.toSupplierDTO(supplier2)).thenReturn(supplierDTO2);

    List<SupplierDTO> result = supplierService.getAllSuppliers();

    assertThat(result)
        .hasSize(2)
        .contains(supplierDTO, supplierDTO2);

    verify(supplierRepository, times(1)).findAll();
    verify(supplierMapper, times(2)).toSupplierDTO(any());
  }

  @Test
  void createSupplier_shouldMapSupplierCorrectly() {
    when(supplierMapper.toSupplier(supplierDTO)).thenReturn(supplier);
    when(supplierRepository.save(supplier)).thenReturn(supplier);
    when(supplierMapper.toSupplierDTO(supplier)).thenReturn(supplierDTO);

    SupplierDTO result = supplierService.createSupplier(supplierDTO);

    assertThat(result.name()).isEqualTo(supplierDTO.name());
    assertThat(result.contact()).isEqualTo(supplierDTO.contact());
    assertThat(result.phone()).isEqualTo(supplierDTO.phone());
  }

  @Test
  void getSupplierById_shouldVerifyRepositoryCall() {
    when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
    when(supplierMapper.toSupplierDTO(supplier)).thenReturn(supplierDTO);

    supplierService.getSupplierbyId(1L);

    verify(supplierRepository).findById(1L);
  }

  @Test
  void deleteSupplier_shouldCallRepositoryDelete() {
    when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));

    supplierService.deleteSupplier(1L);

    verify(supplierRepository).delete(supplier);
  }

  @Test
  void updateSupplier_shouldCallMapperAndRepository() {
    SupplierDTO updatedDTO = new SupplierDTO(1L, "Updated", "Contact", "+1-111-111-1111");
    Supplier updatedSupplier = Supplier.builder()
        .id(1L)
        .name("Updated")
        .contact("Contact")
        .phone("+1-111-111-1111")
        .build();

    when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
    when(supplierMapper.toSupplier(updatedDTO)).thenReturn(updatedSupplier);
    when(supplierRepository.save(updatedSupplier)).thenReturn(updatedSupplier);
    when(supplierMapper.toSupplierDTO(updatedSupplier)).thenReturn(updatedDTO);

    SupplierDTO result = supplierService.updateSupplier(1L, updatedDTO);

    assertThat(result).isEqualTo(updatedDTO);
    verify(supplierRepository).save(updatedSupplier);
  }

  @Test
  void getSupplierById_shouldHandleDataCorrectly() {
    when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
    when(supplierMapper.toSupplierDTO(supplier)).thenReturn(supplierDTO);

    SupplierDTO result = supplierService.getSupplierbyId(1L);

    assertThat(result.id()).isEqualTo(1L);
    assertThat(result.name()).isEqualTo("Supplier 1");
    assertThat(result.contact()).isEqualTo("Contact 1");
    assertThat(result.phone()).isEqualTo("+1-111-111-1111");
  }

  @Test
  void createSupplier_shouldPersistSupplierToRepository() {
    when(supplierMapper.toSupplier(supplierDTO)).thenReturn(supplier);
    when(supplierRepository.save(supplier)).thenReturn(supplier);
    when(supplierMapper.toSupplierDTO(supplier)).thenReturn(supplierDTO);

    supplierService.createSupplier(supplierDTO);

    verify(supplierRepository, times(1)).save(supplier);
  }

  @Test
  void deleteSupplier_shouldVerifyFindAndDelete() {
    when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));

    supplierService.deleteSupplier(1L);

    verify(supplierRepository, times(1)).findById(1L);
    verify(supplierRepository, times(1)).delete(supplier);
  }

  @Test
  void updateSupplier_withValidData_shouldReturnUpdatedSupplier() {
    SupplierDTO updatedDTO = new SupplierDTO(1L, "New Name", "New Contact", "+1-222-222-2222");
    Supplier updatedSupplier = new Supplier();
    updatedSupplier.setId(1L);
    updatedSupplier.setName("New Name");
    updatedSupplier.setContact("New Contact");
    updatedSupplier.setPhone("+1-222-222-2222");

    when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
    when(supplierMapper.toSupplier(updatedDTO)).thenReturn(updatedSupplier);
    when(supplierRepository.save(any(Supplier.class))).thenReturn(updatedSupplier);
    when(supplierMapper.toSupplierDTO(any(Supplier.class))).thenReturn(updatedDTO);

    SupplierDTO result = supplierService.updateSupplier(1L, updatedDTO);

    assertThat(result.name()).isEqualTo("New Name");
  }

  @Test
  void getSupplierById_shouldReturnSupplierWithCorrectId() {
    when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
    when(supplierMapper.toSupplierDTO(supplier)).thenReturn(supplierDTO);

    SupplierDTO result = supplierService.getSupplierbyId(1L);

    assertThat(result.id()).isEqualTo(supplierDTO.id());
  }

  @Test
  void getAllSuppliers_shouldCallRepositoryFindAll() {
    when(supplierRepository.findAll()).thenReturn(Collections.singletonList(supplier));
    when(supplierMapper.toSupplierDTO(supplier)).thenReturn(supplierDTO);

    supplierService.getAllSuppliers();

    verify(supplierRepository).findAll();
  }

  @Test
  void createSupplier_shouldHandleNullDTO() {
    assertThatThrownBy(() -> supplierService.createSupplier(null))
        .isInstanceOf(SupplierException.class);
  }

  @Test
  void deleteSupplier_shouldThrowWhenIdIsNull() {
    assertThatThrownBy(() -> supplierService.deleteSupplier(null))
        .isInstanceOf(SupplierException.class);
  }

  @Test
  void updateSupplier_shouldThrowWhenSupplierDoesNotExist() {
    when(supplierRepository.findById(999L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> supplierService.updateSupplier(999L, supplierDTO))
        .isInstanceOf(SupplierException.class)
        .hasMessageContaining("Supplier not found");
  }

}
