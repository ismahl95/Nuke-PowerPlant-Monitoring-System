package com.ihl95.nuclear.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.ihl95.nuclear.dto.MaintenanceDTO;
import com.ihl95.nuclear.model.Equipment;
import com.ihl95.nuclear.model.Maintenance;
import com.ihl95.nuclear.model.MaintenancePlan;
import com.ihl95.nuclear.model.Reactor;

@Mapper(componentModel = "spring", uses = {ReactorMapper.class, EquipmentMapper.class, MaintenancePlanMapper.class})
public interface MaintenanceMapper {

    MaintenanceDTO toMaintenanceDTO(Maintenance maintenance);

    Maintenance toMaintenance(MaintenanceDTO maintenanceDTO);

}

