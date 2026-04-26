package com.ihl95.nuclear.nuclearplant.mapper;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.ihl95.nuclear.common.mocks.NuclearPlantTestData;
import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearplant.application.mapper.NuclearPlantCompleteMapper;
import com.ihl95.nuclear.nuclearplant.domain.NuclearPlant;

/**
 * Unit tests for NuclearPlantCompleteMapper MapStruct mapper.
 * Tests bidirectional mapping between Entity and DTO.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("NuclearPlantCompleteMapper Tests")
class NuclearPlantCompleteMapperTest {

    private NuclearPlantCompleteMapper mapper;

    private NuclearPlant existingEntity;
    private NuclearPlantDTO existingDTO;

    @BeforeEach
    void setUp() {
        // Usar reflexión para instanciar el mapper generado por MapStruct
        try {
            Class<?> mapperImpl = Class.forName("com.ihl95.nuclear.nuclearplant.application.mapper.NuclearPlantCompleteMapperImpl");
            mapper = (NuclearPlantCompleteMapper) mapperImpl.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo instanciar el mapper", e);
        }

        existingEntity = NuclearPlantTestData.createNuclearPlantEntity();
        existingDTO = NuclearPlantTestData.createNuclearPlantDTO();
    }

    // ── TO DTO ────────────────────────────────────────────────────

    @Test
    @DisplayName("toNuclearPlantDTO → mapea entidad a DTO correctamente")
    void toNuclearPlantDTO_shouldMapEntityToDTO() {
        // ARRANGE
        NuclearPlant entity = NuclearPlantTestData.createNuclearPlantEntity(1L, "Test Plant", "Test Location");

        // ACT
        NuclearPlantDTO result = mapper.toNuclearPlantDTO(entity);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Test Plant");
        assertThat(result.location()).isEqualTo("Test Location");
    }

    @Test
    @DisplayName("toNuclearPlantDTO → retorna DTO con valores null cuando entidad es null")
    void toNuclearPlantDTO_shouldHandleNullEntity() {
        // ACT
        NuclearPlantDTO result = mapper.toNuclearPlantDTO(null);

        // ASSERT
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("toNuclearPlantDTO → ignora audit fields de entidad")
    void toNuclearPlantDTO_shouldIgnoreAuditFields() {
        // ARRANGE
        NuclearPlant entity = NuclearPlantTestData.createNuclearPlantEntity();
        ReflectionTestUtils.setField(entity, "createdBy", "admin");
        ReflectionTestUtils.setField(entity, "lastModifiedBy", "system");

        // ACT
        NuclearPlantDTO result = mapper.toNuclearPlantDTO(entity);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo(entity.getName());
        assertThat(result.location()).isEqualTo(entity.getLocation());
    }

    // ── TO ENTITY ────────────────────────────────────────────────────

    @Test
    @DisplayName("toNuclearPlant → mapea DTO a entidad correctamente")
    void toNuclearPlant_shouldMapDTOToEntity() {
        // ARRANGE
        NuclearPlantDTO dto = NuclearPlantTestData.createNuclearPlantDTO(2L, "New Plant", "New Location");

        // ACT
        NuclearPlant result = mapper.toNuclearPlant(dto);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getName()).isEqualTo("New Plant");
        assertThat(result.getLocation()).isEqualTo("New Location");
    }

    @Test
    @DisplayName("toNuclearPlant → retorna null cuando DTO es null")
    void toNuclearPlant_shouldHandleNullDTO() {
        // ACT
        NuclearPlant result = mapper.toNuclearPlant(null);

        // ASSERT
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("toNuclearPlant → ignora audit fields en mapeo")
    void toNuclearPlant_shouldIgnoreAuditFieldsOnMapping() {
        // ARRANGE
        NuclearPlantDTO dto = NuclearPlantTestData.createNuclearPlantDTO();

        // ACT
        NuclearPlant result = mapper.toNuclearPlant(dto);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getCreatedBy()).isNull();
        assertThat(result.getLastModifiedBy()).isNull();
        assertThat(result.getCreatedDate()).isNull();
        assertThat(result.getLastModifiedDate()).isNull();
    }

    // ── ROUNDTRIP ────────────────────────────────────────────────────

    @Test
    @DisplayName("Roundtrip → DTO → Entity → DTO mantiene datos")
    void roundTrip_shouldPreserveData() {
        // ARRANGE
        NuclearPlantDTO originalDTO = NuclearPlantTestData.createNuclearPlantDTO(1L, "Round Trip Plant", "Round Trip Location");

        // ACT
        NuclearPlant entity = mapper.toNuclearPlant(originalDTO);
        NuclearPlantDTO resultDTO = mapper.toNuclearPlantDTO(entity);

        // ASSERT
        assertThat(resultDTO).isNotNull();
        assertThat(resultDTO.name()).isEqualTo(originalDTO.name());
        assertThat(resultDTO.location()).isEqualTo(originalDTO.location());
    }
}

