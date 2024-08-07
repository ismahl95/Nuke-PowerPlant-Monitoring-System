package com.ihl95.nuclear.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.dto.AnomalyDTO;
import com.ihl95.nuclear.model.Anomaly;

@Mapper(uses = ReactorMapper.class)
public interface AnomalyMapper {
    AnomalyMapper INSTANCE = Mappers.getMapper(AnomalyMapper.class);

    @Mapping(source = "reactor", target = "reactor")
    AnomalyDTO toAnomalyDTO(Anomaly anomaly);

    @Mapping(source = "reactor", target = "reactor")
    Anomaly toAnomaly(AnomalyDTO anomalyDTO);
}
