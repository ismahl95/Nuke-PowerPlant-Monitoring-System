package com.ihl95.nuclear.nuclearplant.application.validator;

import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Patrón: Chain of Responsibility
 * 
 * Validador abstracto para plantas nucleares usando cadena de responsabilidad.
 * Cada validador:
 * 1. Valida su aspecto específico
 * 2. Si es válido, pasa al siguiente en la cadena
 * 3. Si es inválido, detiene la cadena y retorna error
 * 
 * Ventajas:
 * - Fail-fast: detiene en el primer error
 * - Composable: fácil agregar nuevos validadores
 * - Testeable: cada validador aislado
 * - Flexible: cambiar orden de validación
 * 
 * @see <a href="https://refactoring.guru/design-patterns/chain-of-responsibility">Chain of Responsibility Pattern</a>
 * 
 * @author GitHub Copilot
 */
public abstract class NuclearPlantValidator {
    
    private static final Logger logger = LoggerFactory.getLogger(NuclearPlantValidator.class);
    
    protected NuclearPlantValidator next;
    
    /**
     * Establece el siguiente validador en la cadena.
     * 
     * @param next el siguiente validador
     * @return este validador para permitir encadenamiento
     */
    public NuclearPlantValidator setNext(NuclearPlantValidator next) {
        this.next = next;
        return this;
    }
    
    /**
     * Valida el DTO usando la cadena de validadores.
     * 
     * Si este validador pasa, delegará al siguiente.
     * Si alguno falla, retorna el error sin continuar.
     * 
     * @param dto el DTO a validar
     * @return resultado con éxito o error
     */
    public final ValidationResult validate(NuclearPlantDTO dto) {
        logger.debug("Executing validator: {}", this.getClass().getSimpleName());
        
        // Ejecutar validación específica de este validador
        ValidationResult result = doValidate(dto);
        
        // Si falla, detener cadena y retornar
        if (!result.isValid()) {
            logger.warn("Validation failed in {}: {}", 
                this.getClass().getSimpleName(), result.getMessage());
            return result;
        }
        
        // Si hay siguiente, continuar cadena
        if (next != null) {
            logger.debug("Passing to next validator in chain");
            return next.validate(dto);
        }
        
        // Fin de cadena, todo válido
        logger.debug("Validation chain completed successfully");
        return ValidationResult.valid();
    }
    
    /**
     * Implementar validación específica en subclases.
     * 
     * @param dto el DTO a validar
     * @return resultado de validación
     */
    protected abstract ValidationResult doValidate(NuclearPlantDTO dto);
}
