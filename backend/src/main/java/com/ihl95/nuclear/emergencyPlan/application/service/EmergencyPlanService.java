package com.ihl95.nuclear.emergencyPlan.application.service;

import com.ihl95.nuclear.emergencyPlan.domain.EmergencyPlan;

public interface EmergencyPlanService {
  EmergencyPlan getEmergencyPlanById(Long id);
}
