package com.ihl95.nuclear.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.ihl95.nuclear.dto.IncidentDTO;
import com.ihl95.nuclear.model.Incident;

/* @Mapper(componentModel = "spring") */
public interface IncidentMapper {

/*     @Mapping(source = "nuclearPlant.id", target = "nuclearPlantId") // Mapea el ID de nuclearPlant
    IncidentDTO toIncidentDTO(Incident incident);

    @Mapping(source = "nuclearPlantId", target = "nuclearPlant.id") // Mapea el ID de nuclearPlant en lugar del objeto completo
    Incident toIncident(IncidentDTO incidentDTO); */
}

