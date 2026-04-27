package com.ihl95.nuclear.nuclearplant.application.config;

import com.ihl95.nuclear.nuclearplant.application.validator.NuclearPlantValidator;
import com.ihl95.nuclear.nuclearplant.application.validator.NameValidator;
import com.ihl95.nuclear.nuclearplant.application.validator.LocationValidator;
import com.ihl95.nuclear.nuclearplant.application.validator.UniquePlantValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de la cadena de validadores para NuclearPlant.
 * 
 * Patrón: Chain of Responsibility
 * 
 * Construye la cadena en el siguiente orden:
 * 1. NameValidator - valida nombre (formato y longitud)
 * 2. LocationValidator - valida ubicación (formato y longitud)
 * 3. UniquePlantValidator - valida unicidad de nombre
 * 
 * El orden es importante: validaciones básicas antes de búsquedas en BD.
 * 
 * @author GitHub Copilot
 */
@Configuration
public class ValidatorChainConfig {
    
    /**
     * Construye la cadena de validadores.
     * 
     * @param nameValidator validador de nombre
     * @param locationValidator validador de ubicación
     * @param uniqueValidator validador de unicidad
     * @return el primer validador de la cadena
     */
    @Bean
    public NuclearPlantValidator validatorChain(
            NameValidator nameValidator,
            LocationValidator locationValidator,
            UniquePlantValidator uniqueValidator) {
        
        // Construir cadena: Name → Location → Unique
        nameValidator.setNext(locationValidator);
        locationValidator.setNext(uniqueValidator);
        
        return nameValidator;
    }
}
