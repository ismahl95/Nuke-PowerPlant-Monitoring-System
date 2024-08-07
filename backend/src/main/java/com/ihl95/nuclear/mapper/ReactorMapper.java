package com.ihl95.nuclear.mapper;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.dto.ReactorDTO;
import com.ihl95.nuclear.model.Reactor;
@Mapper
public interface ReactorMapper {
    ReactorMapper INSTANCE = Mappers.getMapper(ReactorMapper.class);

    @Mapping(source = "nuclearPlant", target = "nuclearPlant")
    ReactorDTO toReactorDTO(Reactor reactor);

    @Mapping(source = "nuclearPlant", target = "nuclearPlant")
    Reactor toReactor(ReactorDTO reactorDTO);

    List<ReactorDTO> reactorListToReactorDTOList(List<Reactor> reactors);
    List<Reactor> reactorDTOListToReactorList(List<ReactorDTO> reactorDTOs);
}

