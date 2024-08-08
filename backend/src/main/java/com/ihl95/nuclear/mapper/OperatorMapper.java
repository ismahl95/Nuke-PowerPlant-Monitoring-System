package com.ihl95.nuclear.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.dto.OperatorDTO;
import com.ihl95.nuclear.model.Operator;

@Mapper(componentModel = "spring")
public interface OperatorMapper {

    OperatorMapper INSTANCE = Mappers.getMapper(OperatorMapper.class);

    @Mapping(source = "trainings", target = "trainings")
    @Mapping(source = "nuclearPlant", target = "nuclearPlant")
    OperatorDTO toOperatorDTO(Operator operator);

    @Mapping(source = "trainings", target = "trainings")
    @Mapping(source = "nuclearPlant", target = "nuclearPlant")
    Operator toOperator(OperatorDTO operatorDTO);
}

