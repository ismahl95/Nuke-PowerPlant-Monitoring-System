package com.ihl95.nuclear.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ihl95.nuclear.dto.EmergencyPlanDTO;
import com.ihl95.nuclear.dto.IncidentDTO;
import com.ihl95.nuclear.dto.MaintenancePlanDTO;
import com.ihl95.nuclear.dto.NuclearPlantCompleteDTO;
import com.ihl95.nuclear.dto.NuclearPlantDTO;
import com.ihl95.nuclear.dto.OperatorDTO;
import com.ihl95.nuclear.dto.ReactorDTO;
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

/* @Mapper(componentModel = "spring") */
public abstract class NuclearPlantMapper {

/*     @Autowired
    private ReactorService reactorService;

    @Autowired
    private MaintenancePlanService maintenancePlanService;

    @Autowired
    private EmergencyPlanService emergencyPlanService;

    @Autowired
    private IncidentService incidentService;

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private ReactorMapper reactorMapper;

    @Autowired
    private MaintenancePlanMapper maintenancePlanMapper;

    @Autowired
    private EmergencyPlanMapper emergencyPlanMapper;

    @Autowired
    private IncidentMapper incidentMapper;

    @Autowired
    private OperatorMapper operatorMapper;

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

    @Mapping(target = "reactors", source = "reactors")
    @Mapping(target = "maintenancePlans", source = "maintenancePlans")
    @Mapping(target = "emergencyPlans", source = "emergencyPlans")
    @Mapping(target = "incidents", source = "incidents")
    @Mapping(target = "operators", source = "operators")
    public abstract NuclearPlantCompleteDTO toNuclearPlantCompleteDTO(NuclearPlant nuclearPlant);

    // Métodos de conversión de listas de entidades a listas de DTOs
    public List<ReactorDTO> toReactorDTOs(List<Reactor> reactors) {
        return reactors.stream()
                .map(reactorMapper::toReactorDTO)
                .collect(Collectors.toList());
    }

    public List<MaintenancePlanDTO> toMaintenancePlanDTOs(List<MaintenancePlan> maintenancePlans) {
        return maintenancePlans.stream()
                .map(maintenancePlanMapper::toMaintenancePlanDTO)
                .collect(Collectors.toList());
    }

    public List<EmergencyPlanDTO> toEmergencyPlanDTOs(List<EmergencyPlan> emergencyPlans) {
        return emergencyPlans.stream()
                .map(emergencyPlanMapper::toEmergencyPlanDTO)
                .collect(Collectors.toList());
    }

    public List<IncidentDTO> toIncidentDTOs(List<Incident> incidents) {
        return incidents.stream()
                .map(incidentMapper::toIncidentDTO)
                .collect(Collectors.toList());
    }

    public List<OperatorDTO> toOperatorDTOs(List<Operator> operators) {
        return operators.stream()
                .map(operatorMapper::toOperatorDTO)
                .collect(Collectors.toList());
    }

    // Métodos que llaman a los servicios para obtener las entidades a partir de los IDs
    @Mapping(target = "reactors", source = "reactors")
    @Mapping(target = "maintenancePlans", source = "maintenancePlans")
    @Mapping(target = "emergencyPlans", source = "emergencyPlans")
    @Mapping(target = "incidents", source = "incidents")
    @Mapping(target = "operators", source = "operators")
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
    } */
}



