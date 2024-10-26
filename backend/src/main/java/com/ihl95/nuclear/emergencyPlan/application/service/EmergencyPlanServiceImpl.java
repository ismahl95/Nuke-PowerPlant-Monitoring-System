package com.ihl95.nuclear.emergencyplan.application.service;

import org.springframework.stereotype.Service;

import com.ihl95.nuclear.emergencyplan.domain.EmergencyPlan;

@Service
public class EmergencyPlanServiceImpl implements EmergencyPlanService {

    @Override
    public EmergencyPlan getEmergencyPlanById(Long id) {
        // Implementación mínima para pruebas; ajusta según tus necesidades
        return new EmergencyPlan(); // Devuelve un plan de emergencia vacío o simulado
    }
}

