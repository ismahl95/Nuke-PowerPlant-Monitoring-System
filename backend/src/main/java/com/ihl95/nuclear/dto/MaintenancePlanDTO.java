package com.ihl95.nuclear.dto;

import java.time.LocalDateTime;
import java.util.List;

public record MaintenancePlanDTO(
    Long id,
    String name,
    String description,
    LocalDateTime startDate,
    LocalDateTime endDate,
    Long nuclearPlantId,
    boolean isCompleted,
    List<MaintenanceDTO> maintenances
) {}
