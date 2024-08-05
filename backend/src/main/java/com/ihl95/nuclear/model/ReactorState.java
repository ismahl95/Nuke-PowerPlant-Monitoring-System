package com.ihl95.nuclear.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ihl95.nuclear.enums.ReactorStateType;

import lombok.Data;

@Entity
@Data
public class ReactorState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReactorStateType state; // Estado del reactor (operativo, inactivo, etc.)
    private LocalDateTime date;      // Fecha del estado

    @ManyToOne
    @JoinColumn(name = "reactor_id")
    private Reactor reactor;         // Relación con Reactor
}
