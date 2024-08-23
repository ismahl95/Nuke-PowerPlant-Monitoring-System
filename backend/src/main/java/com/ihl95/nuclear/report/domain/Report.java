package com.ihl95.nuclear.report.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ihl95.nuclear.report.domain.enums.ReportStatus;

import lombok.Data;

@Entity
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;              // Título
    private String content;            // Contenido
    @Enumerated(EnumType.STRING)
    private ReportStatus status;       // Estado del reporte (borrador, finalizado, archivado)
    private LocalDateTime creationDate; // Fecha de creación
}
