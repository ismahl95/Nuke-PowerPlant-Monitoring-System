package com.ihl95.nuclear.service;

import org.springframework.stereotype.Service;

import com.ihl95.nuclear.model.Incident;

@Service
public class IncidentServiceImpl implements IncidentService {

    @Override
    public Incident getIncidentById(Long id) {
        // Implementación mínima para pruebas; ajusta según tus necesidades
        return new Incident(); // Devuelve un incidente vacío o simulado
    }
}
