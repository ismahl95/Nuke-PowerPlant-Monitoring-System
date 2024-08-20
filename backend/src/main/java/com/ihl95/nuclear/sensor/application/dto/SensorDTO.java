package com.ihl95.nuclear.sensor.application.dto;

import java.util.List;

import com.ihl95.nuclear.reactor.application.dto.ReactorDTO;
import com.ihl95.nuclear.sensor.domain.enums.SensorType;
import com.ihl95.nuclear.sensorReading.application.dto.SensorReadingDTO;

public record SensorDTO(
    Long id,
    SensorType type,
    String location,
    ReactorDTO reactor,
    List<SensorReadingDTO> sensorReadings
) {}
