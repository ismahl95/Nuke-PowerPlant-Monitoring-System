package com.ihl95.nuclear.dto;

import java.time.LocalDateTime;

import com.ihl95.nuclear.enums.MaintenanceStatus;

public record MaintenanceDTO(
    Long id,
    String description,
    LocalDateTime startDate,
    LocalDateTime endDate,
    MaintenanceStatus status,
    ReactorDTO reactor,
    EquipmentDTO equipment,
    MaintenancePlanDTO maintenancePlan
) {}
