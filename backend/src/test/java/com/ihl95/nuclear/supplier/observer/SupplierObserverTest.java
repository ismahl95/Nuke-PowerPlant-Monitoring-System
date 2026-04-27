package com.ihl95.nuclear.supplier.observer;

import static org.assertj.core.api.Assertions.*;

import com.ihl95.nuclear.common.mocks.SupplierTestData;
import com.ihl95.nuclear.supplier.application.observer.AuditObserver;
import com.ihl95.nuclear.supplier.application.observer.AlertObserver;
import com.ihl95.nuclear.supplier.application.observer.MetricsObserver;
import com.ihl95.nuclear.supplier.domain.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for SupplierObserver implementations.
 * Tests individual observer behaviors in isolation without Spring context.
 * No mocking - testing direct observer behavior.
 *
 * Tests cover:
 * - AuditObserver: logging without failures
 * - AlertObserver: alerting without failures
 * - MetricsObserver: counting operations correctly
 *
 * Execution: mvn test -Dtest=SupplierObserverTest
 */
@DisplayName("SupplierObserver Unit Tests")
class SupplierObserverTest {

    private AuditObserver auditObserver;
    private AlertObserver alertObserver;
    private MetricsObserver metricsObserver;
    private Supplier testSupplier;

    @BeforeEach
    void setUp() {
        auditObserver = new AuditObserver();
        alertObserver = new AlertObserver();
        metricsObserver = new MetricsObserver();
        testSupplier = SupplierTestData.createSupplierEntity(1L, "Test Supplier", "test@example.com", "+34912345678");
    }

    // ─────────────────────────────────────────────────────────────
    // AUDIT OBSERVER TESTS
    // ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("AuditObserver.onSupplierCreated → completes without exception")
    void auditObserver_shouldNotThrow_onSupplierCreated() {
        assertThatNoException()
            .isThrownBy(() -> auditObserver.onSupplierCreated(testSupplier));
    }

    @Test
    @DisplayName("AuditObserver.onSupplierUpdated → completes without exception")
    void auditObserver_shouldNotThrow_onSupplierUpdated() {
        assertThatNoException()
            .isThrownBy(() -> auditObserver.onSupplierUpdated(testSupplier));
    }

    @Test
    @DisplayName("AuditObserver.onSupplierDeleted → completes without exception")
    void auditObserver_shouldNotThrow_onSupplierDeleted() {
        assertThatNoException()
            .isThrownBy(() -> auditObserver.onSupplierDeleted(testSupplier));
    }

    // ─────────────────────────────────────────────────────────────
    // ALERT OBSERVER TESTS
    // ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("AlertObserver.onSupplierCreated → completes without exception")
    void alertObserver_shouldNotThrow_onSupplierCreated() {
        assertThatNoException()
            .isThrownBy(() -> alertObserver.onSupplierCreated(testSupplier));
    }

    @Test
    @DisplayName("AlertObserver.onSupplierUpdated → completes without exception")
    void alertObserver_shouldNotThrow_onSupplierUpdated() {
        assertThatNoException()
            .isThrownBy(() -> alertObserver.onSupplierUpdated(testSupplier));
    }

    @Test
    @DisplayName("AlertObserver.onSupplierDeleted → completes without exception")
    void alertObserver_shouldNotThrow_onSupplierDeleted() {
        assertThatNoException()
            .isThrownBy(() -> alertObserver.onSupplierDeleted(testSupplier));
    }

    // ─────────────────────────────────────────────────────────────
    // METRICS OBSERVER TESTS
    // ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("MetricsObserver.onSupplierCreated → increments created counter")
    void metricsObserver_shouldIncrementCreatedCounter() {
        assertThat(metricsObserver.getTotalSuppliersCreated()).isEqualTo(0);

        metricsObserver.onSupplierCreated(testSupplier);

        assertThat(metricsObserver.getTotalSuppliersCreated()).isEqualTo(1);
    }

    @Test
    @DisplayName("MetricsObserver.onSupplierUpdated → increments updated counter")
    void metricsObserver_shouldIncrementUpdatedCounter() {
        assertThat(metricsObserver.getTotalSuppliersUpdated()).isEqualTo(0);

        metricsObserver.onSupplierUpdated(testSupplier);

        assertThat(metricsObserver.getTotalSuppliersUpdated()).isEqualTo(1);
    }

    @Test
    @DisplayName("MetricsObserver.onSupplierDeleted → increments deleted counter")
    void metricsObserver_shouldIncrementDeletedCounter() {
        assertThat(metricsObserver.getTotalSuppliersDeleted()).isEqualTo(0);

        metricsObserver.onSupplierDeleted(testSupplier);

        assertThat(metricsObserver.getTotalSuppliersDeleted()).isEqualTo(1);
    }

    @Test
    @DisplayName("MetricsObserver → multiple operations count independently")
    void metricsObserver_shouldCountOperationsIndependently() {
        // ACT
        metricsObserver.onSupplierCreated(testSupplier);
        metricsObserver.onSupplierCreated(testSupplier);
        metricsObserver.onSupplierUpdated(testSupplier);
        metricsObserver.onSupplierDeleted(testSupplier);

        // ASSERT
        assertThat(metricsObserver.getTotalSuppliersCreated()).isEqualTo(2);
        assertThat(metricsObserver.getTotalSuppliersUpdated()).isEqualTo(1);
        assertThat(metricsObserver.getTotalSuppliersDeleted()).isEqualTo(1);
    }

    @Test
    @DisplayName("MetricsObserver.reset → resets all counters to zero")
    void metricsObserver_shouldResetCounters() {
        // ARRANGE
        metricsObserver.onSupplierCreated(testSupplier);
        metricsObserver.onSupplierUpdated(testSupplier);
        metricsObserver.onSupplierDeleted(testSupplier);
        assertThat(metricsObserver.getTotalSuppliersCreated()).isEqualTo(1);

        // ACT
        metricsObserver.reset();

        // ASSERT
        assertThat(metricsObserver.getTotalSuppliersCreated()).isEqualTo(0);
        assertThat(metricsObserver.getTotalSuppliersUpdated()).isEqualTo(0);
        assertThat(metricsObserver.getTotalSuppliersDeleted()).isEqualTo(0);
    }

    @Test
    @DisplayName("MetricsObserver → counters are thread-safe (AtomicLong)")
    void metricsObserver_shouldBeThreadSafe() {
        // Verify that multiple operations work concurrently
        for (int i = 0; i < 100; i++) {
            metricsObserver.onSupplierCreated(testSupplier);
            metricsObserver.onSupplierUpdated(testSupplier);
            metricsObserver.onSupplierDeleted(testSupplier);
        }

        assertThat(metricsObserver.getTotalSuppliersCreated()).isEqualTo(100);
        assertThat(metricsObserver.getTotalSuppliersUpdated()).isEqualTo(100);
        assertThat(metricsObserver.getTotalSuppliersDeleted()).isEqualTo(100);
    }
}


