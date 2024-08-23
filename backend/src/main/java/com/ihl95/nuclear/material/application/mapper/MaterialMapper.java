package com.ihl95.nuclear.material.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.material.application.dto.MaterialDTO;
import com.ihl95.nuclear.material.domain.Material;
import com.ihl95.nuclear.supplier.application.mapper.SupplierMapper;

@Mapper(uses = SupplierMapper.class)
public interface MaterialMapper {
    MaterialMapper INSTANCE = Mappers.getMapper(MaterialMapper.class);

    @Mapping(source = "supplier", target = "supplier")
    MaterialDTO toMaterialDTO(Material material);

    @Mapping(source = "supplier", target = "supplier")
    Material toMaterial(MaterialDTO materialDTO);
}
