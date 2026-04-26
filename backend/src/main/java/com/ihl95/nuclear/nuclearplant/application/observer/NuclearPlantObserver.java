package com.ihl95.nuclear.nuclearplant.application.observer;

import com.ihl95.nuclear.nuclearplant.domain.NuclearPlant;

/**
 * Observer interface for NuclearPlant domain events.
 * Implements Observer Pattern (Gang of Four).
 *
 * Purpose: Notify interested parties when NuclearPlant state changes (create, update, delete)
 * without coupling the service to specific subscribers.
 *
 * Usage:
 * - NuclearPlantService notifies all registered observers on CRUD operations
 * - Each observer reacts independently (auditing, alerting, metrics, etc.)
 * - New observers can be added without modifying NuclearPlantService
 *
 * Example subscribers:
 * - AuditObserver: logs all changes for compliance
 * - AlertObserver: sends notifications for critical changes
 * - MetricsObserver: updates monitoring dashboards
 */
public interface NuclearPlantObserver {

    /**
     * Called when a new NuclearPlant is created and persisted.
     * @param plant The newly created plant
     */
    void onNuclearPlantCreated(NuclearPlant plant);

    /**
     * Called when an existing NuclearPlant is updated.
     * @param plant The updated plant
     */
    void onNuclearPlantUpdated(NuclearPlant plant);

    /**
     * Called when a NuclearPlant is deleted.
     * @param plant The deleted plant (if still needed for reference)
     */
    void onNuclearPlantDeleted(NuclearPlant plant);
}

