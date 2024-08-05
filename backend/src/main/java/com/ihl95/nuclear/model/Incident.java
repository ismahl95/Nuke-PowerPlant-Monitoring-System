package com.ihl95.nuclear.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ihl95.nuclear.enums.IncidentSeverity;

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
    private NuclearPlant nuclearPlant; // Relación con PlantaNuclear
}
