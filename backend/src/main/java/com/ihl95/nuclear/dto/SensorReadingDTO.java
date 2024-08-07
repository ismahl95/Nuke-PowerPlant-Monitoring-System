package com.ihl95.nuclear.dto;

import java.time.LocalDateTime;

public record SensorReadingDTO(
    Long id,
    double measurementValue,
    LocalDateTime date,
    SensorDTO sensor
) {}
