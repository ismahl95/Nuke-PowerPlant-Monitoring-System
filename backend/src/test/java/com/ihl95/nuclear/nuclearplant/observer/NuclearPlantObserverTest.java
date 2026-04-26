package com.ihl95.nuclear.nuclearplant.observer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ihl95.nuclear.common.mocks.NuclearPlantTestData;
import com.ihl95.nuclear.nuclearplant.application.observer.AuditObserver;
import com.ihl95.nuclear.nuclearplant.application.observer.AlertObserver;
import com.ihl95.nuclear.nuclearplant.application.observer.MetricsObserver;
import com.ihl95.nuclear.nuclearplant.domain.NuclearPlant;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for NuclearPlant Observer Pattern implementations.
 * Tests each observer independently without Spring context.
 */
@DisplayName("NuclearPlant Observer Pattern - Unit Tests")
class NuclearPlantObserverTest {

    private AuditObserver auditObserver;
    private AlertObserver alertObserver;
    private MetricsObserver metricsObserver;
    private NuclearPlant testPlant;

    @BeforeEach
    void setUp() {
        auditObserver = new AuditObserver();
        alertObserver = new AlertObserver();
        metricsObserver = new MetricsObserver();
        testPlant = NuclearPlantTestData.createNuclearPlantEntity(1L, "Test Plant", "Test Location");
    }

    // ── AUDIT OBSERVER TESTS ──

    @Test
    @DisplayName("AuditObserver should handle plant creation without throwing")
    void auditObserver_shouldNotThrow_onPlantCreated() {
        assertThatNoException().isThrownBy(() ->
            auditObserver.onNuclearPlantCreated(testPlant)
        );
    }

    @Test
    @DisplayName("AuditObserver should handle plant update without throwing")
    void auditObserver_shouldNotThrow_onPlantUpdated() {
        assertThatNoException().isThrownBy(() ->
            auditObserver.onNuclearPlantUpdated(testPlant)
        );
    }

    @Test
    @DisplayName("AuditObserver should handle plant deletion without throwing")
    void auditObserver_shouldNotThrow_onPlantDeleted() {
        assertThatNoException().isThrownBy(() ->
            auditObserver.onNuclearPlantDeleted(testPlant)
        );
    }

    // ── ALERT OBSERVER TESTS ──

    @Test
    @DisplayName("AlertObserver should handle plant creation without throwing")
    void alertObserver_shouldNotThrow_onPlantCreated() {
        assertThatNoException().isThrownBy(() ->
            alertObserver.onNuclearPlantCreated(testPlant)
        );
    }

    @Test
    @DisplayName("AlertObserver should handle plant update without throwing")
    void alertObserver_shouldNotThrow_onPlantUpdated() {
        assertThatNoException().isThrownBy(() ->
            alertObserver.onNuclearPlantUpdated(testPlant)
        );
    }

    @Test
    @DisplayName("AlertObserver should handle plant deletion without throwing")
    void alertObserver_shouldNotThrow_onPlantDeleted() {
        assertThatNoException().isThrownBy(() ->
            alertObserver.onNuclearPlantDeleted(testPlant)
        );
    }

    // ── METRICS OBSERVER TESTS ──

    @Test
    @DisplayName("MetricsObserver should increment plant created counter")
    void metricsObserver_shouldIncrementCreatedCounter() {
        // Arrange
        metricsObserver.reset();

        // Act
        metricsObserver.onNuclearPlantCreated(testPlant);
        metricsObserver.onNuclearPlantCreated(testPlant);

        // Assert
        assertThat(metricsObserver.getTotalPlantsCreated()).isEqualTo(2);
        assertThat(metricsObserver.getTotalPlantsUpdated()).isEqualTo(0);
        assertThat(metricsObserver.getTotalPlantsDeleted()).isEqualTo(0);
    }

    @Test
    @DisplayName("MetricsObserver should increment plant updated counter")
    void metricsObserver_shouldIncrementUpdatedCounter() {
        // Arrange
        metricsObserver.reset();

        // Act
        metricsObserver.onNuclearPlantUpdated(testPlant);
        metricsObserver.onNuclearPlantUpdated(testPlant);
        metricsObserver.onNuclearPlantUpdated(testPlant);

        // Assert
        assertThat(metricsObserver.getTotalPlantsUpdated()).isEqualTo(3);
        assertThat(metricsObserver.getTotalPlantsCreated()).isEqualTo(0);
    }

    @Test
    @DisplayName("MetricsObserver should increment plant deleted counter")
    void metricsObserver_shouldIncrementDeletedCounter() {
        // Arrange
        metricsObserver.reset();

        // Act
        metricsObserver.onNuclearPlantDeleted(testPlant);

        // Assert
        assertThat(metricsObserver.getTotalPlantsDeleted()).isEqualTo(1);
    }

    @Test
    @DisplayName("MetricsObserver should track all metrics independently")
    void metricsObserver_shouldTrackAllMetricsIndependently() {
        // Arrange
        metricsObserver.reset();

        // Act
        metricsObserver.onNuclearPlantCreated(testPlant);
        metricsObserver.onNuclearPlantCreated(testPlant);
        metricsObserver.onNuclearPlantUpdated(testPlant);
        metricsObserver.onNuclearPlantDeleted(testPlant);

        // Assert
        assertThat(metricsObserver.getTotalPlantsCreated()).isEqualTo(2);
        assertThat(metricsObserver.getTotalPlantsUpdated()).isEqualTo(1);
        assertThat(metricsObserver.getTotalPlantsDeleted()).isEqualTo(1);
    }

    @Test
    @DisplayName("MetricsObserver should reset metrics")
    void metricsObserver_shouldResetMetrics() {
        // Arrange
        metricsObserver.onNuclearPlantCreated(testPlant);
        metricsObserver.onNuclearPlantUpdated(testPlant);
        assertThat(metricsObserver.getTotalPlantsCreated()).isEqualTo(1);

        // Act
        metricsObserver.reset();

        // Assert
        assertThat(metricsObserver.getTotalPlantsCreated()).isEqualTo(0);
        assertThat(metricsObserver.getTotalPlantsUpdated()).isEqualTo(0);
        assertThat(metricsObserver.getTotalPlantsDeleted()).isEqualTo(0);
    }
}

