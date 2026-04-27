package com.ihl95.nuclear.supplier.application.validator;

/**
 * Value Object representing the result of a validation operation.
 * Immutable and thread-safe.
 *
 * Supports both success and failure outcomes with optional error messages.
 */
public class ValidationResult {

    private final boolean valid;
    private final String message;

    private ValidationResult(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    /**
     * Creates a successful validation result.
     * @return ValidationResult indicating validation passed
     */
    public static ValidationResult valid() {
        return new ValidationResult(true, null);
    }

    /**
     * Creates a failed validation result with error message.
     * @param message Error message describing why validation failed
     * @return ValidationResult indicating validation failed
     */
    public static ValidationResult invalid(String message) {
        return new ValidationResult(false, message);
    }

    /**
     * @return true if validation passed, false otherwise
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * @return error message if validation failed, null if passed
     */
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ValidationResult{" +
            "valid=" + valid +
            ", message='" + message + '\'' +
            '}';
    }
}


