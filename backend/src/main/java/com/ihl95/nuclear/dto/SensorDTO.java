package com.ihl95.nuclear.dto;

import java.util.List;

import com.ihl95.nuclear.enums.SensorType;

public record SensorDTO(
    Long id,
    SensorType type,
    String location,
    ReactorDTO reactor,
    List<SensorReadingDTO> sensorReadings
) {}
