package com.ihl95.nuclear.incident.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ihl95.nuclear.incident.application.dto.IncidentDTO;
import com.ihl95.nuclear.incident.application.mapper.IncidentMapper;
import com.ihl95.nuclear.incident.application.repository.IncidentRepository;
import com.ihl95.nuclear.incident.application.service.IncidentServiceImpl;
import com.ihl95.nuclear.incident.domain.Incident;
import com.ihl95.nuclear.incident.domain.enums.IncidentSeverity;

@ExtendWith(MockitoExtension.class)
class IncidentServiceImplTest {

    @Mock
    private IncidentRepository incidentRepository;

    @Mock
    private IncidentMapper incidentMapper;

    @InjectMocks
    private IncidentServiceImpl incidentService;

    private Incident testIncident;
    private IncidentDTO testIncidentDTO;

    @BeforeEach
    void setUp() {
        testIncident = new Incident();
        testIncident.setId(1L);
        testIncident.setDescription("Test Incident");
        testIncident.setDate(java.time.LocalDateTime.now());
        testIncident.setSeverity(IncidentSeverity.CRITICAL);

        testIncidentDTO = new IncidentDTO(
            1L,
            "Test Incident",
            java.time.LocalDateTime.now(),
            IncidentSeverity.CRITICAL
        );
    }

    @Test
    void testGetAllIncidents() {
        List<Incident> incidents = Arrays.asList(testIncident);
        List<IncidentDTO> dtos = Arrays.asList(testIncidentDTO);

        when(incidentRepository.findAll()).thenReturn(incidents);
        when(incidentMapper.toIncidentDTO(testIncident)).thenReturn(testIncidentDTO);

        List<IncidentDTO> result = incidentService.getAllIncidents();

        assertEquals(1, result.size());
        verify(incidentRepository, times(1)).findAll();
    }

    @Test
    void testGetIncidentById_Success() {
        when(incidentRepository.findById(1L)).thenReturn(Optional.of(testIncident));
        when(incidentMapper.toIncidentDTO(testIncident)).thenReturn(testIncidentDTO);

        IncidentDTO result = incidentService.getIncidentById(1L);

        assertNotNull(result);
        verify(incidentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetIncidentById_NotFound() {
        when(incidentRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> incidentService.getIncidentById(1L)
        );
        assertEquals("Incident not found with id: 1", exception.getMessage());
    }

    @Test
    void testGetIncidentById_NullId() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> incidentService.getIncidentById(null)
        );
        assertEquals("Incident ID cannot be null", exception.getMessage());
    }

    @Test
    void testCreateIncident_Success() {
        when(incidentMapper.toIncident(testIncidentDTO)).thenReturn(testIncident);
        when(incidentRepository.save(testIncident)).thenReturn(testIncident);
        when(incidentMapper.toIncidentDTO(testIncident)).thenReturn(testIncidentDTO);

        IncidentDTO result = incidentService.createIncident(testIncidentDTO);

        assertNotNull(result);
        verify(incidentRepository, times(1)).save(testIncident);
    }

    @Test
    void testCreateIncident_NullDTO() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> incidentService.createIncident(null)
        );
        assertEquals("Incident data cannot be null", exception.getMessage());
    }

    @Test
    void testUpdateIncident_Success() {
        when(incidentRepository.findById(1L)).thenReturn(Optional.of(testIncident));
        when(incidentMapper.toIncident(testIncidentDTO)).thenReturn(testIncident);
        when(incidentRepository.save(any(Incident.class))).thenReturn(testIncident);
        when(incidentMapper.toIncidentDTO(testIncident)).thenReturn(testIncidentDTO);

        IncidentDTO result = incidentService.updateIncident(1L, testIncidentDTO);

        assertNotNull(result);
        verify(incidentRepository, times(1)).save(any(Incident.class));
    }

    @Test
    void testUpdateIncident_NotFound() {
        when(incidentRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> incidentService.updateIncident(1L, testIncidentDTO)
        );
        assertEquals("Incident not found with id: 1", exception.getMessage());
    }

    @Test
    void testUpdateIncident_NullId() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> incidentService.updateIncident(null, testIncidentDTO)
        );
        assertEquals("Incident ID cannot be null", exception.getMessage());
    }

    @Test
    void testUpdateIncident_NullDTO() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> incidentService.updateIncident(1L, null)
        );
        assertEquals("Incident data cannot be null", exception.getMessage());
    }

    @Test
    void testDeleteIncident_Success() {
        when(incidentRepository.findById(1L)).thenReturn(Optional.of(testIncident));

        incidentService.deleteIncident(1L);

        verify(incidentRepository, times(1)).delete(testIncident);
    }

    @Test
    void testDeleteIncident_NotFound() {
        when(incidentRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> incidentService.deleteIncident(1L)
        );
        assertEquals("Incident not found with id: 1", exception.getMessage());
    }

    @Test
    void testDeleteIncident_NullId() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> incidentService.deleteIncident(null)
        );
        assertEquals("Incident ID cannot be null", exception.getMessage());
    }
}


