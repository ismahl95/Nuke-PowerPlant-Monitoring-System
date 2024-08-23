package com.ihl95.nuclear.anomaly.application.dto;

import java.time.LocalDateTime;

import com.ihl95.nuclear.anomaly.domain.enums.AnomalySeverity;

public record AnomalyDTO(
    Long id,
    String description,
    LocalDateTime date,
    AnomalySeverity severity
) {}
