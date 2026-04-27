package com.ihl95.nuclear.nuclearplant.application.validator;

import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Validador: Ubicación de planta nuclear.
 * 
 * Verifica que:
 * - La ubicación no sea null
 * - La ubicación no esté en blanco
 * - La ubicación tenga al menos 5 caracteres
 * - La ubicación no supere 255 caracteres
 * 
 * @author GitHub Copilot
 */
@Component
public class LocationValidator extends NuclearPlantValidator {
    
    private static final Logger logger = LoggerFactory.getLogger(LocationValidator.class);
    
    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 255;
    
    @Override
    protected ValidationResult doValidate(NuclearPlantDTO dto) {
        logger.debug("Validating plant location: {}", dto.location());
        
        if (dto.location() == null) {
            return ValidationResult.invalid("Plant location is required");
        }
        
        String trimmedLocation = dto.location().trim();
        
        if (trimmedLocation.isBlank()) {
            return ValidationResult.invalid("Plant location cannot be empty");
        }
        
        if (trimmedLocation.length() < MIN_LENGTH) {
            return ValidationResult.invalid(
                String.format("Plant location must have at least %d characters", MIN_LENGTH)
            );
        }
        
        if (trimmedLocation.length() > MAX_LENGTH) {
            return ValidationResult.invalid(
                String.format("Plant location must not exceed %d characters", MAX_LENGTH)
            );
        }
        
        logger.debug("Location validation passed");
        return ValidationResult.valid();
    }
}
