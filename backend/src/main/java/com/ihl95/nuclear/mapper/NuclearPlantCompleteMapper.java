package com.ihl95.nuclear.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ihl95.nuclear.dto.EmergencyPlanDTO;
import com.ihl95.nuclear.dto.IncidentDTO;
import com.ihl95.nuclear.dto.MaintenancePlanDTO;
import com.ihl95.nuclear.dto.NuclearPlantCompleteDTO;
import com.ihl95.nuclear.dto.OperatorDTO;
import com.ihl95.nuclear.dto.ReactorDTO;
import com.ihl95.nuclear.model.EmergencyPlan;
import com.ihl95.nuclear.model.Incident;
import com.ihl95.nuclear.model.MaintenancePlan;
import com.ihl95.nuclear.model.NuclearPlant;
import com.ihl95.nuclear.model.Operator;
import com.ihl95.nuclear.model.Reactor;

@Mapper(componentModel = "spring")
public interface NuclearPlantCompleteMapper {

    @Mapping(target = "reactors", source = "reactors")
    @Mapping(target = "maintenancePlans", source = "maintenancePlans")
    @Mapping(target = "emergencyPlans", source = "emergencyPlans")
    @Mapping(target = "incidents", source = "incidents")
    @Mapping(target = "operators", source = "operators")
    NuclearPlantCompleteDTO toNuclearPlantCompleteDTO(NuclearPlant nuclearPlant);

}
