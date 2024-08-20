package com.ihl95.nuclear.maintenancePlan.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ihl95.nuclear.maintenance.application.dto.MaintenanceDTO;

public record MaintenancePlanDTO(
    Long id,
    String name,
    String description,
    LocalDateTime startDate,
    LocalDateTime endDate,
    boolean isCompleted,
    List<MaintenanceDTO> maintenances
) {}
