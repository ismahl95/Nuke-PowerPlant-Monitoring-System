package com.ihl95.nuclear.reactor.application.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.ihl95.nuclear.reactor.application.dto.ReactorDTO;
import com.ihl95.nuclear.reactor.domain.Reactor;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReactorMapper {

    @Mapping(target = "nuclearPlant", ignore = true)
    Reactor toReactor(ReactorDTO reactorDTO);

    ReactorDTO toReactorDTO(Reactor reactor);

    List<ReactorDTO> toReactorDTOList(List<Reactor> reactors);
    List<Reactor> toReactorList(List<ReactorDTO> reactorDTOs);

    @Mapping(target = "nuclearPlant", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateReactorFromDto(ReactorDTO dto, @MappingTarget Reactor entity);
}

