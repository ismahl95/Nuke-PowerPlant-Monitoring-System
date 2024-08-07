package com.ihl95.nuclear.dto;

import java.time.LocalDateTime;

import com.ihl95.nuclear.enums.TrainingType;

public record TrainingDTO(
    Long id,
    String title,
    String description,
    TrainingType type,
    LocalDateTime trainingDate,
    OperatorDTO operator
) {}
