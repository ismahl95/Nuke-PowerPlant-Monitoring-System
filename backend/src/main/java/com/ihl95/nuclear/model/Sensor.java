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
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // Tipo de sensor
    private String location; // Ubicación del sensor

    @OneToMany(mappedBy = "sensor")
    private List<SensorReading> sensorReadings; // Relación con SensorReading
}
