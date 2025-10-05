package com.ihl95.nuclear.nuclearplant.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearplant.domain.NuclearPlant;

@Mapper(componentModel = "spring")
public interface NuclearPlantCompleteMapper {

    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    NuclearPlant toNuclearPlant(NuclearPlantDTO nuclearPlantDTO);

    NuclearPlantDTO toNuclearPlantDTO(NuclearPlant nuclearPlant);
}
