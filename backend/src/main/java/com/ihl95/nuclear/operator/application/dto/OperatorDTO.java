package com.ihl95.nuclear.operator.application.dto;

import java.util.List;

public record OperatorDTO(
    Long id,
    String name,
    String position,
    String experience
    //List<TrainingDTO> trainings
) {}
