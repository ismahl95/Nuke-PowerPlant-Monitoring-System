package com.ihl95.nuclear.training.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.operator.application.mapper.OperatorMapper;
import com.ihl95.nuclear.training.application.dto.TrainingDTO;
import com.ihl95.nuclear.training.domain.Training;

@Mapper(uses = OperatorMapper.class)
public interface TrainingMapper {
    TrainingMapper INSTANCE = Mappers.getMapper(TrainingMapper.class);

    @Mapping(source = "operator", target = "operator")
    TrainingDTO toTrainingDTO(Training training);

    @Mapping(source = "operator", target = "operator")
    Training toTraining(TrainingDTO trainingDTO);
}
