package com.ihl95.nuclear.reactor.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ihl95.nuclear.anomaly.application.dto.AnomalyDTO;
import com.ihl95.nuclear.maintenance.application.dto.MaintenanceDTO;
import com.ihl95.nuclear.reactor.domain.enums.ReactorStatus;
import com.ihl95.nuclear.reactor.domain.enums.ReactorType;

public record ReactorDTO(
    Long id,
    String name,
    ReactorType type,
    LocalDateTime installationDate,
    ReactorStatus status,
    List<AnomalyDTO> anomalies,
    List<MaintenanceDTO> maintenances
) {}
