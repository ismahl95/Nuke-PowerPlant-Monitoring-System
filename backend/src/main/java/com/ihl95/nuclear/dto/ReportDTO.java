package com.ihl95.nuclear.dto;

import java.time.LocalDateTime;

import com.ihl95.nuclear.enums.ReportStatus;

public record ReportDTO(
    Long id,
    String title,
    String content,
    ReportStatus status,
    LocalDateTime creationDate
) {}
