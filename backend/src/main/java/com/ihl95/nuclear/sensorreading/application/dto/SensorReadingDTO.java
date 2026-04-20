package com.ihl95.nuclear.sensorreading.application.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.ihl95.nuclear.sensor.application.dto.SensorDTO;

public record SensorReadingDTO(
    Long id,

    @Positive(message = "Measurement value must be greater than 0")
    double measurementValue,

    @NotNull(message = "Reading date is required")
    LocalDateTime date,

    @NotNull(message = "Associated sensor is required")
    SensorDTO sensor
) {}
