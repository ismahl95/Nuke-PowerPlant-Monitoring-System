package com.ihl95.nuclear.reactor.application.mapper;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.reactor.application.dto.ReactorDTO;
import com.ihl95.nuclear.reactor.domain.Reactor;
@Mapper(componentModel = "spring")
public interface ReactorMapper {
    ReactorMapper INSTANCE = Mappers.getMapper(ReactorMapper.class);

    List<ReactorDTO> reactorListToReactorDTOList(List<Reactor> reactors);
    List<Reactor> reactorDTOListToReactorList(List<ReactorDTO> reactorDTOs);
}

