package com.ihl95.nuclear.supplier.application.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ihl95.nuclear.supplier.domain.Supplier;

/**
 * AuditObserver implementation - logs all Supplier changes for compliance.
 *
 * Responsibility:
 * - Record all mutations (create, update, delete) to audit log
 * - Maintain compliance trail for regulatory requirements
 * - Track who changed what and when
 */
@Component("supplierAuditObserver")
public class AuditObserver implements SupplierObserver {

    private static final Logger auditLogger = LoggerFactory.getLogger("AuditLogger");
    private static final Logger logger = LoggerFactory.getLogger(AuditObserver.class);

    @Override
    public void onSupplierCreated(Supplier supplier) {
        try {
            auditLogger.info("SUPPLIER_CREATED | ID: {} | Name: {} | Contact: {} | Phone: {}",
                supplier.getId(), supplier.getName(), supplier.getContact(), supplier.getPhone());
            logger.debug("Audit recorded for supplier creation: {}", supplier.getId());
        } catch (Exception e) {
            logger.error("Error writing audit log for supplier creation", e);
            // Don't throw - audit failure shouldn't break business operation
        }
    }

    @Override
    public void onSupplierUpdated(Supplier supplier) {
        try {
            auditLogger.info("SUPPLIER_UPDATED | ID: {} | Name: {} | Contact: {} | Phone: {} | LastModified: {}",
                supplier.getId(), supplier.getName(), supplier.getContact(), supplier.getPhone(),
                supplier.getLastModifiedDate());
            logger.debug("Audit recorded for supplier update: {}", supplier.getId());
        } catch (Exception e) {
            logger.error("Error writing audit log for supplier update", e);
        }
    }

    @Override
    public void onSupplierDeleted(Supplier supplier) {
        try {
            auditLogger.info("SUPPLIER_DELETED | ID: {} | Name: {} | Contact: {} | Phone: {}",
                supplier.getId(), supplier.getName(), supplier.getContact(), supplier.getPhone());
            logger.debug("Audit recorded for supplier deletion: {}", supplier.getId());
        } catch (Exception e) {
            logger.error("Error writing audit log for supplier deletion", e);
        }
    }
}



