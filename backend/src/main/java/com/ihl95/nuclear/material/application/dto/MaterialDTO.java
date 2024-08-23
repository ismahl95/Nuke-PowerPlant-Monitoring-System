package com.ihl95.nuclear.material.application.dto;

import com.ihl95.nuclear.material.domain.enums.MaterialType;
import com.ihl95.nuclear.material.domain.enums.UnitOfMeasure;
import com.ihl95.nuclear.supplier.application.dto.SupplierDTO;

public record MaterialDTO(
    Long id,
    String name,
    MaterialType type,
    int quantity,
    UnitOfMeasure unitOfMeasure,
    SupplierDTO supplier
) {}
