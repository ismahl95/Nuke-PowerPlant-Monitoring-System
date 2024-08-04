package com.ihl95.nuclear.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String tipo; // Ej. combustible, refrigerante
    private int cantidad;
    private String unidadDeMedida; // Ej. litros, kg
}
