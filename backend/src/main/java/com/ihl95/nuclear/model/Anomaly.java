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

import com.ihl95.nuclear.enums.AnomalySeverity;

import lombok.Data;

@Entity
@Data
public class Anomaly {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;  // Descripción
    private LocalDateTime date;  // Fecha
    @Enumerated(EnumType.STRING)
    private AnomalySeverity severity; // Gravedad (alta, media, baja)

    @ManyToOne
    @JoinColumn(name = "reactor_id")
    private Reactor reactor;    // Relación con Reactor
}
