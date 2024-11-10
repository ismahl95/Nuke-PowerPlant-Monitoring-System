package com.ihl95.nuclear.supplier.application.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;

@Builder
public record SupplierDTO(
    Long id,

    @NotBlank(message = "El nombre es obligatorio")
    String name,

    @NotBlank(message = "El contacto es obligatorio")
    String contact,

    @NotBlank(message = "El teléfono es obligatorio")
    String phone
) {}
