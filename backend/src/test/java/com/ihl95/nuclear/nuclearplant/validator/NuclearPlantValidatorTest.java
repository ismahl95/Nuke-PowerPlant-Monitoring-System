package com.ihl95.nuclear.nuclearplant.validator;

import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearplant.application.validator.NameValidator;
import com.ihl95.nuclear.nuclearplant.application.validator.LocationValidator;
import com.ihl95.nuclear.nuclearplant.application.validator.UniquePlantValidator;
import com.ihl95.nuclear.nuclearplant.application.validator.ValidationResult;
import com.ihl95.nuclear.nuclearplant.infraestructure.NuclearPlantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Tests unitarios para validadores individuales.
 * Testing Chain of Responsibility pattern.
 */
@DisplayName("NuclearPlant Validators - Unit Tests")
class NuclearPlantValidatorTest {

    private NameValidator nameValidator;
    private LocationValidator locationValidator;
    private UniquePlantValidator uniqueValidator;

    @Mock
    private NuclearPlantRepository repositoryMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        nameValidator = new NameValidator();
        locationValidator = new LocationValidator();
        uniqueValidator = new UniquePlantValidator(repositoryMock);
    }

    @Test
    @DisplayName("NameValidator: Should pass with valid name")
    void nameValidator_validName() {
        NuclearPlantDTO dto = NuclearPlantDTO.builder()
            .name("Almaraz")
            .location("Almaraz, Caceres")
            .build();
        
        ValidationResult result = nameValidator.validate(dto);
        
        assertThat(result.isValid()).isTrue();
        assertThat(result.getMessage()).contains("Validation passed");
    }

    @Test
    @DisplayName("NameValidator: Should fail with null name")
    void nameValidator_nullName() {
        NuclearPlantDTO dto = NuclearPlantDTO.builder()
            .name(null)
            .location("Almaraz, Caceres")
            .build();
        
        ValidationResult result = nameValidator.validate(dto);
        
        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).contains("required");
    }

    @Test
    @DisplayName("NameValidator: Should fail with blank name")
    void nameValidator_blankName() {
        NuclearPlantDTO dto = NuclearPlantDTO.builder()
            .name("   ")
            .location("Almaraz, Caceres")
            .build();
        
        ValidationResult result = nameValidator.validate(dto);
        
        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).contains("empty");
    }

    @Test
    @DisplayName("NameValidator: Should fail with name too short")
    void nameValidator_shortName() {
        NuclearPlantDTO dto = NuclearPlantDTO.builder()
            .name("AB")
            .location("Almaraz, Caceres")
            .build();
        
        ValidationResult result = nameValidator.validate(dto);
        
        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).contains("at least");
    }

    @Test
    @DisplayName("NameValidator: Should fail with name too long")
    void nameValidator_longName() {
        String longName = "A".repeat(256);
        NuclearPlantDTO dto = NuclearPlantDTO.builder()
            .name(longName)
            .location("Almaraz, Caceres")
            .build();
        
        ValidationResult result = nameValidator.validate(dto);
        
        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).contains("not exceed");
    }

    // ── LOCATION VALIDATOR TESTS ──
    
    @Test
    @DisplayName("LocationValidator: Should pass with valid location")
    void locationValidator_validLocation() {
        NuclearPlantDTO dto = NuclearPlantDTO.builder()
            .name("Almaraz")
            .location("Almaraz, Caceres")
            .build();
        
        ValidationResult result = locationValidator.validate(dto);
        
        assertThat(result.isValid()).isTrue();
    }

    @Test
    @DisplayName("LocationValidator: Should fail with null location")
    void locationValidator_nullLocation() {
        NuclearPlantDTO dto = NuclearPlantDTO.builder()
            .name("Almaraz")
            .location(null)
            .build();
        
        ValidationResult result = locationValidator.validate(dto);
        
        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).contains("required");
    }

    @Test
    @DisplayName("LocationValidator: Should fail with blank location")
    void locationValidator_blankLocation() {
        NuclearPlantDTO dto = NuclearPlantDTO.builder()
            .name("Almaraz")
            .location("   ")
            .build();
        
        ValidationResult result = locationValidator.validate(dto);
        
        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).contains("empty");
    }

    @Test
    @DisplayName("LocationValidator: Should fail with location too short")
    void locationValidator_shortLocation() {
        NuclearPlantDTO dto = NuclearPlantDTO.builder()
            .name("Almaraz")
            .location("ABC")
            .build();
        
        ValidationResult result = locationValidator.validate(dto);
        
        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).contains("at least");
    }

    // ── CHAIN OF RESPONSIBILITY TESTS ──

    @Test
    @DisplayName("ValidatorChain: Should stop at first error (fail-fast)")
    void validatorChain_stopAtFirstError() {
        // Chain: Name → Location
        nameValidator.setNext(locationValidator);

        // Invalid name should not check location
        NuclearPlantDTO dto = NuclearPlantDTO.builder()
            .name(null)  // Invalid
            .location("Valid Location That Is Long") // Valid
            .build();

        ValidationResult result = nameValidator.validate(dto);

        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).contains("name");
    }

    @Test
    @DisplayName("ValidatorChain: Should pass through all validators")
    void validatorChain_passAll() {
        // Chain: Name → Location
        nameValidator.setNext(locationValidator);

        NuclearPlantDTO dto = NuclearPlantDTO.builder()
            .name("Almaraz")
            .location("Almaraz, Caceres")
            .build();

        ValidationResult result = nameValidator.validate(dto);

        assertThat(result.isValid()).isTrue();
    }

    @Test
    @DisplayName("ValidatorChain: Should fail at second validator")
    void validatorChain_failAtSecond() {
        nameValidator.setNext(locationValidator);

        NuclearPlantDTO dto = NuclearPlantDTO.builder()
            .name("ValidName")  // Valid
            .location("ABC")    // Invalid - too short
            .build();

        ValidationResult result = nameValidator.validate(dto);

        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).contains("location");
    }

    @Test
    @DisplayName("ValidatorChain: Three-step chain complete")
    void validatorChain_threeStepChain() {
        when(repositoryMock.findAll()).thenReturn(java.util.List.of());

        // Chain: Name → Location → Unique
        nameValidator.setNext(locationValidator);
        locationValidator.setNext(uniqueValidator);

        NuclearPlantDTO dto = NuclearPlantDTO.builder()
            .name("Almaraz")
            .location("Almaraz, Caceres")
            .build();

        ValidationResult result = nameValidator.validate(dto);

        assertThat(result.isValid()).isTrue();
    }
}


