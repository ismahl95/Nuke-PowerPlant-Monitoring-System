package com.ihl95.nuclear.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class EquipmentMaintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description; // Descripción
    private LocalDateTime startDate; // Fecha de inicio
    private LocalDateTime endDate;   // Fecha de fin

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment; // Relación con Equipment
}
