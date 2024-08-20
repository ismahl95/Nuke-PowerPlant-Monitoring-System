package com.ihl95.nuclear.sensorReading.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ihl95.nuclear.sensor.domain.Sensor;

import lombok.Data;

@Entity
@Data
public class SensorReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double measurementValue; // Valor de la lectura
    private LocalDateTime date;       // Fecha de la lectura

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    @JsonBackReference // Evita la recursión infinita al serializar la relación con Sensor
    private Sensor sensor; // Relación con Sensor

    // Otros atributos y métodos
}
