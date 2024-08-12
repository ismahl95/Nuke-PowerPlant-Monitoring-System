package com.ihl95.nuclear.dto;

import java.time.LocalDateTime;

public record EmergencyPlanDTO(
    Long id,
    String name,
    String description,
    LocalDateTime creationDate,
    Long nuclearPlantId
) {}
