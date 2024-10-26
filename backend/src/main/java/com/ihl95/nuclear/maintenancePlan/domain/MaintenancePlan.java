package com.ihl95.nuclear.maintenanceplan.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ihl95.nuclear.nuclearplant.domain.NuclearPlant;

import lombok.Data;

@Entity
@Data
public class MaintenancePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;             // Nombre
    private String description;      // Descripción
    private LocalDateTime startDate; // Fecha de inicio
    private LocalDateTime endDate;   // Fecha de fin

    @ManyToOne
    @JoinColumn(name = "nuclear_plant_id")
    @JsonBackReference // Evita recursividad desde MaintenancePlan hacia NuclearPlant
    private NuclearPlant nuclearPlant; // Relación con PlantaNuclear

    private boolean isCompleted;    // Indica si el plan de mantenimiento está completado

    // Getters y setters
}