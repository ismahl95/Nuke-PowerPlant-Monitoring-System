package com.ihl95.nuclear.nuclearplant.application.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ihl95.nuclear.nuclearplant.domain.NuclearPlant;

/**
 * AlertObserver implementation - sends alerts for critical NuclearPlant changes.
 *
 * Responsibility:
 * - Evaluate if change requires operator notification
 * - Send critical alerts to monitoring systems
 * - Trigger immediate response procedures if needed
 */
@Component
public class AlertObserver implements NuclearPlantObserver {

    private static final Logger logger = LoggerFactory.getLogger(AlertObserver.class);

    @Override
    public void onNuclearPlantCreated(NuclearPlant plant) {
        try {
            logger.info("Alert: New nuclear plant created - ID: {} Name: {}",
                plant.getId(), plant.getName());
            // In production: Send notification to operators
            // Example: notificationService.sendAlert("PLANT_CREATED", plant);
        } catch (Exception e) {
            logger.error("Error processing alert for plant creation", e);
        }
    }

    @Override
    public void onNuclearPlantUpdated(NuclearPlant plant) {
        try {
            // Check if location change (might indicate facility relocation)
            if (plant.getLocation() != null) {
                logger.warn("Alert: Nuclear plant location updated - ID: {} Location: {}",
                    plant.getId(), plant.getLocation());
                // In production: Send alert to relevant stakeholders
            }
        } catch (Exception e) {
            logger.error("Error processing alert for plant update", e);
        }
    }

    @Override
    public void onNuclearPlantDeleted(NuclearPlant plant) {
        try {
            logger.error("CRITICAL ALERT: Nuclear plant deleted - ID: {} Name: {}. Manual review required.",
                plant.getId(), plant.getName());
            // In production: Trigger escalation procedure
            // Example: escalationService.triggerReview("PLANT_DELETION", plant);
        } catch (Exception e) {
            logger.error("Error processing alert for plant deletion", e);
        }
    }
}

