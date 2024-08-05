package com.ihl95.nuclear.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

    @OneToMany(mappedBy = "operator")
    private List<Training> trainings; // Relación con Training
}
