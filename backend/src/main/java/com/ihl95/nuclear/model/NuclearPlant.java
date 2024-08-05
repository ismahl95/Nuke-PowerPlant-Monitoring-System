package com.ihl95.nuclear.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class NuclearPlant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Nombre de la planta
    private String location; // Ubicación

    @OneToMany(mappedBy = "nuclearPlant")
    private List<Reactor> reactors; // Relación con Reactor

    @OneToMany(mappedBy = "nuclearPlant")
    private List<MaintenancePlan> maintenancePlans; // Relación con MaintenancePlan

    @OneToMany(mappedBy = "nuclearPlant")
    private List<EmergencyPlan> emergencyPlans; // Relación con EmergencyPlan

    @OneToMany(mappedBy = "nuclearPlant")
    private List<Incident> incidents; // Relación con Incident
}
