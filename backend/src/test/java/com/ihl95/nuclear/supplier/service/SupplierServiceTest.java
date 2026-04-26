package com.ihl95.nuclear.supplier.service;

import static org.assertj.core.api.Assertions.*;
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

import com.ihl95.nuclear.common.mocks.SupplierTestData;
import com.ihl95.nuclear.supplier.application.dto.SupplierDTO;
import com.ihl95.nuclear.supplier.application.exception.SupplierException;
import com.ihl95.nuclear.supplier.application.mapper.SupplierMapper;
import com.ihl95.nuclear.supplier.application.service.SupplierServiceImpl;
import com.ihl95.nuclear.supplier.domain.Supplier;
import com.ihl95.nuclear.supplier.infraestructure.SupplierRepository;

/**
 * Unit tests for SupplierService.
 * Tests business logic in complete isolation using Mockito mocks.
 * No Spring context, no database, milliseconds execution.
 *
 * 18 tests covering 5 service methods:
 * - getAllSuppliers (2 tests)
 * - getSupplierbyId (4 tests)
 * - createSupplier (3 tests)
 * - updateSupplier (4 tests)
 * - deleteSupplier (3 tests)
 *
 * Execution: mvn test -Dtest=SupplierServiceTest
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("SupplierService Unit Tests")
class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private SupplierMapper supplierMapper;

    @InjectMocks
    private SupplierServiceImpl supplierService;

    private Supplier existingSupplier;
    private SupplierDTO supplierDTO;

    @BeforeEach
    void setUp() {
        existingSupplier = SupplierTestData.createSupplierEntity(1L, "Supplier A", "supplier@example.com", "+34912345678");
        supplierDTO = SupplierTestData.createSupplierDTO(1L, "Supplier A", "supplier@example.com", "+34912345678");
    }

    // ─────────────────────────────────────────────────────────────
    // GET ALL SUPPLIERS (2 tests)
    // ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("getAllSuppliers → returns DTO list when suppliers exist")
    void getAllSuppliers_shouldReturnDTOList_whenSuppliersExist() {
        // ARRANGE
        List<Supplier> suppliers = List.of(existingSupplier);
        List<SupplierDTO> expectedDTOs = List.of(supplierDTO);

        when(supplierRepository.findAll()).thenReturn(suppliers);
        when(supplierMapper.toSupplierDTO(existingSupplier)).thenReturn(supplierDTO);

        // ACT
        List<SupplierDTO> result = supplierService.getAllSuppliers();

        // ASSERT
        assertThat(result)
            .isNotNull()
            .isNotEmpty()
            .hasSize(1)
            .containsExactly(supplierDTO);
        verify(supplierRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getAllSuppliers → returns empty list when no suppliers exist")
    void getAllSuppliers_shouldReturnEmptyList_whenNoSuppliersExist() {
        // ARRANGE
        when(supplierRepository.findAll()).thenReturn(List.of());

        // ACT
        List<SupplierDTO> result = supplierService.getAllSuppliers();

        // ASSERT
        assertThat(result)
            .isNotNull()
            .isEmpty();
        verify(supplierRepository, times(1)).findAll();
    }

    // ─────────────────────────────────────────────────────────────
    // GET SUPPLIER BY ID (4 tests)
    // ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("getSupplierbyId → returns DTO when supplier exists")
    void getSupplierbyId_shouldReturnDTO_whenSupplierExists() {
        // ARRANGE
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(existingSupplier));
        when(supplierMapper.toSupplierDTO(existingSupplier)).thenReturn(supplierDTO);

        // ACT
        SupplierDTO result = supplierService.getSupplierbyId(1L);

        // ASSERT
        assertThat(result)
            .isNotNull()
            .isEqualTo(supplierDTO);
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Supplier A");
        verify(supplierRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getSupplierbyId → throws SupplierException when supplier not found")
    void getSupplierbyId_shouldThrow_whenSupplierNotFound() {
        // ARRANGE
        when(supplierRepository.findById(99L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> supplierService.getSupplierbyId(99L))
            .isInstanceOf(SupplierException.class)
            .hasMessageContaining("not found");
        verify(supplierRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("getSupplierbyId → throws SupplierException when ID is null")
    void getSupplierbyId_shouldThrow_whenIdNull() {
        // ACT & ASSERT
        assertThatThrownBy(() -> supplierService.getSupplierbyId(null))
            .isInstanceOf(SupplierException.class)
            .hasMessageContaining("ID");
        verify(supplierRepository, never()).findById(any());
    }

    @Test
    @DisplayName("getSupplierbyId → throws SupplierException on repository runtime error")
    void getSupplierbyId_shouldThrow_onRepositoryError() {
        // ARRANGE
        when(supplierRepository.findById(1L))
            .thenThrow(new RuntimeException("Database connection error"));

        // ACT & ASSERT
        assertThatThrownBy(() -> supplierService.getSupplierbyId(1L))
            .isInstanceOf(SupplierException.class)
            .hasMessageContaining("Error retrieving");
    }

    // ─────────────────────────────────────────────────────────────
    // CREATE SUPPLIER (3 tests)
    // ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("createSupplier → saves and returns DTO when valid")
    void createSupplier_shouldSaveAndReturnDTO_whenValid() {
        // ARRANGE
        Supplier supplierToSave = Supplier.builder()
            .name("supplier@example.com")
            .contact("supplier@example.com")
            .phone("+34912345678")
            .build();
        Supplier savedSupplier = SupplierTestData.createSupplierEntity(2L, "Supplier B", "supplier@example.com", "+34912345678");
        SupplierDTO savedDTO = SupplierTestData.createSupplierDTO(2L, "Supplier B", "supplier@example.com", "+34912345678");

        when(supplierMapper.toSupplier(supplierDTO)).thenReturn(supplierToSave);
        when(supplierRepository.save(supplierToSave)).thenReturn(savedSupplier);
        when(supplierMapper.toSupplierDTO(savedSupplier)).thenReturn(savedDTO);

        // ACT
        SupplierDTO result = supplierService.createSupplier(supplierDTO);

        // ASSERT
        assertThat(result)
            .isNotNull()
            .isEqualTo(savedDTO);
        assertThat(result.id()).isEqualTo(2L);
        verify(supplierRepository, times(1)).save(supplierToSave);
    }

    @Test
    @DisplayName("createSupplier → throws SupplierException when DTO is null")
    void createSupplier_shouldThrow_whenDTONull() {
        // ACT & ASSERT
        assertThatThrownBy(() -> supplierService.createSupplier(null))
            .isInstanceOf(SupplierException.class)
            .hasMessageContaining("unexpected");
        verify(supplierRepository, never()).save(any());
    }

    @Test
    @DisplayName("createSupplier → throws exception on save error")
    void createSupplier_shouldThrow_onSaveError() {
        // ARRANGE
        Supplier supplierToSave = Supplier.builder()
            .name("Test")
            .contact("test@example.com")
            .phone("+34912345678")
            .build();

        when(supplierMapper.toSupplier(supplierDTO)).thenReturn(supplierToSave);
        when(supplierRepository.save(supplierToSave))
            .thenThrow(new RuntimeException("Database error"));

        // ACT & ASSERT
        assertThatThrownBy(() -> supplierService.createSupplier(supplierDTO))
            .isInstanceOf(RuntimeException.class);
    }

    // ─────────────────────────────────────────────────────────────
    // UPDATE SUPPLIER (4 tests)
    // ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("updateSupplier → modifies and returns updated DTO when exists")
    void updateSupplier_shouldUpdateAndReturnDTO_whenSupplierExists() {
        // ARRANGE
        SupplierDTO updateDTO = SupplierTestData.createSupplierDTO(
            null, "Supplier A Updated", "updated@example.com", "+34987654321"
        );
        Supplier mappedSupplier = Supplier.builder()
            .name("Supplier A Updated")
            .contact("updated@example.com")
            .phone("+34987654321")
            .build();
        Supplier existingForUpdate = SupplierTestData.createSupplierEntity(1L, "Supplier A", "supplier@example.com", "+34912345678");
        Supplier savedUpdatedSupplier = SupplierTestData.createSupplierEntity(
            1L, "Supplier A Updated", "updated@example.com", "+34987654321"
        );
        SupplierDTO updatedDTO = SupplierTestData.createSupplierDTO(
            1L, "Supplier A Updated", "updated@example.com", "+34987654321"
        );

        when(supplierRepository.findById(1L)).thenReturn(Optional.of(existingForUpdate));
        when(supplierMapper.toSupplier(updateDTO)).thenReturn(mappedSupplier);
        when(supplierRepository.save(any(Supplier.class))).thenReturn(savedUpdatedSupplier);
        when(supplierMapper.toSupplierDTO(savedUpdatedSupplier)).thenReturn(updatedDTO);

        // ACT
        SupplierDTO result = supplierService.updateSupplier(1L, updateDTO);

        // ASSERT
        assertThat(result)
            .isNotNull()
            .isEqualTo(updatedDTO);
        assertThat(result.name()).isEqualTo("Supplier A Updated");
        assertThat(result.contact()).isEqualTo("updated@example.com");
        verify(supplierRepository, times(1)).findById(1L);
        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }

    @Test
    @DisplayName("updateSupplier → throws SupplierException when supplier not found")
    void updateSupplier_shouldThrow_whenSupplierNotFound() {
        // ARRANGE
        when(supplierRepository.findById(99L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> supplierService.updateSupplier(99L, supplierDTO))
            .isInstanceOf(SupplierException.class)
            .hasMessageContaining("not found");
        verify(supplierRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("updateSupplier → throws SupplierException when ID is null")
    void updateSupplier_shouldThrow_whenIdNull() {
        // ACT & ASSERT
        assertThatThrownBy(() -> supplierService.updateSupplier(null, supplierDTO))
            .isInstanceOf(SupplierException.class);
        verify(supplierRepository, never()).findById(any());
    }

    @Test
    @DisplayName("updateSupplier → throws exception on save error")
    void updateSupplier_shouldThrow_onSaveError() {
        // ARRANGE
        Supplier mappedSupplier = Supplier.builder().name("Test").build();
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(existingSupplier));
        when(supplierMapper.toSupplier(supplierDTO)).thenReturn(mappedSupplier);
        when(supplierRepository.save(any(Supplier.class)))
            .thenThrow(new RuntimeException("Database error"));

        // ACT & ASSERT
        assertThatThrownBy(() -> supplierService.updateSupplier(1L, supplierDTO))
            .isInstanceOf(RuntimeException.class);
    }

    // ─────────────────────────────────────────────────────────────
    // DELETE SUPPLIER (3 tests)
    // ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("deleteSupplier → deletes when supplier exists")
    void deleteSupplier_shouldDelete_whenSupplierExists() {
        // ARRANGE
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(existingSupplier));
        doNothing().when(supplierRepository).delete(existingSupplier);

        // ACT
        supplierService.deleteSupplier(1L);

        // ASSERT
        verify(supplierRepository, times(1)).findById(1L);
        verify(supplierRepository, times(1)).delete(existingSupplier);
    }

    @Test
    @DisplayName("deleteSupplier → throws SupplierException when supplier not found")
    void deleteSupplier_shouldThrow_whenSupplierNotFound() {
        // ARRANGE
        when(supplierRepository.findById(99L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> supplierService.deleteSupplier(99L))
            .isInstanceOf(SupplierException.class)
            .hasMessageContaining("not found");
        verify(supplierRepository, times(1)).findById(99L);
        verify(supplierRepository, never()).delete(any());
    }

    @Test
    @DisplayName("deleteSupplier → throws SupplierException when ID is null")
    void deleteSupplier_shouldThrow_whenIdNull() {
        // ACT & ASSERT
        assertThatThrownBy(() -> supplierService.deleteSupplier(null))
            .isInstanceOf(SupplierException.class);
        verify(supplierRepository, never()).delete(any());
    }
}

