package com.ihl95.nuclear.incident.application.service;

import com.ihl95.nuclear.incident.domain.Incident;

public interface IncidentService {
  Incident getIncidentById(Long id);
}
