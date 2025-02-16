package com.ihl95.nuclear.nuclearplant.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.ihl95.nuclear.components.AuditEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa una planta nuclear.
 * 
 * <p>Esta clase es una entidad JPA que se utiliza para mapear la tabla de plantas nucleares en la base de datos.
 * Incluye información sobre el nombre, ubicación, fechas de creación y modificación, y los usuarios que realizaron
 * estas acciones.</p>
 * 
 * <p>La clase utiliza anotaciones de Lombok para generar automáticamente los constructores, getters, setters y otros
 * métodos comunes.</p>
 * 
 * <p>La clase también está anotada con {@link EntityListeners} para incluir un listener de auditoría que maneja
 * automáticamente las fechas y usuarios de creación y modificación.</p>
 * 
 * @see AuditEntityListener
 * 
 * @author Ismahl95
 */
@Entity
@EntityListeners(AuditEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NuclearPlant {

    /**
     * Identificador único de la planta nuclear.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de la planta nuclear.
     */
    private String name;

    /**
     * Ubicación de la planta nuclear.
     */
    private String location;

    /**
     * Fecha y hora en que se creó la planta nuclear.
     */
    @CreatedDate
    private LocalDateTime createdDate;

    /**
     * Fecha y hora en que se modificó por última vez la planta nuclear.
     */
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    /**
     * Usuario que creó la planta nuclear.
     */
    @CreatedBy
    private String createdBy;

    /**
     * Usuario que modificó por última vez la planta nuclear.
     */
    @LastModifiedBy
    private String lastModifiedBy;

}
