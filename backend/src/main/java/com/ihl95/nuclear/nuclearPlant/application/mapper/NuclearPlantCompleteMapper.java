package com.ihl95.nuclear.nuclearplant.application.mapper;

import org.mapstruct.Mapper;

import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearplant.domain.NuclearPlant;

@Mapper(componentModel = "spring")
public interface NuclearPlantCompleteMapper {

    NuclearPlant toNuclearPlant(NuclearPlantDTO nuclearPlantDTO);

    NuclearPlantDTO toNuclearPlantDTO(NuclearPlant nuclearPlant);

}
