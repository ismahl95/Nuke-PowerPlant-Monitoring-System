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
 * Security Note:
 * - Pattern is precompiled to prevent repeated regex compilation overhead
 * - Input length is limited to prevent ReDoS (Regular Expression Denial of Service)
 * - RFC 5321 specifies max email length of 254 characters
 * - Pattern uses atomic grouping equivalent logic to prevent catastrophic backtracking
 *
 * @author GitHub Copilot
 */
@Component("supplierContactValidator")
public class ContactValidator extends SupplierValidator {

    private static final Logger logger = LoggerFactory.getLogger(ContactValidator.class);

    // Maximum email length (RFC 5321: 254 characters)
    private static final int MAX_EMAIL_LENGTH = 254;

    // Simple email validation pattern (precompiled)
    // Matches: username@domain.extension
    // Pattern designed to be ReDoS-resistant: uses simple character classes without nested quantifiers
    // Pattern: ^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

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

        // Prevent ReDoS attacks by limiting input length
        if (trimmedContact.length() > MAX_EMAIL_LENGTH) {
            return ValidationResult.invalid("Supplier contact (email) exceeds maximum length of " + MAX_EMAIL_LENGTH + " characters");
        }

        if (!EMAIL_PATTERN.matcher(trimmedContact).matches()) {
            return ValidationResult.invalid("Supplier contact must be a valid email address");
        }

        logger.debug("Contact validation passed");
        return ValidationResult.valid();
    }
}







