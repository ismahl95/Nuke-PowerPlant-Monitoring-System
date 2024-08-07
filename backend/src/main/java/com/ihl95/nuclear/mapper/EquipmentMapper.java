package com.ihl95.nuclear.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.dto.EquipmentDTO;
import com.ihl95.nuclear.model.Equipment;

@Mapper(uses = MaintenanceMapper.class)
public interface EquipmentMapper {
    EquipmentMapper INSTANCE = Mappers.getMapper(EquipmentMapper.class);

    @Mapping(source = "maintenances", target = "maintenances")
    EquipmentDTO toEquipmentDTO(Equipment equipment);

    @Mapping(source = "maintenances", target = "maintenances")
    Equipment toEquipment(EquipmentDTO equipmentDTO);
}
