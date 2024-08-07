package com.ihl95.nuclear.dto;

import com.ihl95.nuclear.enums.MaterialType;
import com.ihl95.nuclear.enums.UnitOfMeasure;

public record MaterialDTO(
    Long id,
    String name,
    MaterialType type,
    int quantity,
    UnitOfMeasure unitOfMeasure,
    SupplierDTO supplier
) {}
