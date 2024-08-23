package com.ihl95.nuclear.maintenance.application.mapper;

import org.mapstruct.Mapper;

import com.ihl95.nuclear.equipment.application.mapper.EquipmentMapper;
import com.ihl95.nuclear.maintenance.application.dto.MaintenanceDTO;
import com.ihl95.nuclear.maintenance.domain.Maintenance;
import com.ihl95.nuclear.maintenancePlan.application.mapper.MaintenancePlanMapper;
import com.ihl95.nuclear.reactor.application.mapper.ReactorMapper;

@Mapper(componentModel = "spring", uses = {ReactorMapper.class, EquipmentMapper.class, MaintenancePlanMapper.class})
public interface MaintenanceMapper {

    MaintenanceDTO toMaintenanceDTO(Maintenance maintenance);

    Maintenance toMaintenance(MaintenanceDTO maintenanceDTO);

}

