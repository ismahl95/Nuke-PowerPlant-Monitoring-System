package com.ihl95.nuclear.nuclearplant.application.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ihl95.nuclear.nuclearplant.domain.NuclearPlant;

/**
 * AuditObserver implementation - logs all NuclearPlant changes for compliance.
 *
 * Responsibility:
 * - Record all mutations (create, update, delete) to audit log
 * - Maintain compliance trail for regulatory requirements
 * - Track who changed what and when
 */
@Component
public class AuditObserver implements NuclearPlantObserver {

    private static final Logger auditLogger = LoggerFactory.getLogger("AuditLogger");
    private static final Logger logger = LoggerFactory.getLogger(AuditObserver.class);

    @Override
    public void onNuclearPlantCreated(NuclearPlant plant) {
        try {
            auditLogger.info("NUCLEAR_PLANT_CREATED | ID: {} | Name: {} | Location: {}",
                plant.getId(), plant.getName(), plant.getLocation());
            logger.debug("Audit recorded for plant creation: {}", plant.getId());
        } catch (Exception e) {
            logger.error("Error writing audit log for plant creation", e);
            // Don't throw - audit failure shouldn't break business operation
        }
    }

    @Override
    public void onNuclearPlantUpdated(NuclearPlant plant) {
        try {
            auditLogger.info("NUCLEAR_PLANT_UPDATED | ID: {} | Name: {} | Location: {} | LastModified: {}",
                plant.getId(), plant.getName(), plant.getLocation(), plant.getLastModifiedDate());
            logger.debug("Audit recorded for plant update: {}", plant.getId());
        } catch (Exception e) {
            logger.error("Error writing audit log for plant update", e);
        }
    }

    @Override
    public void onNuclearPlantDeleted(NuclearPlant plant) {
        try {
            auditLogger.info("NUCLEAR_PLANT_DELETED | ID: {} | Name: {} | Location: {}",
                plant.getId(), plant.getName(), plant.getLocation());
            logger.debug("Audit recorded for plant deletion: {}", plant.getId());
        } catch (Exception e) {
            logger.error("Error writing audit log for plant deletion", e);
        }
    }
}

