package com.ihl95.nuclear.emergencyplan.application.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;

public record EmergencyPlanDTO(
    Long id,

    @NotBlank(message = "Emergency plan name is required")
    String name,

    @NotBlank(message = "Emergency plan description is required")
    String description,

    LocalDateTime creationDate
) {}
