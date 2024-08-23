package com.ihl95.nuclear.operator.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ihl95.nuclear.nuclearPlant.domain.NuclearPlant;
import com.ihl95.nuclear.training.domain.Training;

import lombok.Data;

@Entity
@Data
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;           // Nombre
    private String position;       // Cargo
    private String experience;     // Experiencia

    @ManyToOne
    @JoinColumn(name = "nuclear_plant_id")
    @JsonBackReference // Evita recursión infinita al serializar la relación con NuclearPlant
    private NuclearPlant nuclearPlant; // Relación con PlantaNuclear

    @OneToMany(mappedBy = "operator", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Maneja la serialización de la lista de entrenamientos
    private List<Training> trainings; // Relación con Training
}
