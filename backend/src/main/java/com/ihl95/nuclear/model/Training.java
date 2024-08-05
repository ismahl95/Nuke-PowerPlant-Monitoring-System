package com.ihl95.nuclear.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ihl95.nuclear.enums.TrainingType;

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
}
