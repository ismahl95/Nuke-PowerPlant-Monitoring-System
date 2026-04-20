package com.ihl95.nuclear.anomaly.application.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.ihl95.nuclear.anomaly.domain.enums.AnomalySeverity;

public record AnomalyDTO(
    Long id,

    @NotBlank(message = "Anomaly description is required")
    String description,

    @NotNull(message = "Anomaly date is required")
    LocalDateTime date,

    @NotNull(message = "Anomaly severity is required")
    AnomalySeverity severity
) {}
