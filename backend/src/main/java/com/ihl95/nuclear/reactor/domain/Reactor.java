package com.ihl95.nuclear.reactor.domain;

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
import com.ihl95.nuclear.nuclearplant.domain.NuclearPlant;
import com.ihl95.nuclear.reactor.domain.enums.ReactorStatus;
import com.ihl95.nuclear.reactor.domain.enums.ReactorType;
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

}
