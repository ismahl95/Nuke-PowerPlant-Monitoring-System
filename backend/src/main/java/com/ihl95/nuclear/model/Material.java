package com.ihl95.nuclear.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ihl95.nuclear.enums.MaterialType;
import com.ihl95.nuclear.enums.UnitOfMeasure;

import lombok.Data;

@Entity
@Data
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Nombre

    @Enumerated(EnumType.STRING)
    private MaterialType type; // Tipo (combustible, refrigerante, etc.)

    private int quantity; // Cantidad

    @Enumerated(EnumType.STRING)
    private UnitOfMeasure unitOfMeasure; // Unidad de medida (litros, kg, etc.)

    @ManyToOne
    @JoinColumn(name = "supplier_id") // La columna que se usará para la FK
    private Supplier supplier; // Relación con Supplier
}
