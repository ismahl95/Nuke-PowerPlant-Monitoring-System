package com.ihl95.nuclear.nuclearPlant.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ihl95.nuclear.emergencyPlan.application.dto.EmergencyPlanDTO;
import com.ihl95.nuclear.emergencyPlan.domain.EmergencyPlan;
import com.ihl95.nuclear.incident.application.dto.IncidentDTO;
import com.ihl95.nuclear.incident.domain.Incident;
import com.ihl95.nuclear.maintenancePlan.application.dto.MaintenancePlanDTO;
import com.ihl95.nuclear.maintenancePlan.domain.MaintenancePlan;
import com.ihl95.nuclear.nuclearPlant.application.dto.NuclearPlantCompleteDTO;
import com.ihl95.nuclear.nuclearPlant.domain.NuclearPlant;
import com.ihl95.nuclear.operator.application.dto.OperatorDTO;
import com.ihl95.nuclear.operator.domain.Operator;
import com.ihl95.nuclear.reactor.application.dto.ReactorDTO;
import com.ihl95.nuclear.reactor.domain.Reactor;

@Mapper(componentModel = "spring")
public interface NuclearPlantCompleteMapper {

    @Mapping(target = "reactors", source = "reactors")
    @Mapping(target = "maintenancePlans", source = "maintenancePlans")
    @Mapping(target = "emergencyPlans", source = "emergencyPlans")
    @Mapping(target = "incidents", source = "incidents")
    @Mapping(target = "operators", source = "operators")
    NuclearPlantCompleteDTO toNuclearPlantCompleteDTO(NuclearPlant nuclearPlant);

}
