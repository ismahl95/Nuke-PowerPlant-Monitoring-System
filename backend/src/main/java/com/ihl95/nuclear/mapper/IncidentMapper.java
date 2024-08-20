package com.ihl95.nuclear.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.ihl95.nuclear.dto.IncidentDTO;
import com.ihl95.nuclear.model.Incident;

@Mapper(componentModel = "spring")
public interface IncidentMapper {

    IncidentDTO toIncidentDTO(Incident incident);

    Incident toIncident(IncidentDTO incidentDTO);
}

