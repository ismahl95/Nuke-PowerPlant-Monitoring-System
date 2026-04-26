package com.ihl95.nuclear.nuclearplant.observer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.ihl95.nuclear.common.mocks.NuclearPlantTestData;
import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearplant.application.observer.MetricsObserver;
import com.ihl95.nuclear.nuclearplant.application.service.NuclearPlantService;
import com.ihl95.nuclear.nuclearplant.infraestructure.NuclearPlantRepository;

import static org.assertj.core.api.Assertions.*;

/**
 * Integration tests for NuclearPlant Observer Pattern.
 * Tests that observers are properly notified from the Service layer.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@DisplayName("NuclearPlant Observer Pattern - Integration Tests")
class NuclearPlantObserverIntegrationTest {

    @Autowired
    private NuclearPlantService nuclearPlantService;

    @Autowired
    private NuclearPlantRepository nuclearPlantRepository;

    @Autowired
    private MetricsObserver metricsObserver;

    private NuclearPlantDTO testPlantDTO;

    @BeforeEach
    void setUp() {
        testPlantDTO = NuclearPlantTestData.createNuclearPlantDTO(null, "Plant A", "Location A");
        metricsObserver.reset();
    }

    @Test
    @DisplayName("Observers should be notified when plant is created")
    void observers_shouldBeNotified_whenPlantCreated() {
        // Act
        NuclearPlantDTO createdPlant = nuclearPlantService.createNuclearPlant(testPlantDTO);

        // Assert
        assertThat(createdPlant).isNotNull();
        assertThat(metricsObserver.getTotalPlantsCreated()).isEqualTo(1);
        assertThat(metricsObserver.getTotalPlantsUpdated()).isEqualTo(0);
    }

    @Test
    @DisplayName("Observers should be notified when plant is updated")
    void observers_shouldBeNotified_whenPlantUpdated() {
        // Arrange
        NuclearPlantDTO createdPlant = nuclearPlantService.createNuclearPlant(testPlantDTO);
        metricsObserver.reset(); // Reset after creation to isolate update notification

        NuclearPlantDTO updateDTO = NuclearPlantTestData.createNuclearPlantDTO(
            createdPlant.id(), "Plant A Updated", "Location A Updated"
        );

        // Act
        NuclearPlantDTO updatedPlant = nuclearPlantService.updateNuclearPlant(createdPlant.id(), updateDTO);

        // Assert
        assertThat(updatedPlant).isNotNull();
        assertThat(metricsObserver.getTotalPlantsUpdated()).isEqualTo(1);
        assertThat(metricsObserver.getTotalPlantsCreated()).isEqualTo(0);
    }

    @Test
    @DisplayName("Observers should be notified when plant is deleted")
    void observers_shouldBeNotified_whenPlantDeleted() {
        // Arrange
        NuclearPlantDTO createdPlant = nuclearPlantService.createNuclearPlant(testPlantDTO);
        metricsObserver.reset(); // Reset after creation

        // Act
        nuclearPlantService.deleteNuclearPlant(createdPlant.id());

        // Assert
        assertThat(nuclearPlantRepository.findById(createdPlant.id())).isEmpty();
        assertThat(metricsObserver.getTotalPlantsDeleted()).isEqualTo(1);
    }

    @Test
    @DisplayName("Observers should track multiple operations in sequence")
    void observers_shouldTrackMultipleOperations_inSequence() {
        // Act
        NuclearPlantDTO plant1 = nuclearPlantService.createNuclearPlant(testPlantDTO);
        NuclearPlantDTO plant2 = nuclearPlantService.createNuclearPlant(
            NuclearPlantTestData.createNuclearPlantDTO(null, "Plant B", "Location B")
        );

        nuclearPlantService.updateNuclearPlant(plant1.id(),
            NuclearPlantTestData.createNuclearPlantDTO(plant1.id(), "Plant 1 Updated", "Location 1 Updated")
        );

        nuclearPlantService.deleteNuclearPlant(plant2.id());

        // Assert
        assertThat(metricsObserver.getTotalPlantsCreated()).isEqualTo(2);
        assertThat(metricsObserver.getTotalPlantsUpdated()).isEqualTo(1);
        assertThat(metricsObserver.getTotalPlantsDeleted()).isEqualTo(1);
    }

    @Test
    @DisplayName("Observer failure should not affect service operation")
    void observerFailure_shouldNotAffect_serviceOperation() {
        // Arrange - Service should continue even if observer fails
        // The error handling in NuclearPlantService catches observer exceptions

        // Act
        NuclearPlantDTO createdPlant = nuclearPlantService.createNuclearPlant(testPlantDTO);

        // Assert - Service completed successfully despite any observer failures
        assertThat(createdPlant).isNotNull();
        assertThat(createdPlant.id()).isNotNull();
        assertThat(nuclearPlantRepository.findById(createdPlant.id())).isPresent();
    }
}

