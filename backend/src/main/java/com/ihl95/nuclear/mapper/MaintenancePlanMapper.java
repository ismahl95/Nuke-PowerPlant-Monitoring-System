package com.ihl95.nuclear.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.dto.MaintenancePlanDTO;
import com.ihl95.nuclear.model.MaintenancePlan;

@Mapper
public interface MaintenancePlanMapper {
    MaintenancePlanMapper INSTANCE = Mappers.getMapper(MaintenancePlanMapper.class);

    @Mapping(source = "nuclearPlant", target = "nuclearPlant")
    MaintenancePlanDTO toMaintenancePlanDTO(MaintenancePlan maintenancePlan);

    @Mapping(source = "nuclearPlant", target = "nuclearPlant")
    MaintenancePlan toMaintenancePlan(MaintenancePlanDTO maintenancePlanDTO);

    List<MaintenancePlanDTO> maintenancePlanListToMaintenancePlanDTOList(List<MaintenancePlan> maintenancePlans);
    List<MaintenancePlan> maintenancePlanDTOListToMaintenancePlanList(List<MaintenancePlanDTO> maintenancePlanDTOs);
}

