package com.ihl95.nuclear.nuclearplant.application.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ihl95.nuclear.nuclearplant.domain.NuclearPlant;

/**
 * MetricsObserver implementation - updates monitoring dashboards and metrics.
 *
 * Responsibility:
 * - Track plant lifecycle metrics
 * - Update real-time dashboards
 * - Collect statistics for reporting
 */
@Component
public class MetricsObserver implements NuclearPlantObserver {

    private static final Logger logger = LoggerFactory.getLogger(MetricsObserver.class);

    private int totalPlantsCreated = 0;
    private int totalPlantsUpdated = 0;
    private int totalPlantsDeleted = 0;

    @Override
    public void onNuclearPlantCreated(NuclearPlant plant) {
        try {
            totalPlantsCreated++;
            logger.info("Metric: Plant created | Total: {} | Plant ID: {}",
                totalPlantsCreated, plant.getId());
            // In production: Update Prometheus/Grafana metrics
            // Example: meterRegistry.counter("plant.created").increment();
        } catch (Exception e) {
            logger.error("Error updating metrics for plant creation", e);
        }
    }

    @Override
    public void onNuclearPlantUpdated(NuclearPlant plant) {
        try {
            totalPlantsUpdated++;
            logger.info("Metric: Plant updated | Total: {} | Plant ID: {}",
                totalPlantsUpdated, plant.getId());
            // In production: Update metrics
            // Example: meterRegistry.counter("plant.updated").increment();
        } catch (Exception e) {
            logger.error("Error updating metrics for plant update", e);
        }
    }

    @Override
    public void onNuclearPlantDeleted(NuclearPlant plant) {
        try {
            totalPlantsDeleted++;
            logger.info("Metric: Plant deleted | Total: {} | Plant ID: {}",
                totalPlantsDeleted, plant.getId());
            // In production: Update metrics
            // Example: meterRegistry.counter("plant.deleted").increment();
        } catch (Exception e) {
            logger.error("Error updating metrics for plant deletion", e);
        }
    }

    // Test support methods
    public int getTotalPlantsCreated() {
        return totalPlantsCreated;
    }

    public int getTotalPlantsUpdated() {
        return totalPlantsUpdated;
    }

    public int getTotalPlantsDeleted() {
        return totalPlantsDeleted;
    }

    public void reset() {
        totalPlantsCreated = 0;
        totalPlantsUpdated = 0;
        totalPlantsDeleted = 0;
    }
}

