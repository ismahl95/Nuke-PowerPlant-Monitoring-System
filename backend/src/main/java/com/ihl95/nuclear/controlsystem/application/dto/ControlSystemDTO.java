package com.ihl95.nuclear.controlsystem.application.dto;

import com.ihl95.nuclear.controlsystem.domain.enums.ControlSystemType;
import com.ihl95.nuclear.reactor.application.dto.ReactorDTO;

public record ControlSystemDTO(
    Long id,
    String name,
    ControlSystemType type,
    ReactorDTO reactor
) {}
