package com.ihl95.nuclear.sensorReading.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.sensor.application.mapper.SensorMapper;
import com.ihl95.nuclear.sensorReading.application.dto.SensorReadingDTO;
import com.ihl95.nuclear.sensorReading.domain.SensorReading;

@Mapper(uses = SensorMapper.class)
public interface SensorReadingMapper {
    SensorReadingMapper INSTANCE = Mappers.getMapper(SensorReadingMapper.class);

    @Mapping(source = "sensor", target = "sensor")
    SensorReadingDTO toSensorReadingDTO(SensorReading sensorReading);

    @Mapping(source = "sensor", target = "sensor")
    SensorReading toSensorReading(SensorReadingDTO sensorReadingDTO);
}
