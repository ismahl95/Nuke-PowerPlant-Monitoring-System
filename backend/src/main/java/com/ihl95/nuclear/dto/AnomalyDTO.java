package com.ihl95.nuclear.dto;

import java.time.LocalDateTime;

import com.ihl95.nuclear.enums.AnomalySeverity;

public record AnomalyDTO(
    Long id,
    String description,
    LocalDateTime date,
    AnomalySeverity severity
) {}
