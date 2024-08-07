package com.ihl95.nuclear.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ihl95.nuclear.enums.ControlSystemType;

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
    private Reactor reactor; // Relación con Reactor
}
