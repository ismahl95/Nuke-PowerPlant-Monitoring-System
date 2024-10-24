package com.ihl95.nuclear.reactor.application.dto;

import java.time.LocalDateTime;
import com.ihl95.nuclear.reactor.domain.enums.ReactorStatus;
import com.ihl95.nuclear.reactor.domain.enums.ReactorType;

public record ReactorDTO(
    Long id,
    String name,
    ReactorType type,
    LocalDateTime installationDate,
    ReactorStatus status
) {}
