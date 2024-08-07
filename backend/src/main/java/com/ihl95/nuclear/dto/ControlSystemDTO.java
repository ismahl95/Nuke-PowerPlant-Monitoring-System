package com.ihl95.nuclear.dto;

import com.ihl95.nuclear.enums.ControlSystemType;

public record ControlSystemDTO(
    Long id,
    String name,
    ControlSystemType type,
    ReactorDTO reactor
) {}
