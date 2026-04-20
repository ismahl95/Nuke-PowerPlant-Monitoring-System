package com.ihl95.nuclear.incident.application.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.ihl95.nuclear.incident.domain.enums.IncidentSeverity;

public record IncidentDTO(
    Long id,

    @NotBlank(message = "Incident description is required")
    String description,

    @NotNull(message = "Incident date is required")
    LocalDateTime date,

    @NotNull(message = "Incident severity is required")
    IncidentSeverity severity
) {}
