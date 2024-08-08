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

    @Mapping(source = "reactor.id", target = "reactorId")
    @Mapping(source = "equipment.id", target = "equipmentId")
    @Mapping(source = "maintenancePlan.id", target = "maintenancePlanId")
    MaintenanceDTO toMaintenanceDTO(Maintenance maintenance);

    @Mapping(source = "reactorId", target = "reactor", qualifiedByName = "idToReactor")
    @Mapping(source = "equipmentId", target = "equipment", qualifiedByName = "idToEquipment")
    @Mapping(source = "maintenancePlanId", target = "maintenancePlan", qualifiedByName = "idToMaintenancePlan")
    Maintenance toMaintenance(MaintenanceDTO maintenanceDTO);

    @Named("idToReactor")
    default Reactor idToReactor(Long id) {
        // Implementar lógica para recuperar Reactor por ID, por ejemplo, desde un repositorio
        return null; // Implementar la lógica real aquí
    }

    @Named("idToEquipment")
    default Equipment idToEquipment(Long id) {
        // Implementar lógica para recuperar Equipment por ID, por ejemplo, desde un repositorio
        return null; // Implementar la lógica real aquí
    }

    @Named("idToMaintenancePlan")
    default MaintenancePlan idToMaintenancePlan(Long id) {
        // Implementar lógica para recuperar MaintenancePlan por ID, por ejemplo, desde un repositorio
        return null; // Implementar la lógica real aquí
    }
}

