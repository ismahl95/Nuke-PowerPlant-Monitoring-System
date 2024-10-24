package com.ihl95.nuclear.equipment.domain;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.ihl95.nuclear.equipment.domain.enums.EquipmentType;
import lombok.Data;

@Entity
@Data
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Nombre del equipo
    private String manufacturer; // Fabricante
    private LocalDate installationDate; // Fecha de instalación

    @Enumerated(EnumType.STRING)
    private EquipmentType type; // Tipo de equipo (bomba, válvula, etc.)

}
