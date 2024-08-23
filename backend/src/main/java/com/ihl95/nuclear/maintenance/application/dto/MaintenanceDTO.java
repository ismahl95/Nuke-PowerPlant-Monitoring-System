package com.ihl95.nuclear.maintenance.application.dto;

import java.time.LocalDateTime;

import com.ihl95.nuclear.maintenance.domain.enums.MaintenanceStatus;

public record MaintenanceDTO(
    Long id,
    String description,
    LocalDateTime startDate,
    LocalDateTime endDate,
    MaintenanceStatus status
) {}
