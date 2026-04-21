package com.ihl95.nuclear.incident.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ihl95.nuclear.incident.application.dto.IncidentDTO;
import com.ihl95.nuclear.incident.application.mapper.IncidentMapper;
import com.ihl95.nuclear.incident.domain.Incident;
import com.ihl95.nuclear.incident.domain.enums.IncidentSeverity;

@SpringBootTest
class IncidentMapperTest {

    @Autowired
    private IncidentMapper incidentMapper;

    private Incident incident;
    private IncidentDTO incidentDTO;

    @BeforeEach
    void setUp() {
        incident = new Incident();
        incident.setId(1L);
        incident.setDescription("Test Incident");
        incident.setDate(java.time.LocalDateTime.now());
        incident.setSeverity(IncidentSeverity.CRITICAL);

        incidentDTO = new IncidentDTO(
            1L,
            "Test Incident",
            java.time.LocalDateTime.now(),
            IncidentSeverity.CRITICAL
        );
    }

    @Test
    void testToIncidentDTO() {
        IncidentDTO result = incidentMapper.toIncidentDTO(incident);
        assertNotNull(result);
        assertEquals(incident.getId(), result.id());
    }

    @Test
    void testToIncident() {
        Incident result = incidentMapper.toIncident(incidentDTO);
        assertNotNull(result);
    }

    @Test
    void testToIncidentDTOWithNull() {
        IncidentDTO result = incidentMapper.toIncidentDTO(null);
        assertNull(result);
    }

    @Test
    void testToIncidentWithNull() {
        Incident result = incidentMapper.toIncident(null);
        assertNull(result);
    }
}


