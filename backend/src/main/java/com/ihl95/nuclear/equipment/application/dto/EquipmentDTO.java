package com.ihl95.nuclear.equipment.application.dto;

import java.time.LocalDate;
import java.util.List;

import com.ihl95.nuclear.equipment.domain.enums.EquipmentType;
import com.ihl95.nuclear.maintenance.application.dto.MaintenanceDTO;

public record EquipmentDTO(
    Long id,
    String name,
    String manufacturer,
    LocalDate installationDate,
    EquipmentType type,
    List<MaintenanceDTO> maintenances
) {}
