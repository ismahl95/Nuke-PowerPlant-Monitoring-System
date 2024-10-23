package com.ihl95.nuclear.supplier.application.dto;

import lombok.Builder;

@Builder
public record SupplierDTO(
    Long id,
    String name,
    String contact,
    String phone
) {}
