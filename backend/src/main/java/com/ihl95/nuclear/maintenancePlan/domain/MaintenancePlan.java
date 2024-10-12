package com.ihl95.nuclear.maintenancePlan.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ihl95.nuclear.maintenance.domain.Maintenance;
import com.ihl95.nuclear.nuclearPlant.domain.NuclearPlant;

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

    @OneToMany(mappedBy = "maintenancePlan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Maneja la serialización desde MaintenancePlan hacia Maintenance
    private List<Maintenance> maintenances = new ArrayList<>();

    // Getters y setters
}