package com.ihl95.nuclear.dto;

import java.time.LocalDate;
import java.util.List;

import com.ihl95.nuclear.enums.EquipmentType;

public record EquipmentDTO(
    Long id,
    String name,
    String manufacturer,
    LocalDate installationDate,
    EquipmentType type,
    List<MaintenanceDTO> maintenances
) {}
