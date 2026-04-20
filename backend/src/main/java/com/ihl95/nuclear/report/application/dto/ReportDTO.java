package com.ihl95.nuclear.report.application.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.ihl95.nuclear.report.domain.enums.ReportStatus;

public record ReportDTO(
    Long id,

    @NotBlank(message = "Report title is required")
    String title,

    @NotBlank(message = "Report content is required")
    String content,

    @NotNull(message = "Report status is required")
    ReportStatus status,

    LocalDateTime creationDate
) {}
