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
public class SensorReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double measurementValue;         // Valor de la lectura
    private LocalDateTime date;   // Fecha de la lectura

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;        // Relación con Sensor
}
