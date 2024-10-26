package com.ihl95.nuclear.emergencyplan.application.service;

import com.ihl95.nuclear.emergencyplan.domain.EmergencyPlan;

public interface EmergencyPlanService {
  EmergencyPlan getEmergencyPlanById(Long id);
}
