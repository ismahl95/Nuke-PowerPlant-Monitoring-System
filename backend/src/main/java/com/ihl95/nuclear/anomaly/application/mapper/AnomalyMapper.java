package com.ihl95.nuclear.anomaly.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.anomaly.application.dto.AnomalyDTO;
import com.ihl95.nuclear.anomaly.domain.Anomaly;

@Mapper(componentModel = "spring")
public interface AnomalyMapper {
    AnomalyMapper INSTANCE = Mappers.getMapper(AnomalyMapper.class);

    AnomalyDTO toAnomalyDTO(Anomaly anomaly);

    Anomaly toAnomaly(AnomalyDTO anomalyDTO);
}
