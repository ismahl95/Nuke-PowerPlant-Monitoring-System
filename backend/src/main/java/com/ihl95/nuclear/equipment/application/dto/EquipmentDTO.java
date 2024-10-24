package com.ihl95.nuclear.equipment.application.dto;

import java.time.LocalDate;
import com.ihl95.nuclear.equipment.domain.enums.EquipmentType;

public record EquipmentDTO(
    Long id,
    String name,
    String manufacturer,
    LocalDate installationDate,
    EquipmentType type
) {}
