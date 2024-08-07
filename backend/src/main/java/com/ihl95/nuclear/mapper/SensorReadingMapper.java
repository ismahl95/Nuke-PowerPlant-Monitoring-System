package com.ihl95.nuclear.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.dto.SensorReadingDTO;
import com.ihl95.nuclear.model.SensorReading;

@Mapper(uses = SensorMapper.class)
public interface SensorReadingMapper {
    SensorReadingMapper INSTANCE = Mappers.getMapper(SensorReadingMapper.class);

    @Mapping(source = "sensor", target = "sensor")
    SensorReadingDTO toSensorReadingDTO(SensorReading sensorReading);

    @Mapping(source = "sensor", target = "sensor")
    SensorReading toSensorReading(SensorReadingDTO sensorReadingDTO);
}
