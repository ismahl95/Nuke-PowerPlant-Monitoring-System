package com.ihl95.nuclear.incident.application.service;

import java.util.List;

import com.ihl95.nuclear.incident.application.dto.IncidentDTO;

public interface IncidentService {
  List<IncidentDTO> getAllIncidents();

  IncidentDTO getIncidentById(Long id);

  IncidentDTO createIncident(IncidentDTO incidentDTO);

  IncidentDTO updateIncident(Long id, IncidentDTO incidentDTO);

  void deleteIncident(Long id);
}
