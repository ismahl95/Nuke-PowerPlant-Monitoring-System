package com.ihl95.nuclear.mapper;

import org.mapstruct.Mapper;
import com.ihl95.nuclear.dto.MaintenanceDTO;
import com.ihl95.nuclear.model.Maintenance;

@Mapper(componentModel = "spring", uses = {ReactorMapper.class, EquipmentMapper.class, MaintenancePlanMapper.class})
public interface MaintenanceMapper {

    MaintenanceDTO toMaintenanceDTO(Maintenance maintenance);

    Maintenance toMaintenance(MaintenanceDTO maintenanceDTO);

}

