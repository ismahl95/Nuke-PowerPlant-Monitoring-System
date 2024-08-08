package com.ihl95.nuclear.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ihl95.nuclear.dto.NuclearPlantDTO;
import com.ihl95.nuclear.model.EmergencyPlan;
import com.ihl95.nuclear.model.Incident;
import com.ihl95.nuclear.model.MaintenancePlan;
import com.ihl95.nuclear.model.NuclearPlant;
import com.ihl95.nuclear.model.Operator;
import com.ihl95.nuclear.model.Reactor;
import com.ihl95.nuclear.service.EmergencyPlanService;
import com.ihl95.nuclear.service.IncidentService;
import com.ihl95.nuclear.service.MaintenancePlanService;
import com.ihl95.nuclear.service.OperatorService;
import com.ihl95.nuclear.service.ReactorService;

@Mapper(componentModel = "spring")
@Component
public abstract class NuclearPlantMapper {

    @Autowired
    private ReactorService reactorService;

    @Autowired
    private MaintenancePlanService maintenancePlanService;

    @Autowired
    private EmergencyPlanService emergencyPlanService;

    @Autowired
    private IncidentService incidentService;

    @Autowired
    private OperatorService operatorService;

    @Mapping(target = "reactorIds", source = "reactors", qualifiedByName = "toReactorIds")
    @Mapping(target = "maintenancePlanIds", source = "maintenancePlans", qualifiedByName = "toMaintenancePlanIds")
    @Mapping(target = "emergencyPlanIds", source = "emergencyPlans", qualifiedByName = "toEmergencyPlanIds")
    @Mapping(target = "incidentIds", source = "incidents", qualifiedByName = "toIncidentIds")
    @Mapping(target = "operatorIds", source = "operators", qualifiedByName = "toOperatorIds")
    public abstract NuclearPlantDTO toNuclearPlantDTO(NuclearPlant nuclearPlant);

    @Named("toReactorIds")
    public List<Long> toReactorIds(List<Reactor> reactors) {
        return reactors.stream().map(Reactor::getId).collect(Collectors.toList());
    }

    @Named("toMaintenancePlanIds")
    public List<Long> toMaintenancePlanIds(List<MaintenancePlan> maintenancePlans) {
        return maintenancePlans.stream().map(MaintenancePlan::getId).collect(Collectors.toList());
    }

    @Named("toEmergencyPlanIds")
    public List<Long> toEmergencyPlanIds(List<EmergencyPlan> emergencyPlans) {
        return emergencyPlans.stream().map(EmergencyPlan::getId).collect(Collectors.toList());
    }

    @Named("toIncidentIds")
    public List<Long> toIncidentIds(List<Incident> incidents) {
        return incidents.stream().map(Incident::getId).collect(Collectors.toList());
    }

    @Named("toOperatorIds")
    public List<Long> toOperatorIds(List<Operator> operators) {
        return operators.stream().map(Operator::getId).collect(Collectors.toList());
    }

    @Mapping(target = "reactors", source = "reactorIds", qualifiedByName = "toReactors")
    @Mapping(target = "maintenancePlans", source = "maintenancePlanIds", qualifiedByName = "toMaintenancePlans")
    @Mapping(target = "emergencyPlans", source = "emergencyPlanIds", qualifiedByName = "toEmergencyPlans")
    @Mapping(target = "incidents", source = "incidentIds", qualifiedByName = "toIncidents")
    @Mapping(target = "operators", source = "operatorIds", qualifiedByName = "toOperators")
    public NuclearPlant toNuclearPlant(NuclearPlantDTO nuclearPlantDTO) {
        List<Reactor> reactors = nuclearPlantDTO.reactorIds().stream()
                .map(reactorService::getReactorById)
                .collect(Collectors.toList());

        List<MaintenancePlan> maintenancePlans = nuclearPlantDTO.maintenancePlanIds().stream()
                .map(maintenancePlanService::getMaintenancePlanById)
                .collect(Collectors.toList());

        List<EmergencyPlan> emergencyPlans = nuclearPlantDTO.emergencyPlanIds().stream()
                .map(emergencyPlanService::getEmergencyPlanById)
                .collect(Collectors.toList());

        List<Incident> incidents = nuclearPlantDTO.incidentIds().stream()
                .map(incidentService::getIncidentById)
                .collect(Collectors.toList());

        List<Operator> operators = nuclearPlantDTO.operatorIds().stream()
                .map(operatorService::getOperatorById)
                .collect(Collectors.toList());

        return NuclearPlant.builder()
                .id(nuclearPlantDTO.id())
                .name(nuclearPlantDTO.name())
                .location(nuclearPlantDTO.location())
                .reactors(reactors)
                .maintenancePlans(maintenancePlans)
                .emergencyPlans(emergencyPlans)
                .incidents(incidents)
                .operators(operators)
                .build();
    }
}


