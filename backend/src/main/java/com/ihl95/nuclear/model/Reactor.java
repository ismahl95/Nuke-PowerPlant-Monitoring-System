package com.ihl95.nuclear.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.ihl95.nuclear.enums.ReactorStatus;
import com.ihl95.nuclear.enums.ReactorType;

import lombok.Data;

@Entity
@Data
public class Reactor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;             // Nombre
    @Enumerated(EnumType.STRING)
    private ReactorType type;        // Tipo de reactor (agua en ebullición, agua a presión, etc.)
    private LocalDateTime installationDate; // Fecha de instalación
    @Enumerated(EnumType.STRING)
    private ReactorStatus status;    // Estado (en funcionamiento, en mantenimiento, parado)

    @ManyToOne
    @JoinColumn(name = "nuclear_plant_id")
    private NuclearPlant nuclearPlant; // Relación con PlantaNuclear

    @OneToMany(mappedBy = "reactor")
    private List<ReactorState> states; // Relación con EstadoReactor

    @OneToMany(mappedBy = "reactor")
    private List<Anomaly> anomalies;    // Relación con Anomalia

    @OneToMany(mappedBy = "reactor")
    private List<Maintenance> maintenances; // Relación con Mantenimiento
}
