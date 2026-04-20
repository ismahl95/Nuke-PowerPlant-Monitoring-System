package com.ihl95.nuclear.nuclearplant.application.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;

@Builder
public record NuclearPlantDTO(
    Long id,

    @NotBlank(message = "Plant name is required")
    String name,
    
    @NotBlank(message = "Plant location is required")
    String location
) {}
