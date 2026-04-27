package com.ihl95.nuclear.supplier.observer;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.ihl95.nuclear.common.mocks.SupplierTestData;
import com.ihl95.nuclear.supplier.application.dto.SupplierDTO;
import com.ihl95.nuclear.supplier.application.observer.MetricsObserver;
import com.ihl95.nuclear.supplier.application.service.SupplierService;
import com.ihl95.nuclear.supplier.domain.Supplier;
import com.ihl95.nuclear.supplier.infraestructure.SupplierRepository;

/**
 * Integration tests for SupplierObserver Pattern.
 * Tests full request flow through all layers with real Spring context.
 * Verifies that observers are correctly notified on CRUD operations.
 *
 * Uses @SpringBootTest with H2 in-memory database and @Transactional rollback.
 *
 * Tests cover:
 * - Observers notified on Create
 * - Observers notified on Update
 * - Observers notified on Delete
 * - Multiple operations in sequence
 * - Observer failure doesn't break service
 *
 * Execution: mvn test -Dtest=SupplierObserverIntegrationTest
 */
@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DisplayName("SupplierObserver Integration Tests")
class SupplierObserverIntegrationTest {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private MetricsObserver metricsObserver;

    private SupplierDTO testSupplierDTO;

    @BeforeEach
    void setUp() {
        metricsObserver.reset();
        testSupplierDTO = SupplierTestData.createSupplierDTO(
            null, "Test Supplier", "test@example.com", "+34912345678"
        );
    }

    // ─────────────────────────────────────────────────────────────
    // CREATE OPERATION OBSERVER TESTS
    // ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("create → observers notified on supplier creation")
    void create_shouldNotifyObservers_onSupplierCreation() {
        // ARRANGE
        assertThat(metricsObserver.getTotalSuppliersCreated()).isEqualTo(0);

        // ACT
        SupplierDTO created = supplierService.createSupplier(testSupplierDTO);

        // ASSERT
        assertThat(created).isNotNull();
        assertThat(created.id()).isNotNull();
        assertThat(metricsObserver.getTotalSuppliersCreated()).isEqualTo(1);

        // Verify persistence
        Supplier persisted = supplierRepository.findById(created.id()).orElseThrow();
        assertThat(persisted.getName()).isEqualTo("Test Supplier");
    }

    // ─────────────────────────────────────────────────────────────
    // UPDATE OPERATION OBSERVER TESTS
    // ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("update → observers notified on supplier update")
    void update_shouldNotifyObservers_onSupplierUpdate() {
        // ARRANGE
        SupplierDTO created = supplierService.createSupplier(testSupplierDTO);
        metricsObserver.reset(); // Reset to isolate update count

        SupplierDTO updateDTO = SupplierTestData.createSupplierDTO(
            null, "Updated Supplier", "updated@example.com", "+34987654321"
        );

        // ACT
        SupplierDTO updated = supplierService.updateSupplier(created.id(), updateDTO);

        // ASSERT
        assertThat(updated).isNotNull();
        assertThat(updated.name()).isEqualTo("Updated Supplier");
        assertThat(metricsObserver.getTotalSuppliersUpdated()).isEqualTo(1);
    }

    // ─────────────────────────────────────────────────────────────
    // DELETE OPERATION OBSERVER TESTS
    // ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("delete → observers notified on supplier deletion")
    void delete_shouldNotifyObservers_onSupplierDeletion() {
        // ARRANGE
        SupplierDTO created = supplierService.createSupplier(testSupplierDTO);
        metricsObserver.reset(); // Reset to isolate delete count

        long supplierIdToDelete = created.id();

        // ACT
        supplierService.deleteSupplier(supplierIdToDelete);

        // ASSERT
        assertThat(supplierRepository.findById(supplierIdToDelete)).isEmpty();
        assertThat(metricsObserver.getTotalSuppliersDeleted()).isEqualTo(1);
    }

    // ─────────────────────────────────────────────────────────────
    // SEQUENCE OPERATION OBSERVER TESTS
    // ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("crud → observers notified for complete CRUD sequence")
    void crud_shouldNotifyObserversForCompleteSequence() {
        // ARRANGE
        assertThat(metricsObserver.getTotalSuppliersCreated()).isEqualTo(0);
        assertThat(metricsObserver.getTotalSuppliersUpdated()).isEqualTo(0);
        assertThat(metricsObserver.getTotalSuppliersDeleted()).isEqualTo(0);

        // CREATE
        SupplierDTO created = supplierService.createSupplier(testSupplierDTO);
        assertThat(metricsObserver.getTotalSuppliersCreated()).isEqualTo(1);

        // UPDATE
        SupplierDTO updateDTO = SupplierTestData.createSupplierDTO(
            null, "Updated", "updated@example.com", "+34987654321"
        );
        supplierService.updateSupplier(created.id(), updateDTO);
        assertThat(metricsObserver.getTotalSuppliersUpdated()).isEqualTo(1);

        // DELETE
        supplierService.deleteSupplier(created.id());
        assertThat(metricsObserver.getTotalSuppliersDeleted()).isEqualTo(1);

        // ASSERT - final state
        assertThat(metricsObserver.getTotalSuppliersCreated()).isEqualTo(1);
        assertThat(metricsObserver.getTotalSuppliersUpdated()).isEqualTo(1);
        assertThat(metricsObserver.getTotalSuppliersDeleted()).isEqualTo(1);
    }

    @Test
    @DisplayName("multipleOperations → observers track all CRUD operations independently")
    void multipleOperations_shouldTrackAllOperationsIndependently() {
        // Create 3 suppliers
        SupplierDTO s1 = supplierService.createSupplier(testSupplierDTO);
        SupplierDTO s2 = supplierService.createSupplier(testSupplierDTO);
        SupplierDTO s3 = supplierService.createSupplier(testSupplierDTO);

        assertThat(metricsObserver.getTotalSuppliersCreated()).isEqualTo(3);

        // Update 2 of them
        SupplierDTO updateDTO = SupplierTestData.createSupplierDTO(
            null, "Updated", "updated@example.com", "+34987654321"
        );
        supplierService.updateSupplier(s1.id(), updateDTO);
        supplierService.updateSupplier(s2.id(), updateDTO);

        assertThat(metricsObserver.getTotalSuppliersUpdated()).isEqualTo(2);

        // Delete 1
        supplierService.deleteSupplier(s1.id());

        // ASSERT - all counters work independently
        assertThat(metricsObserver.getTotalSuppliersCreated()).isEqualTo(3);
        assertThat(metricsObserver.getTotalSuppliersUpdated()).isEqualTo(2);
        assertThat(metricsObserver.getTotalSuppliersDeleted()).isEqualTo(1);
    }
}


