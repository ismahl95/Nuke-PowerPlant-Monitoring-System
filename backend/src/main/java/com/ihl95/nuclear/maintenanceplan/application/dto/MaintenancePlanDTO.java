package com.ihl95.nuclear.maintenanceplan.application.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record MaintenancePlanDTO(
    Long id,

    @NotBlank(message = "Maintenance plan name is required")
    String name,

    @NotBlank(message = "Maintenance plan description is required")
    String description,

    @NotNull(message = "Start date is required")
    LocalDateTime startDate,

    LocalDateTime endDate,

    boolean isCompleted
) {}
