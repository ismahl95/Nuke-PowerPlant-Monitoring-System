package com.ihl95.nuclear.supplier.application.observer;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ihl95.nuclear.supplier.domain.Supplier;

/**
 * MetricsObserver implementation - tracks supplier lifecycle metrics.
 *
 * Responsibility:
 * - Count supplier create/update/delete operations
 * - Provide metrics for monitoring and dashboards
 * - Thread-safe counter implementation using AtomicLong
 */
@Component("supplierMetricsObserver")
public class MetricsObserver implements SupplierObserver {

    private static final Logger logger = LoggerFactory.getLogger(MetricsObserver.class);

    private final AtomicLong totalSuppliersCreated = new AtomicLong(0);
    private final AtomicLong totalSuppliersUpdated = new AtomicLong(0);
    private final AtomicLong totalSuppliersDeleted = new AtomicLong(0);

    @Override
    public void onSupplierCreated(Supplier supplier) {
        try {
            long count = totalSuppliersCreated.incrementAndGet();
            logger.debug("📊 METRICS: Supplier created - Total created: {} | ID: {}", count, supplier.getId());
        } catch (Exception e) {
            logger.error("Error updating metrics for supplier creation", e);
        }
    }

    @Override
    public void onSupplierUpdated(Supplier supplier) {
        try {
            long count = totalSuppliersUpdated.incrementAndGet();
            logger.debug("📊 METRICS: Supplier updated - Total updated: {} | ID: {}", count, supplier.getId());
        } catch (Exception e) {
            logger.error("Error updating metrics for supplier update", e);
        }
    }

    @Override
    public void onSupplierDeleted(Supplier supplier) {
        try {
            long count = totalSuppliersDeleted.incrementAndGet();
            logger.debug("📊 METRICS: Supplier deleted - Total deleted: {} | ID: {}", count, supplier.getId());
        } catch (Exception e) {
            logger.error("Error updating metrics for supplier deletion", e);
        }
    }

    /**
     * Get total suppliers created since application start or last reset
     */
    public long getTotalSuppliersCreated() {
        return totalSuppliersCreated.get();
    }

    /**
     * Get total suppliers updated since application start or last reset
     */
    public long getTotalSuppliersUpdated() {
        return totalSuppliersUpdated.get();
    }

    /**
     * Get total suppliers deleted since application start or last reset
     */
    public long getTotalSuppliersDeleted() {
        return totalSuppliersDeleted.get();
    }

    /**
     * Reset all metrics to zero
     */
    public void reset() {
        totalSuppliersCreated.set(0);
        totalSuppliersUpdated.set(0);
        totalSuppliersDeleted.set(0);
        logger.debug("📊 METRICS: All supplier metrics reset to zero");
    }
}



