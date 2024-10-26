package com.ihl95.nuclear.nuclearplant.application.dto;

import java.util.List;

import com.ihl95.nuclear.emergencyplan.application.dto.EmergencyPlanDTO;
import com.ihl95.nuclear.incident.application.dto.IncidentDTO;
import com.ihl95.nuclear.maintenanceplan.application.dto.MaintenancePlanDTO;
import com.ihl95.nuclear.operator.application.dto.OperatorDTO;
import com.ihl95.nuclear.reactor.application.dto.ReactorDTO;

public record NuclearPlantCompleteDTO(
  Long id,
  String name,
  String location,
  List<ReactorDTO> reactors,
  List<MaintenancePlanDTO> maintenancePlans,
  List<EmergencyPlanDTO> emergencyPlans,
  List<IncidentDTO> incidents,
  List<OperatorDTO> operators
) {}
