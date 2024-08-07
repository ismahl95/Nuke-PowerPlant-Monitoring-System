package com.ihl95.nuclear.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.dto.IncidentDTO;
import com.ihl95.nuclear.model.Incident;

@Mapper
public interface IncidentMapper {

    IncidentMapper INSTANCE = Mappers.getMapper(IncidentMapper.class);

    @Mapping(source = "nuclearPlant", target = "nuclearPlant")
    IncidentDTO toIncidentDTO(Incident incident);

    @Mapping(source = "nuclearPlant", target = "nuclearPlant")
    Incident toIncident(IncidentDTO incidentDTO);
}

