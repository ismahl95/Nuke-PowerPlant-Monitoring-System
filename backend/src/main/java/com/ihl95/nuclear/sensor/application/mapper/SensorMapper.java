package com.ihl95.nuclear.sensor.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.reactor.application.mapper.ReactorMapper;
import com.ihl95.nuclear.sensor.application.dto.SensorDTO;
import com.ihl95.nuclear.sensor.domain.Sensor;
import com.ihl95.nuclear.sensorReading.application.mapper.SensorReadingMapper;

@Mapper(uses = {ReactorMapper.class, SensorReadingMapper.class})
public interface SensorMapper {
    SensorMapper INSTANCE = Mappers.getMapper(SensorMapper.class);

    @Mapping(source = "reactor", target = "reactor")
    SensorDTO toSensorDTO(Sensor sensor);

    @Mapping(source = "reactor", target = "reactor")
    Sensor toSensor(SensorDTO sensorDTO);
}
