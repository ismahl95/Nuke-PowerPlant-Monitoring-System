package com.ihl95.nuclear.incident.application.mapper;

import org.mapstruct.Mapper;
import com.ihl95.nuclear.incident.application.dto.IncidentDTO;
import com.ihl95.nuclear.incident.domain.Incident;

@Mapper(componentModel = "spring")
public interface IncidentMapper {

    IncidentDTO toIncidentDTO(Incident incident);

    Incident toIncident(IncidentDTO incidentDTO);
}

