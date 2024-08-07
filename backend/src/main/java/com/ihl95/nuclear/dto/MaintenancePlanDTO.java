package com.ihl95.nuclear.dto;

import java.time.LocalDateTime;
import java.util.List;

public record MaintenancePlanDTO(
    Long id,
    String name,
    String description,
    LocalDateTime startDate,
    LocalDateTime endDate,
    NuclearPlantDTO nuclearPlant,
    boolean isCompleted,
    List<MaintenanceDTO> maintenances
) {}
