package com.ihl95.nuclear.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.dto.OperatorDTO;
import com.ihl95.nuclear.model.Operator;

@Mapper(componentModel = "spring")
public interface OperatorMapper {

    OperatorMapper INSTANCE = Mappers.getMapper(OperatorMapper.class);

    OperatorDTO toOperatorDTO(Operator operator);

    Operator toOperator(OperatorDTO operatorDTO);
}

