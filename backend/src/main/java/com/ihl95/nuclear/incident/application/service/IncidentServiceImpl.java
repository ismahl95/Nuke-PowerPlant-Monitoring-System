package com.ihl95.nuclear.incident.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ihl95.nuclear.incident.application.dto.IncidentDTO;
import com.ihl95.nuclear.incident.application.mapper.IncidentMapper;
import com.ihl95.nuclear.incident.application.repository.IncidentRepository;
import com.ihl95.nuclear.incident.domain.Incident;

@Service
@Transactional
public class IncidentServiceImpl implements IncidentService {

    private final IncidentRepository incidentRepository;
    private final IncidentMapper incidentMapper;

    public IncidentServiceImpl(
        IncidentRepository incidentRepository,
        IncidentMapper incidentMapper) {
        this.incidentRepository = incidentRepository;
        this.incidentMapper = incidentMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<IncidentDTO> getAllIncidents() {
        return incidentRepository.findAll()
            .stream()
            .map(incidentMapper::toIncidentDTO)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public IncidentDTO getIncidentById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Incident ID cannot be null");
        }
        return incidentRepository.findById(id)
            .map(incidentMapper::toIncidentDTO)
            .orElseThrow(() -> new IllegalArgumentException("Incident not found with id: " + id));
    }

    @Override
    public IncidentDTO createIncident(IncidentDTO incidentDTO) {
        if (incidentDTO == null) {
            throw new IllegalArgumentException("Incident data cannot be null");
        }
        Incident incident = incidentMapper.toIncident(incidentDTO);
        Incident savedIncident = incidentRepository.save(incident);
        return incidentMapper.toIncidentDTO(savedIncident);
    }

    @Override
    public IncidentDTO updateIncident(Long id, IncidentDTO incidentDTO) {
        if (id == null) {
            throw new IllegalArgumentException("Incident ID cannot be null");
        }
        if (incidentDTO == null) {
            throw new IllegalArgumentException("Incident data cannot be null");
        }

        Incident incident = incidentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Incident not found with id: " + id));

        Incident updatedIncident = incidentMapper.toIncident(incidentDTO);
        updatedIncident.setId(id);

        Incident savedIncident = incidentRepository.save(updatedIncident);
        return incidentMapper.toIncidentDTO(savedIncident);
    }

    @Override
    public void deleteIncident(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Incident ID cannot be null");
        }

        Incident incident = incidentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Incident not found with id: " + id));

        incidentRepository.delete(incident);
    }
}
