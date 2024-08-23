package com.ihl95.nuclear.reactor.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ihl95.nuclear.anomaly.domain.Anomaly;
import com.ihl95.nuclear.controlSystem.domain.ControlSystem;
import com.ihl95.nuclear.maintenance.domain.Maintenance;
import com.ihl95.nuclear.nuclearPlant.domain.NuclearPlant;
import com.ihl95.nuclear.reactor.domain.enums.ReactorStatus;
import com.ihl95.nuclear.reactor.domain.enums.ReactorType;
import com.ihl95.nuclear.sensor.domain.Sensor;

import lombok.Data;

@Entity
@Data
public class Reactor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Nombre
    @Enumerated(EnumType.STRING)
    private ReactorType type; // Tipo de reactor (agua en ebullición, agua a presión, etc.)
    private LocalDateTime installationDate; // Fecha de instalación
    @Enumerated(EnumType.STRING)
    private ReactorStatus status; // Estado (en funcionamiento, en mantenimiento, parado)

    @ManyToOne
    @JoinColumn(name = "nuclear_plant_id")
    @JsonBackReference // Evitar recursión infinita
    private NuclearPlant nuclearPlant; // Relación con NuclearPlant

    @OneToMany(mappedBy = "reactor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Evitar recursión infinita
    private List<Anomaly> anomalies; // Relación con Anomaly

    @OneToMany(mappedBy = "reactor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Evitar recursión infinita
    private List<Maintenance> maintenances; // Relación con Maintenance

    @OneToMany(mappedBy = "reactor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ControlSystem> controlSystems; // Relación con ControlSystem

    @OneToMany(mappedBy = "reactor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Sensor> sensor; // Relación con Sensor
}
