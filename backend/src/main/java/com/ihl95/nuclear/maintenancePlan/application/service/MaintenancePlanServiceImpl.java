package com.ihl95.nuclear.maintenanceplan.application.service;

import org.springframework.stereotype.Service;

import com.ihl95.nuclear.maintenanceplan.domain.MaintenancePlan;

@Service
public class MaintenancePlanServiceImpl implements MaintenancePlanService {

    @Override
    public MaintenancePlan getMaintenancePlanById(Long id) {
        // Implementación mínima para pruebas; ajusta según tus necesidades
        return new MaintenancePlan();
    }
}