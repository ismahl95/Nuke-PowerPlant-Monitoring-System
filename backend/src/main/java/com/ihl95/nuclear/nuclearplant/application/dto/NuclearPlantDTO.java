package com.ihl95.nuclear.nuclearplant.application.dto;

import javax.validation.constraints.NotBlank;

public record NuclearPlantDTO(
    Long id,

    @NotBlank(message = "El nombre es obligatorio")
    String name,
    
    @NotBlank(message = "La localización es obligatoria")
    String location
) {}
