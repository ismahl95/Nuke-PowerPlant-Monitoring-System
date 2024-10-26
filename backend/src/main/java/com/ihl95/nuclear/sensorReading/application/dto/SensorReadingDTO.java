package com.ihl95.nuclear.sensorreading.application.dto;

import java.time.LocalDateTime;

import com.ihl95.nuclear.sensor.application.dto.SensorDTO;

public record SensorReadingDTO(
    Long id,
    double measurementValue,
    LocalDateTime date,
    SensorDTO sensor
) {}
