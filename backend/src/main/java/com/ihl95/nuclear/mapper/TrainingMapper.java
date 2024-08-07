package com.ihl95.nuclear.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.dto.TrainingDTO;
import com.ihl95.nuclear.model.Training;

@Mapper(uses = OperatorMapper.class)
public interface TrainingMapper {
    TrainingMapper INSTANCE = Mappers.getMapper(TrainingMapper.class);

    @Mapping(source = "operator", target = "operator")
    TrainingDTO toTrainingDTO(Training training);

    @Mapping(source = "operator", target = "operator")
    Training toTraining(TrainingDTO trainingDTO);
}
