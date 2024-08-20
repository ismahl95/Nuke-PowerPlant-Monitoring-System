package com.ihl95.nuclear.emergencyPlan.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.emergencyPlan.application.dto.EmergencyPlanDTO;
import com.ihl95.nuclear.emergencyPlan.domain.EmergencyPlan;

@Mapper(componentModel = "spring")
public interface EmergencyPlanMapper {
    EmergencyPlanMapper INSTANCE = Mappers.getMapper(EmergencyPlanMapper.class);

    EmergencyPlanDTO toEmergencyPlanDTO(EmergencyPlan emergencyPlan);

    EmergencyPlan toEmergencyPlan(EmergencyPlanDTO emergencyPlanDTO);
}

