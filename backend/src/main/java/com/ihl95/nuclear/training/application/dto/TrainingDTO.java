package com.ihl95.nuclear.training.application.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.ihl95.nuclear.operator.application.dto.OperatorDTO;
import com.ihl95.nuclear.training.domain.enums.TrainingType;

public record TrainingDTO(
    Long id,

    @NotBlank(message = "Training title is required")
    String title,

    @NotBlank(message = "Training description is required")
    String description,

    @NotNull(message = "Training type is required")
    TrainingType type,

    @NotNull(message = "Training date is required")
    LocalDateTime trainingDate,

    @NotNull(message = "Operator is required")
    OperatorDTO operator
) {}
