package com.ihl95.nuclear.nuclearPlant.application.dto;

import java.util.List;

public record NuclearPlantDTO(
    Long id,
    String name,
    String location,
    List<Long> reactorIds,
    List<Long> maintenancePlanIds,
    List<Long> emergencyPlanIds,
    List<Long> incidentIds,
    List<Long> operatorIds
) {}
