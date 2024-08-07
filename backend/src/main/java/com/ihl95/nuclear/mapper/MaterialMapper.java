package com.ihl95.nuclear.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.dto.MaterialDTO;
import com.ihl95.nuclear.model.Material;

@Mapper(uses = SupplierMapper.class)
public interface MaterialMapper {
    MaterialMapper INSTANCE = Mappers.getMapper(MaterialMapper.class);

    @Mapping(source = "supplier", target = "supplier")
    MaterialDTO toMaterialDTO(Material material);

    @Mapping(source = "supplier", target = "supplier")
    Material toMaterial(MaterialDTO materialDTO);
}
