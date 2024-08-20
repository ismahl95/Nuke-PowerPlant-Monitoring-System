package com.ihl95.nuclear.training.domain;

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
import com.ihl95.nuclear.operator.domain.Operator;
import com.ihl95.nuclear.training.domain.enums.TrainingType;

import lombok.Data;

@Entity
@Data
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // Título
    private String description; // Descripción

    @Enumerated(EnumType.STRING)
    private TrainingType type; // Tipo de capacitación (seguridad, operacional, etc.)

    private LocalDateTime trainingDate; // Fecha de capacitación

    @ManyToOne
    @JoinColumn(name = "operator_id")
    @JsonBackReference // Evita la recursión infinita al serializar la relación con Operator
    private Operator operator; // Relación con Operator

    // Otros atributos y métodos
}
