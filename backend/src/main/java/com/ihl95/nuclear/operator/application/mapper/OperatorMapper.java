package com.ihl95.nuclear.operator.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.operator.application.dto.OperatorDTO;
import com.ihl95.nuclear.operator.domain.Operator;

@Mapper(componentModel = "spring")
public interface OperatorMapper {

    OperatorMapper INSTANCE = Mappers.getMapper(OperatorMapper.class);

    OperatorDTO toOperatorDTO(Operator operator);

    Operator toOperator(OperatorDTO operatorDTO);
}

