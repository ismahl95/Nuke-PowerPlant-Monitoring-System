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
import org.mockito.junit.jupiter.MockitoExtension;

import com.ihl95.nuclear.supplier.application.dto.SupplierDTO;
import com.ihl95.nuclear.supplier.application.exception.SupplierException;
import com.ihl95.nuclear.supplier.application.service.SupplierServiceImpl;
import com.ihl95.nuclear.supplier.domain.Supplier;

@ExtendWith(MockitoExtension.class)
class SupplierUnitTest extends SupplierServiceTestMocks {

  @InjectMocks
  private SupplierServiceImpl supplierService;

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

}
