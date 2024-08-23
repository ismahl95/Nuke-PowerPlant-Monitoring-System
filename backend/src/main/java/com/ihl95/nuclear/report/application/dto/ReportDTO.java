package com.ihl95.nuclear.report.application.dto;

import java.time.LocalDateTime;

import com.ihl95.nuclear.report.domain.enums.ReportStatus;

public record ReportDTO(
    Long id,
    String title,
    String content,
    ReportStatus status,
    LocalDateTime creationDate
) {}
