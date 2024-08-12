package com.ihl95.nuclear.dto;

import java.util.List;

public record OperatorDTO(
    Long id,
    String name,
    String position,
    String experience,
    Long nuclearPlantId,
    List<TrainingDTO> trainings
) {}
