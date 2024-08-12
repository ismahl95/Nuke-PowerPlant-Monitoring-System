package com.ihl95.nuclear.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ihl95.nuclear.enums.ReactorStatus;
import com.ihl95.nuclear.enums.ReactorType;

public record ReactorDTO(
    Long id,
    String name,
    ReactorType type,
    LocalDateTime installationDate,
    ReactorStatus status,
    Long nuclearPlantId,
    List<AnomalyDTO> anomalies,
    List<MaintenanceDTO> maintenances
) {}
