package com.ihl95.nuclear.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ihl95.nuclear.enums.EquipmentType;

import lombok.Data;

@Entity
@Data
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Nombre del equipo
    private String manufacturer; // Fabricante
    private LocalDate installationDate; // Fecha de instalación

    @Enumerated(EnumType.STRING)
    private EquipmentType type; // Tipo de equipo (bomba, válvula, etc.)

    @OneToMany(mappedBy = "equipment")
    @JsonManagedReference // Evitar recursión infinita
    private List<Maintenance> maintenances; // Relación con Mantenimiento

    // Getters and Setters
}
