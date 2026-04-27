package com.ihl95.nuclear.supplier.application.validator;

import com.ihl95.nuclear.supplier.application.dto.SupplierDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Validador: Contacto (email) del proveedor.
 *
 * Verifica que:
 * - El contacto no sea null
 * - El contacto no esté en blanco
 * - El contacto tenga un formato de email válido
 *
 * Email regex pattern: basic validation
 * More comprehensive patterns can be found at: https://www.emailregex.com/
 *
 * @author GitHub Copilot
 */
@Component("supplierContactValidator")
public class ContactValidator extends SupplierValidator {

    private static final Logger logger = LoggerFactory.getLogger(ContactValidator.class);

    // Simple email validation pattern
    // Matches: username@domain.extension
    // Pattern: ^[^@\s]+@[^@\s]+\.[^@\s]+$
    private static final String EMAIL_PATTERN = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";

    @Override
    protected ValidationResult doValidate(SupplierDTO dto) {
        logger.debug("Validating supplier contact: {}", dto.contact());

        if (dto.contact() == null) {
            return ValidationResult.invalid("Supplier contact (email) is required");
        }

        String trimmedContact = dto.contact().trim();

        if (trimmedContact.isBlank()) {
            return ValidationResult.invalid("Supplier contact (email) cannot be empty");
        }

        if (!trimmedContact.matches(EMAIL_PATTERN)) {
            return ValidationResult.invalid("Supplier contact must be a valid email address");
        }

        logger.debug("Contact validation passed");
        return ValidationResult.valid();
    }
}



