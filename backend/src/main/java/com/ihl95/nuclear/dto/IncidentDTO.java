package com.ihl95.nuclear.dto;

import java.time.LocalDateTime;

import com.ihl95.nuclear.enums.IncidentSeverity;

public record IncidentDTO(
    Long id,
    String description,
    LocalDateTime date,
    IncidentSeverity severity,
    NuclearPlantDTO nuclearPlant
) {}
