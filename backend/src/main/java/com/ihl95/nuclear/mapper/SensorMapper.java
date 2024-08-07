package com.ihl95.nuclear.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.dto.SensorDTO;
import com.ihl95.nuclear.model.Sensor;

@Mapper(uses = {ReactorMapper.class, SensorReadingMapper.class})
public interface SensorMapper {
    SensorMapper INSTANCE = Mappers.getMapper(SensorMapper.class);

    @Mapping(source = "reactor", target = "reactor")
    @Mapping(source = "sensorReadings", target = "sensorReadings")
    SensorDTO toSensorDTO(Sensor sensor);

    @Mapping(source = "reactor", target = "reactor")
    @Mapping(source = "sensorReadings", target = "sensorReadings")
    Sensor toSensor(SensorDTO sensorDTO);
}
