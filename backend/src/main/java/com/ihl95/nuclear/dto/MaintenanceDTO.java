package com.ihl95.nuclear.dto;

import java.time.LocalDateTime;

import com.ihl95.nuclear.enums.MaintenanceStatus;

public record MaintenanceDTO(
    Long id,
    String description,
    LocalDateTime startDate,
    LocalDateTime endDate,
    MaintenanceStatus status,
    Long reactorId,  // Solo el ID, no el objeto completo
    Long equipmentId, // Solo el ID, no el objeto completo
    Long maintenancePlanId // Solo el ID, no el objeto completo
) {}
