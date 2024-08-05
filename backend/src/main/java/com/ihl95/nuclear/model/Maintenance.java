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

    private String description;  // Descripción
    private LocalDateTime startDate;  // Fecha de inicio
    private LocalDateTime endDate;    // Fecha de fin

    @Enumerated(EnumType.STRING)
    private MaintenanceStatus status; // Estado del mantenimiento

    @ManyToOne
    @JoinColumn(name = "reactor_id", nullable = true)
    private Reactor reactor; // Relación opcional con Reactor

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = true)
    private Equipment equipment; // Relación opcional con Equipment

    @ManyToOne
    @JoinColumn(name = "maintenance_plan_id")
    private MaintenancePlan maintenancePlan; // Relación con MaintenancePlan
}
