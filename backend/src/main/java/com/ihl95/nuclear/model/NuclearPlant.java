package com.ihl95.nuclear.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Builder
public class NuclearPlant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Nombre de la planta
    private String location; // Ubicación

    @OneToMany(mappedBy = "nuclearPlant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Reactor> reactors; // Relación con Reactor

    @OneToMany(mappedBy = "nuclearPlant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MaintenancePlan> maintenancePlans; // Relación con MaintenancePlan

    @OneToMany(mappedBy = "nuclearPlant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<EmergencyPlan> emergencyPlans; // Relación con EmergencyPlan

    @OneToMany(mappedBy = "nuclearPlant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Incident> incidents; // Relación con Incident

/*     

    @OneToMany(mappedBy = "nuclearPlant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Operator> operators; // Relación con Operator */

    // Getters and Setters
}
