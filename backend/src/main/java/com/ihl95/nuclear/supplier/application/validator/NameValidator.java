package com.ihl95.nuclear.supplier.application.validator;

import com.ihl95.nuclear.supplier.application.dto.SupplierDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Validador: Nombre del proveedor.
 *
 * Verifica que:
 * - El nombre no sea null
 * - El nombre no esté en blanco
 * - El nombre tenga al menos 3 caracteres
 * - El nombre no supere 255 caracteres
 *
 * @author GitHub Copilot
 */
@Component("supplierNameValidator")
public class NameValidator extends SupplierValidator {

    private static final Logger logger = LoggerFactory.getLogger(NameValidator.class);

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 255;

    @Override
    protected ValidationResult doValidate(SupplierDTO dto) {
        logger.debug("Validating supplier name: {}", dto.name());

        if (dto.name() == null) {
            return ValidationResult.invalid("Supplier name is required");
        }

        String trimmedName = dto.name().trim();

        if (trimmedName.isBlank()) {
            return ValidationResult.invalid("Supplier name cannot be empty");
        }

        if (trimmedName.length() < MIN_LENGTH) {
            return ValidationResult.invalid(
                String.format("Supplier name must have at least %d characters", MIN_LENGTH)
            );
        }

        if (trimmedName.length() > MAX_LENGTH) {
            return ValidationResult.invalid(
                String.format("Supplier name must not exceed %d characters", MAX_LENGTH)
            );
        }

        logger.debug("Name validation passed");
        return ValidationResult.valid();
    }
}



