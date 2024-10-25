package com.ihl95.nuclear.equipment.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.equipment.application.dto.EquipmentDTO;
import com.ihl95.nuclear.equipment.domain.Equipment;

@Mapper(componentModel = "spring") // Asegúrate de incluir el uso de MaintenanceMapper
public interface EquipmentMapper {
    EquipmentMapper INSTANCE = Mappers.getMapper(EquipmentMapper.class);

    EquipmentDTO toEquipmentDTO(Equipment equipment);

    Equipment toEquipment(EquipmentDTO equipmentDTO);
}
