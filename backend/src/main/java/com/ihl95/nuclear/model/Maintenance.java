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

import com.ihl95.nuclear.enums.MaintenanceStatus;

import lombok.Data;

@Entity
@Data
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description; // Descripción
    private LocalDateTime startDate; // Fecha de inicio
    private LocalDateTime endDate;   // Fecha de fin
    @Enumerated(EnumType.STRING)
    private MaintenanceStatus status; // Estado (completado, en curso)

    @ManyToOne
    @JoinColumn(name = "reactor_id")
    private Reactor reactor;  // Relación con Reactor
}
