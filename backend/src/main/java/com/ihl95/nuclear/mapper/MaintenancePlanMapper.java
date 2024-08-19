package com.ihl95.nuclear.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.dto.MaintenancePlanDTO;
import com.ihl95.nuclear.model.MaintenancePlan;

@Mapper(componentModel = "spring", uses = {MaintenanceMapper.class})
public interface MaintenancePlanMapper {
    MaintenancePlanMapper INSTANCE = Mappers.getMapper(MaintenancePlanMapper.class);

    @Mapping(source = "nuclearPlant.id", target = "nuclearPlantId")
    @Mapping(source = "maintenances", target = "maintenances")
    @Mapping(source = "completed", target = "isCompleted")
    MaintenancePlanDTO toMaintenancePlanDTO(MaintenancePlan maintenancePlan);

    @Mapping(source = "nuclearPlantId", target = "nuclearPlant.id")
    @Mapping(source = "maintenances", target = "maintenances")
    @Mapping(source = "isCompleted", target = "completed")
    MaintenancePlan toMaintenancePlan(MaintenancePlanDTO maintenancePlanDTO);
}

