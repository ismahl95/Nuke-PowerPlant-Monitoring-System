package com.ihl95.nuclear.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ihl95.nuclear.dto.MaintenanceDTO;
import com.ihl95.nuclear.model.Maintenance;

@Mapper(uses = {ReactorMapper.class, EquipmentMapper.class, MaintenancePlanMapper.class})
public interface MaintenanceMapper {
    MaintenanceMapper INSTANCE = Mappers.getMapper(MaintenanceMapper.class);

    @Mapping(source = "reactor", target = "reactor")
    @Mapping(source = "equipment", target = "equipment")
    @Mapping(source = "maintenancePlan", target = "maintenancePlan")
    MaintenanceDTO toMaintenanceDTO(Maintenance maintenance);

    @Mapping(source = "reactor", target = "reactor")
    @Mapping(source = "equipment", target = "equipment")
    @Mapping(source = "maintenancePlan", target = "maintenancePlan")
    Maintenance toMaintenance(MaintenanceDTO maintenanceDTO);
}
