package com.ihl95.nuclear.supplier.application.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ihl95.nuclear.supplier.domain.Supplier;

/**
 * AlertObserver implementation - notifies critical supplier changes.
 *
 * Responsibility:
 * - Generate alerts when suppliers are created, modified, or deleted
 * - Track supplier lifecycle events
 * - Ready for integration with notification system (email, SMS, Slack, etc.)
 */
@Component("supplierAlertObserver")
public class AlertObserver implements SupplierObserver {

    private static final Logger logger = LoggerFactory.getLogger(AlertObserver.class);

    @Override
    public void onSupplierCreated(Supplier supplier) {
        try {
            logger.info("🔔 ALERT: New supplier created - ID: {} | Name: {} | Contact: {}",
                supplier.getId(), supplier.getName(), supplier.getContact());
            // TODO: Integrate with notification system (email, SMS, Slack, etc.)
            // notificationService.sendAlert("Supplier Created", supplier);
        } catch (Exception e) {
            logger.error("Error sending alert for supplier creation", e);
            // Don't throw - alert failure shouldn't break business operation
        }
    }

    @Override
    public void onSupplierUpdated(Supplier supplier) {
        try {
            logger.info("🔔 ALERT: Supplier updated - ID: {} | Name: {} | Contact: {}",
                supplier.getId(), supplier.getName(), supplier.getContact());
            // TODO: Integrate with notification system
            // notificationService.sendAlert("Supplier Updated", supplier);
        } catch (Exception e) {
            logger.error("Error sending alert for supplier update", e);
        }
    }

    @Override
    public void onSupplierDeleted(Supplier supplier) {
        try {
            logger.warn("🔔 ALERT: Supplier deleted - ID: {} | Name: {} | Contact: {}",
                supplier.getId(), supplier.getName(), supplier.getContact());
            // TODO: Integrate with notification system
            // notificationService.sendAlert("Supplier Deleted", supplier);
        } catch (Exception e) {
            logger.error("Error sending alert for supplier deletion", e);
        }
    }
}



