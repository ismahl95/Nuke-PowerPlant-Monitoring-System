package com.ihl95.nuclear.supplier.application.validator;

import com.ihl95.nuclear.supplier.application.dto.SupplierDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Validador: Contacto (email) del proveedor.
 *
 * Verifica que:
 * - El contacto no sea null
 * - El contacto no esté en blanco
 * - El contacto tenga un formato de email válido
 *
 * Email regex pattern: basic validation (precompiled for performance and security)
 * More comprehensive patterns can be found at: https://www.emailregex.com/
 *
 * Security Note: Pattern is precompiled to prevent ReDoS (Regular Expression Denial of Service)
 * attacks through repeated regex compilation and backtracking.
 *
 * @author GitHub Copilot
 */
@Component("supplierContactValidator")
public class ContactValidator extends SupplierValidator {

    private static final Logger logger = LoggerFactory.getLogger(ContactValidator.class);

    // Simple email validation pattern (precompiled)
    // Matches: username@domain.extension
    // Pattern: ^[^@\s]+@[^@\s]+\.[^@\s]+$
    // Precompiled to prevent regex compilation on every validation call
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

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

        if (!EMAIL_PATTERN.matcher(trimmedContact).matches()) {
            return ValidationResult.invalid("Supplier contact must be a valid email address");
        }

        logger.debug("Contact validation passed");
        return ValidationResult.valid();
    }
}





