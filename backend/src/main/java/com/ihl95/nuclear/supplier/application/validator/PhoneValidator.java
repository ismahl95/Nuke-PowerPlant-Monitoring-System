package com.ihl95.nuclear.supplier.application.validator;

import com.ihl95.nuclear.supplier.application.dto.SupplierDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Validador: Teléfono del proveedor.
 *
 * Verifica que:
 * - El teléfono no sea null
 * - El teléfono no esté en blanco
 * - El teléfono tenga un formato válido (7-15 dígitos, opcional + inicial)
 *
 * Phone regex pattern: ^\\+?[0-9]{7,15}$
 * Examples: +34912345678, 912345678, +1234567890
 *
 * @author GitHub Copilot
 */
@Component("supplierPhoneValidator")
public class PhoneValidator extends SupplierValidator {

    private static final Logger logger = LoggerFactory.getLogger(PhoneValidator.class);

    // Phone format: optional + followed by 7-15 digits
    private static final String PHONE_PATTERN = "^\\+?[0-9]{7,15}$";

    @Override
    protected ValidationResult doValidate(SupplierDTO dto) {
        logger.debug("Validating supplier phone: {}", dto.phone());

        if (dto.phone() == null) {
            return ValidationResult.invalid("Supplier phone number is required");
        }

        String trimmedPhone = dto.phone().trim();

        if (trimmedPhone.isBlank()) {
            return ValidationResult.invalid("Supplier phone number cannot be empty");
        }

        if (!trimmedPhone.matches(PHONE_PATTERN)) {
            return ValidationResult.invalid(
                "Supplier phone must be valid (7-15 digits, optional + prefix)"
            );
        }

        logger.debug("Phone validation passed");
        return ValidationResult.valid();
    }
}



