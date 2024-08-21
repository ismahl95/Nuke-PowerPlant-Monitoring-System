package com.ihl95.nuclear.nuclearPlant.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ihl95.nuclear.nuclearPlant.application.dto.NuclearPlantCompleteDTO;
import com.ihl95.nuclear.nuclearPlant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearPlant.domain.NuclearPlant;

@Mapper(componentModel = "spring")
public interface NuclearPlantCompleteMapper {

    @Mapping(target = "reactors", source = "reactors")
    @Mapping(target = "maintenancePlans", source = "maintenancePlans")
    @Mapping(target = "emergencyPlans", source = "emergencyPlans")
    @Mapping(target = "incidents", source = "incidents")
    @Mapping(target = "operators", source = "operators")
    NuclearPlantCompleteDTO toNuclearPlantCompleteDTO(NuclearPlant nuclearPlant);

    NuclearPlantDTO toNuclearPlantDTO(NuclearPlant nuclearPlant);

}
