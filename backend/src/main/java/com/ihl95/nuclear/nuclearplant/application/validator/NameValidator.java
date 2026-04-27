package com.ihl95.nuclear.nuclearplant.application.validator;

import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Validador: Nombre de planta nuclear.
 * 
 * Verifica que:
 * - El nombre no sea null
 * - El nombre no esté en blanco
 * - El nombre tenga al menos 3 caracteres
 * - El nombre no supere 255 caracteres
 * 
 * @author GitHub Copilot
 */
@Component
public class NameValidator extends NuclearPlantValidator {
    
    private static final Logger logger = LoggerFactory.getLogger(NameValidator.class);
    
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 255;
    
    @Override
    protected ValidationResult doValidate(NuclearPlantDTO dto) {
        logger.debug("Validating plant name: {}", dto.name());
        
        if (dto.name() == null) {
            return ValidationResult.invalid("Plant name is required");
        }
        
        String trimmedName = dto.name().trim();
        
        if (trimmedName.isBlank()) {
            return ValidationResult.invalid("Plant name cannot be empty");
        }
        
        if (trimmedName.length() < MIN_LENGTH) {
            return ValidationResult.invalid(
                String.format("Plant name must have at least %d characters", MIN_LENGTH)
            );
        }
        
        if (trimmedName.length() > MAX_LENGTH) {
            return ValidationResult.invalid(
                String.format("Plant name must not exceed %d characters", MAX_LENGTH)
            );
        }
        
        logger.debug("Name validation passed");
        return ValidationResult.valid();
    }
}
