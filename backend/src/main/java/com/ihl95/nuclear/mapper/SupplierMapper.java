package com.ihl95.nuclear.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.dto.SupplierDTO;
import com.ihl95.nuclear.model.Supplier;

@Mapper
public interface SupplierMapper {
    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);

    SupplierDTO toSupplierDTO(Supplier supplier);

    Supplier toSupplier(SupplierDTO supplierDTO);
}
