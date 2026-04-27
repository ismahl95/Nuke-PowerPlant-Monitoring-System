package com.ihl95.nuclear.supplier.application.observer;

import com.ihl95.nuclear.supplier.domain.Supplier;

/**
 * Observer interface for Supplier domain events.
 * Implements Observer Pattern (Gang of Four).
 *
 * Purpose: Notify interested parties when Supplier state changes (create, update, delete)
 * without coupling the service to specific subscribers.
 *
 * Usage:
 * - SupplierService notifies all registered observers on CRUD operations
 * - Each observer reacts independently (auditing, alerting, metrics, etc.)
 * - New observers can be added without modifying SupplierService
 *
 * Example subscribers:
 * - AuditObserver: logs all changes for compliance
 * - AlertObserver: sends notifications for critical changes
 * - MetricsObserver: updates monitoring dashboards
 */
public interface SupplierObserver {

    /**
     * Called when a new Supplier is created and persisted.
     * @param supplier The newly created supplier
     */
    void onSupplierCreated(Supplier supplier);

    /**
     * Called when an existing Supplier is updated.
     * @param supplier The updated supplier
     */
    void onSupplierUpdated(Supplier supplier);

    /**
     * Called when a Supplier is deleted.
     * @param supplier The deleted supplier (if still needed for reference)
     */
    void onSupplierDeleted(Supplier supplier);
}


