package com.ihl95.nuclear.maintenancePlan.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.maintenancePlan.application.dto.MaintenancePlanDTO;
import com.ihl95.nuclear.maintenancePlan.domain.MaintenancePlan;

@Mapper(componentModel = "spring")
public interface MaintenancePlanMapper {
    MaintenancePlanMapper INSTANCE = Mappers.getMapper(MaintenancePlanMapper.class);

    @Mapping(source = "completed", target = "isCompleted")
    MaintenancePlanDTO toMaintenancePlanDTO(MaintenancePlan maintenancePlan);

    @Mapping(source = "isCompleted", target = "completed")
    MaintenancePlan toMaintenancePlan(MaintenancePlanDTO maintenancePlanDTO);
}

