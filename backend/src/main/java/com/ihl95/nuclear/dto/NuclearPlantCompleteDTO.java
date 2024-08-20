package com.ihl95.nuclear.dto;

import java.util.List;

public record NuclearPlantCompleteDTO(
  Long id,
  String name,
  String location,
  List<ReactorDTO> reactors,
  List<MaintenancePlanDTO> maintenancePlans,
  List<EmergencyPlanDTO> emergencyPlans,
  List<IncidentDTO> incidents
/*   ,
  ,
  ,
  List<OperatorDTO> operators */
) {}
