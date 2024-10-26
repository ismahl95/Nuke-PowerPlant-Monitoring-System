package com.ihl95.nuclear.maintenanceplan.application.dto;

import java.time.LocalDateTime;

public record MaintenancePlanDTO(
    Long id,
    String name,
    String description,
    LocalDateTime startDate,
    LocalDateTime endDate,
    boolean isCompleted
) {}
