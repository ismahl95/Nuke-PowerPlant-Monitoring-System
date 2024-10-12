package com.ihl95.nuclear.controlSystem.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.controlSystem.application.dto.ControlSystemDTO;
import com.ihl95.nuclear.controlSystem.domain.ControlSystem;

@Mapper(componentModel = "spring")
public interface ControlSystemMapper {
    ControlSystemMapper INSTANCE = Mappers.getMapper(ControlSystemMapper.class);

    @Mapping(source = "reactor", target = "reactor")
    ControlSystemDTO toControlSystemDTO(ControlSystem controlSystem);

    @Mapping(source = "reactor", target = "reactor")
    ControlSystem toControlSystem(ControlSystemDTO controlSystemDTO);
}
