package com.ihl95.nuclear.controlsystem.application.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.ihl95.nuclear.controlsystem.domain.enums.ControlSystemType;
import com.ihl95.nuclear.reactor.application.dto.ReactorDTO;

public record ControlSystemDTO(
    Long id,

    @NotBlank(message = "Control system name is required")
    String name,

    @NotNull(message = "Control system type is required")
    ControlSystemType type,

    @NotNull(message = "Associated reactor is required")
    ReactorDTO reactor
) {}
