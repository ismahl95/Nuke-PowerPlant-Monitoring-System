package com.ihl95.nuclear.supplier.application.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import lombok.Builder;

@Builder
public record SupplierDTO(
    Long id,

    @NotBlank(message = "Supplier name is required")
    String name,

    @NotBlank(message = "Contact is required")
    @Email(message = "Contact must be a valid email address")
    String contact,

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Phone number must be valid (7-15 digits, optional +)")
    String phone
) {}
