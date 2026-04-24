package com.ihl95.nuclear.common.mocks;

import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearplant.domain.NuclearPlant;

/**
 * Datos de prueba compartidos para NuclearPlant.
 * Reutilizable en tests unitarios, integración y E2E.
 */
public class NuclearPlantTestData {

    // ── ENTIDADES ────────────────────────────────────

    public static NuclearPlant createNuclearPlantEntity(Long id, String name, String location) {
        if (id == null) {
            return NuclearPlant.builder()
                    .name(name)
                    .location(location)
                    .createdBy("system")
                    .build();
        }
        return NuclearPlant.builder()
                .id(id)
                .name(name)
                .location(location)
                .createdBy("system")
                .build();
    }

    public static NuclearPlant createNuclearPlantEntity() {
        return createNuclearPlantEntity(1L, "Planta Nuclear Central", "Madrid");
    }

    // ── DTOs ─────────────────────────────────────────

    public static NuclearPlantDTO createNuclearPlantDTO(Long id, String name, String location) {
        if (id == null) {
            return NuclearPlantDTO.builder()
                    .name(name)
                    .location(location)
                    .build();
        }
        return NuclearPlantDTO.builder()
                .id(id)
                .name(name)
                .location(location)
                .build();
    }

    public static NuclearPlantDTO createNuclearPlantDTO() {
        return createNuclearPlantDTO(1L, "Planta Nuclear Central", "Madrid");
    }

    // ── CONSTANTES ───────────────────────────────────

    public static final String VALID_PLANT_NAME = "Planta Nuclear Asc";
    public static final String VALID_PLANT_LOCATION = "Barcelona";
    public static final String INVALID_PLANT_NAME = "";
    public static final String INVALID_PLANT_LOCATION = "   ";
}

