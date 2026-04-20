package com.ihl95.nuclear.maintenance.application.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.ihl95.nuclear.maintenance.domain.enums.MaintenanceStatus;

public record MaintenanceDTO(
    Long id,

    @NotBlank(message = "Maintenance description is required")
    String description,

    @NotNull(message = "Start date is required")
    LocalDateTime startDate,

    LocalDateTime endDate,

    @NotNull(message = "Maintenance status is required")
    MaintenanceStatus status
) {}
