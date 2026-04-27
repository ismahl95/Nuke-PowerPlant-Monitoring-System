package com.ihl95.nuclear.supplier.validator;

import static org.assertj.core.api.Assertions.*;

import com.ihl95.nuclear.common.mocks.SupplierTestData;
import com.ihl95.nuclear.supplier.application.dto.SupplierDTO;
import com.ihl95.nuclear.supplier.application.validator.ContactValidator;
import com.ihl95.nuclear.supplier.application.validator.NameValidator;
import com.ihl95.nuclear.supplier.application.validator.PhoneValidator;
import com.ihl95.nuclear.supplier.application.validator.SupplierValidator;
import com.ihl95.nuclear.supplier.application.validator.ValidationResult;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for SupplierValidator chain implementations.
 * Tests individual validators and the complete chain in isolation.
 * No Spring context, no database.
 *
 * Tests cover:
 * - NameValidator: all validation rules
 * - ContactValidator: all validation rules
 * - PhoneValidator: all validation rules
 * - Validator chain: fail-fast behavior and complete validation
 *
 * Execution: mvn test -Dtest=SupplierValidatorTest
 */
@DisplayName("SupplierValidator Unit Tests")
class SupplierValidatorTest {

    private NameValidator nameValidator;
    private ContactValidator contactValidator;
    private PhoneValidator phoneValidator;
    private SupplierDTO validSupplier;

    @BeforeEach
    void setUp() {
        nameValidator = new NameValidator();
        contactValidator = new ContactValidator();
        phoneValidator = new PhoneValidator();
        validSupplier = SupplierTestData.createSupplierDTO(
            null, "Valid Supplier", "contact@example.com", "+34912345678"
        );
    }

    // ─────────────────────────────────────────────────────────────
    // NAME VALIDATOR TESTS
    // ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("NameValidator → valid when name is correct")
    void nameValidator_shouldBeValid_whenNameCorrect() {
        ValidationResult result = nameValidator.validate(validSupplier);
        assertThat(result.isValid()).isTrue();
    }

    @Test
    @DisplayName("NameValidator → invalid when name is null")
    void nameValidator_shouldBeInvalid_whenNameNull() {
        SupplierDTO invalidDTO = new SupplierDTO(null, null, "test@example.com", "+34912345678");
        ValidationResult result = nameValidator.validate(invalidDTO);

        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).containsIgnoringCase("required");
    }

    @Test
    @DisplayName("NameValidator → invalid when name is blank")
    void nameValidator_shouldBeInvalid_whenNameBlank() {
        SupplierDTO invalidDTO = new SupplierDTO(null, "   ", "test@example.com", "+34912345678");
        ValidationResult result = nameValidator.validate(invalidDTO);

        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).containsIgnoringCase("empty");
    }

    @Test
    @DisplayName("NameValidator → invalid when name is too short")
    void nameValidator_shouldBeInvalid_whenNameTooShort() {
        SupplierDTO invalidDTO = new SupplierDTO(null, "AB", "test@example.com", "+34912345678");
        ValidationResult result = nameValidator.validate(invalidDTO);

        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).containsIgnoringCase("at least");
    }

    @Test
    @DisplayName("NameValidator → invalid when name is too long")
    void nameValidator_shouldBeInvalid_whenNameTooLong() {
        String longName = "A".repeat(256);
        SupplierDTO invalidDTO = new SupplierDTO(null, longName, "test@example.com", "+34912345678");
        ValidationResult result = nameValidator.validate(invalidDTO);

        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).containsIgnoringCase("exceed");
    }

    @Test
    @DisplayName("NameValidator → valid when name has minimum length")
    void nameValidator_shouldBeValid_whenNameMinimumLength() {
        SupplierDTO minDTO = new SupplierDTO(null, "ABC", "test@example.com", "+34912345678");
        ValidationResult result = nameValidator.validate(minDTO);
        assertThat(result.isValid()).isTrue();
    }

    // ─────────────────────────────────────────────────────────────
    // CONTACT VALIDATOR TESTS
    // ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("ContactValidator → valid when email is correct")
    void contactValidator_shouldBeValid_whenEmailCorrect() {
        ValidationResult result = contactValidator.validate(validSupplier);
        assertThat(result.isValid()).isTrue();
    }

    @Test
    @DisplayName("ContactValidator → invalid when email is null")
    void contactValidator_shouldBeInvalid_whenEmailNull() {
        SupplierDTO invalidDTO = new SupplierDTO(null, "Valid", null, "+34912345678");
        ValidationResult result = contactValidator.validate(invalidDTO);

        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).containsIgnoringCase("required");
    }

    @Test
    @DisplayName("ContactValidator → invalid when email is blank")
    void contactValidator_shouldBeInvalid_whenEmailBlank() {
        SupplierDTO invalidDTO = new SupplierDTO(null, "Valid", "   ", "+34912345678");
        ValidationResult result = contactValidator.validate(invalidDTO);

        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).containsIgnoringCase("empty");
    }

    @Test
    @DisplayName("ContactValidator → invalid when email format is wrong")
    void contactValidator_shouldBeInvalid_whenEmailFormatWrong() {
        SupplierDTO invalidDTO = new SupplierDTO(null, "Valid", "not-an-email", "+34912345678");
        ValidationResult result = contactValidator.validate(invalidDTO);

        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).containsIgnoringCase("email");
    }

    @Test
    @DisplayName("ContactValidator → valid with different email formats")
    void contactValidator_shouldBeValid_withVariousEmailFormats() {
        String[] validEmails = {
            "user@domain.com",
            "first.last@domain.co.uk",
            "user123@example.org",
            "test@subdomain.example.com"
        };

        for (String email : validEmails) {
            SupplierDTO dto = new SupplierDTO(null, "Valid", email, "+34912345678");
            ValidationResult result = contactValidator.validate(dto);
            assertThat(result.isValid())
                .as("Email should be valid: " + email)
                .isTrue();
        }
    }

    // ─────────────────────────────────────────────────────────────
    // PHONE VALIDATOR TESTS
    // ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("PhoneValidator → valid when phone is correct")
    void phoneValidator_shouldBeValid_whenPhoneCorrect() {
        ValidationResult result = phoneValidator.validate(validSupplier);
        assertThat(result.isValid()).isTrue();
    }

    @Test
    @DisplayName("PhoneValidator → invalid when phone is null")
    void phoneValidator_shouldBeInvalid_whenPhoneNull() {
        SupplierDTO invalidDTO = new SupplierDTO(null, "Valid", "test@example.com", null);
        ValidationResult result = phoneValidator.validate(invalidDTO);

        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).containsIgnoringCase("required");
    }

    @Test
    @DisplayName("PhoneValidator → invalid when phone is blank")
    void phoneValidator_shouldBeInvalid_whenPhoneBlank() {
        SupplierDTO invalidDTO = new SupplierDTO(null, "Valid", "test@example.com", "   ");
        ValidationResult result = phoneValidator.validate(invalidDTO);

        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).containsIgnoringCase("empty");
    }

    @Test
    @DisplayName("PhoneValidator → invalid when phone has too few digits")
    void phoneValidator_shouldBeInvalid_whenPhoneTooFewDigits() {
        SupplierDTO invalidDTO = new SupplierDTO(null, "Valid", "test@example.com", "123456");
        ValidationResult result = phoneValidator.validate(invalidDTO);

        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).containsIgnoringCase("valid");
    }

    @Test
    @DisplayName("PhoneValidator → invalid when phone contains non-digits")
    void phoneValidator_shouldBeInvalid_whenPhoneContainsNonDigits() {
        SupplierDTO invalidDTO = new SupplierDTO(null, "Valid", "test@example.com", "+34-912-345-678");
        ValidationResult result = phoneValidator.validate(invalidDTO);

        assertThat(result.isValid()).isFalse();
    }

    @Test
    @DisplayName("PhoneValidator → valid with different phone formats")
    void phoneValidator_shouldBeValid_withVariousPhoneFormats() {
        String[] validPhones = {
            "+34912345678",
            "912345678",
            "+1234567890",
            "+33123456789"
        };

        for (String phone : validPhones) {
            SupplierDTO dto = new SupplierDTO(null, "Valid", "test@example.com", phone);
            ValidationResult result = phoneValidator.validate(dto);
            assertThat(result.isValid())
                .as("Phone should be valid: " + phone)
                .isTrue();
        }
    }

    // ─────────────────────────────────────────────────────────────
    // VALIDATOR CHAIN TESTS
    // ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("ValidatorChain → passes validation when all validators pass")
    void validatorChain_shouldPass_whenAllValidatorsPass() {
        // Build chain: Name → Contact → Phone
        nameValidator.setNext(contactValidator);
        contactValidator.setNext(phoneValidator);

        ValidationResult result = nameValidator.validate(validSupplier);

        assertThat(result.isValid()).isTrue();
    }

    @Test
    @DisplayName("ValidatorChain → fails fast at first validator")
    void validatorChain_shouldFailFast_atFirstValidator() {
        // Build chain
        nameValidator.setNext(contactValidator);
        contactValidator.setNext(phoneValidator);

        // Invalid name
        SupplierDTO invalidDTO = new SupplierDTO(null, null, "test@example.com", "+34912345678");
        ValidationResult result = nameValidator.validate(invalidDTO);

        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).containsIgnoringCase("required");
    }

    @Test
    @DisplayName("ValidatorChain → fails at second validator if first passes")
    void validatorChain_shouldTest_atSecondValidator() {
        // Build chain
        nameValidator.setNext(contactValidator);
        contactValidator.setNext(phoneValidator);

        // Invalid email, valid name
        SupplierDTO invalidDTO = new SupplierDTO(null, "Valid Supplier", "not-an-email", "+34912345678");
        ValidationResult result = nameValidator.validate(invalidDTO);

        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).containsIgnoringCase("email");
    }

    @Test
    @DisplayName("ValidatorChain → fails at third validator if first two pass")
    void validatorChain_shouldTest_atThirdValidator() {
        // Build chain
        nameValidator.setNext(contactValidator);
        contactValidator.setNext(phoneValidator);

        // Invalid phone, valid name and email
        SupplierDTO invalidDTO = new SupplierDTO(null, "Valid Supplier", "test@example.com", "123");
        ValidationResult result = nameValidator.validate(invalidDTO);

        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).containsIgnoringCase("valid");
    }

    @Test
    @DisplayName("ValidatorChain → order matters - can customize sequence")
    void validatorChain_shouldRespect_customOrder() {
        // Alternative chain: Phone → Contact → Name (reverse order)
        SupplierValidator phoneFirst = phoneValidator;
        phoneValidator.setNext(contactValidator);
        contactValidator.setNext(nameValidator);

        ValidationResult result = phoneValidator.validate(validSupplier);

        assertThat(result.isValid()).isTrue();
    }
}



