package com.ihl95.nuclear.nuclearplant.application.config;

import com.ihl95.nuclear.nuclearplant.application.validator.NuclearPlantValidator;
import com.ihl95.nuclear.nuclearplant.application.validator.NameValidator;
import com.ihl95.nuclear.nuclearplant.application.validator.LocationValidator;
import com.ihl95.nuclear.nuclearplant.application.validator.UniquePlantValidator;
import com.ihl95.nuclear.nuclearplant.infraestructure.NuclearPlantRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuración Spring para el Chain of Responsibility Pattern.
 *
 * Construye la cadena de validadores en el siguiente orden:
 * 1. NameValidator - Valida que el nombre sea válido
 * 2. LocationValidator - Valida que la ubicación sea válida
 * 3. UniquePlantValidator - Valida que el nombre sea único
 *
 * Usar @Qualifier("nuclearPlantValidatorChain") para inyectar en el Service.
 */
@Configuration
public class ValidatorChainConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ValidatorChainConfiguration.class);

    /**
     * Construye la cadena de validadores.
     *
     * El orden es importante:
     * - Name validation primero (más rápido, no requiere BD)
     * - Location validation segundo (más rápido, no requiere BD)
     * - Unique validation tercero (requiere acceso a BD)
     *
     * Esto asegura fail-fast en validaciones sintácticas antes de queries a BD.
     */
    @Bean(name = "nuclearPlantValidatorChain")
    public NuclearPlantValidator nuclearPlantValidatorChain(
            NameValidator nameValidator,
            LocationValidator locationValidator,
            UniquePlantValidator uniquePlantValidator) {

        logger.info("Constructing NuclearPlantValidator chain of responsibility");

        // Construir cadena: Name → Location → Unique
        nameValidator.setNext(locationValidator);
        locationValidator.setNext(uniquePlantValidator);

        logger.info("Validator chain constructed: NameValidator → LocationValidator → UniquePlantValidator");

        return nameValidator;  // El primero de la cadena es el entry point
    }
}

