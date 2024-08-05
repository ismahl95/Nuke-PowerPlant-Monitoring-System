package com.ihl95.nuclear.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

    @OneToMany(mappedBy = "sensor")
    private List<SensorReading> sensorReadings; // Relación con SensorReading
}
