package com.ihl95.nuclear.sensor.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ihl95.nuclear.reactor.domain.Reactor;
import com.ihl95.nuclear.sensor.domain.enums.SensorType;
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

}
