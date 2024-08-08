package com.ihl95.nuclear.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ihl95.nuclear.enums.SensorType;

import lombok.Data;

@Entity
@Data
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SensorType type; // Tipo de sensor

    private String location; // Ubicación del sensor

    @ManyToOne
    @JoinColumn(name = "reactor_id")
    @JsonBackReference // Evita la recursión infinita al serializar la relación con Reactor
    private Reactor reactor; // Relación con Reactor

    @OneToMany(mappedBy = "sensor")
    @JsonManagedReference // Maneja la serialización de la lista de SensorReadings
    private List<SensorReading> sensorReadings; // Relación con SensorReading
}
