package com.ihl95.nuclear.incident.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ihl95.nuclear.incident.domain.enums.IncidentSeverity;
import com.ihl95.nuclear.nuclearplant.domain.NuclearPlant;

import lombok.Data;

@Entity
@Data
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;  // Descripción
    private LocalDateTime date;  // Fecha
    @Enumerated(EnumType.STRING)
    private IncidentSeverity severity; // Gravedad (crítica, mayor, menor)

    @ManyToOne
    @JoinColumn(name = "nuclear_plant_id")
    @JsonBackReference
    private NuclearPlant nuclearPlant; // Relación con PlantaNuclear
}
