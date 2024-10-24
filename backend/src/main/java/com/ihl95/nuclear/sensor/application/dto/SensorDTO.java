package com.ihl95.nuclear.sensor.application.dto;

import com.ihl95.nuclear.reactor.application.dto.ReactorDTO;
import com.ihl95.nuclear.sensor.domain.enums.SensorType;

public record SensorDTO(
    Long id,
    SensorType type,
    String location,
    ReactorDTO reactor
) {}
