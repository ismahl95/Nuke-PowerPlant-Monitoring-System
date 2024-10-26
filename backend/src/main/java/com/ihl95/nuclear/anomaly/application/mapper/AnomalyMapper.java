package com.ihl95.nuclear.anomaly.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.anomaly.application.dto.AnomalyDTO;
import com.ihl95.nuclear.anomaly.domain.Anomaly;

@Mapper(componentModel = "spring")
public interface AnomalyMapper {
    AnomalyMapper INSTANCE = Mappers.getMapper(AnomalyMapper.class);

    /* @Mapping(source = "reactor", target = "reactor") QUITARLO DEFINITIVAMENTE */
    AnomalyDTO toAnomalyDTO(Anomaly anomaly);

    /* @Mapping(source = "reactor", target = "reactor")
    Anomaly toAnomaly(AnomalyDTO anomalyDTO); */
}
