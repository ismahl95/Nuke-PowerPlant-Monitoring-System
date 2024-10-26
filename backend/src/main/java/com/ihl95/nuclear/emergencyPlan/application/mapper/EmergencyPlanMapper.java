package com.ihl95.nuclear.emergencyplan.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.emergencyplan.application.dto.EmergencyPlanDTO;
import com.ihl95.nuclear.emergencyplan.domain.EmergencyPlan;

@Mapper(componentModel = "spring")
public interface EmergencyPlanMapper {
    EmergencyPlanMapper INSTANCE = Mappers.getMapper(EmergencyPlanMapper.class);

    EmergencyPlanDTO toEmergencyPlanDTO(EmergencyPlan emergencyPlan);

    EmergencyPlan toEmergencyPlan(EmergencyPlanDTO emergencyPlanDTO);
}

