package com.ihl95.nuclear.equipment.application.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.ihl95.nuclear.equipment.domain.enums.EquipmentType;

public record EquipmentDTO(
    Long id,

    @NotBlank(message = "Equipment name is required")
    String name,

    @NotBlank(message = "Manufacturer is required")
    String manufacturer,

    LocalDate installationDate,

    @NotNull(message = "Equipment type is required")
    EquipmentType type
) {}
