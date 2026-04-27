package com.ihl95.nuclear.nuclearplant.application.validator;

import lombok.Builder;
import lombok.Getter;

/**
 * Resultado de una validación.
 * 
 * @author GitHub Copilot
 */
@Getter
@Builder
public class ValidationResult {
    private final boolean valid;
    private final String message;
    
    public static ValidationResult valid() {
        return ValidationResult.builder()
            .valid(true)
            .message("Validation passed")
            .build();
    }
    
    public static ValidationResult invalid(String message) {
        return ValidationResult.builder()
            .valid(false)
            .message(message)
            .build();
    }
}
