package com.ihl95.nuclear.controlsystem.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ihl95.nuclear.controlsystem.domain.enums.ControlSystemType;
import com.ihl95.nuclear.reactor.domain.Reactor;

import lombok.Data;

@Entity
@Data
public class ControlSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // Nombre

    @Enumerated(EnumType.STRING)
    private ControlSystemType type;  // Tipo (Sistema de Control Distribuido, SCADA, etc.)

    @ManyToOne
    @JoinColumn(name = "reactor_id")
    @JsonBackReference
    private Reactor reactor; // Relación con Reactor
}
