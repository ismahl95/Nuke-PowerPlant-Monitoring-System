package com.ihl95.nuclear.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.dto.NuclearPlantDTO;
import com.ihl95.nuclear.model.NuclearPlant;

@Mapper
public interface NuclearPlantMapper {

    NuclearPlantMapper INSTANCE = Mappers.getMapper(NuclearPlantMapper.class);

    @Mapping(source = "reactors", target = "reactors")
    @Mapping(source = "maintenancePlans", target = "maintenancePlans")
    @Mapping(source = "emergencyPlans", target = "emergencyPlans")
    @Mapping(source = "incidents", target = "incidents")
    @Mapping(source = "operators", target = "operators")
    NuclearPlantDTO toNuclearPlantDTO(NuclearPlant nuclearPlant);

    @Mapping(source = "reactors", target = "reactors")
    @Mapping(source = "maintenancePlans", target = "maintenancePlans")
    @Mapping(source = "emergencyPlans", target = "emergencyPlans")
    @Mapping(source = "incidents", target = "incidents")
    @Mapping(source = "operators", target = "operators")
    NuclearPlant toNuclearPlant(NuclearPlantDTO nuclearPlantDTO);
}
