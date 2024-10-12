package com.ihl95.nuclear.maintenance.domain;

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
import com.ihl95.nuclear.equipment.domain.Equipment;
import com.ihl95.nuclear.maintenance.domain.enums.MaintenanceStatus;
import com.ihl95.nuclear.maintenancePlan.domain.MaintenancePlan;
import com.ihl95.nuclear.reactor.domain.Reactor;

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
    @JsonBackReference // Evita la recursión infinita en la serialización/deserialización
    private Reactor reactor; // Relación opcional con Reactor

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = true)
    @JsonBackReference // Evita la recursión infinita en la serialización/deserialización
    private Equipment equipment; // Relación opcional con Equipment

    @ManyToOne
    @JoinColumn(name = "maintenance_plan_id")
    @JsonBackReference // Evita la recursión infinita en la serialización/deserialización
    private MaintenancePlan maintenancePlan; // Relación con MaintenancePlan
}
