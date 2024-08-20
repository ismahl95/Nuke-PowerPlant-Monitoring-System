package com.ihl95.nuclear.incident.application.dto;

import java.time.LocalDateTime;

import com.ihl95.nuclear.incident.domain.enums.IncidentSeverity;

public record IncidentDTO(
    Long id,
    String description,
    LocalDateTime date,
    IncidentSeverity severity
) {}
