package com.ihl95.nuclear.training.application.dto;

import java.time.LocalDateTime;

import com.ihl95.nuclear.operator.application.dto.OperatorDTO;
import com.ihl95.nuclear.training.domain.enums.TrainingType;

public record TrainingDTO(
    Long id,
    String title,
    String description,
    TrainingType type,
    LocalDateTime trainingDate,
    OperatorDTO operator
) {}
