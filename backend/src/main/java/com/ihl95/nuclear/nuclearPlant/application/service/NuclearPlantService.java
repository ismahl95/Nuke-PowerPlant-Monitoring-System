package com.ihl95.nuclear.nuclearPlant.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihl95.nuclear.nuclearPlant.application.dto.NuclearPlantCompleteDTO;
import com.ihl95.nuclear.nuclearPlant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearPlant.application.exception.NuclearPlantException;
import com.ihl95.nuclear.nuclearPlant.application.mapper.NuclearPlantCompleteMapper;
import com.ihl95.nuclear.nuclearPlant.domain.NuclearPlant;
import com.ihl95.nuclear.nuclearPlant.infraestructure.NuclearPlantRepository;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
@Transactional
public class NuclearPlantService {

    @Autowired
    private NuclearPlantRepository nuclearPlantRepository;

    @Autowired
    private NuclearPlantCompleteMapper nuclearPlantCompleteMapper;

    public List<NuclearPlantDTO> getAllNuclearPlants() {
        List<NuclearPlant> nuclearPlants = nuclearPlantRepository.findAll();
        return nuclearPlants.stream()
                .map(nuclearPlantCompleteMapper::toNuclearPlantDTO)
                .collect(Collectors.toList());
    }

    public NuclearPlantDTO getNuclearPlantById(Long id) {
        NuclearPlant nuclearPlant = nuclearPlantRepository.findById(id)
                .orElseThrow(() -> NuclearPlantException.notFound("Nuclear Plant not found with id: " + id));
        return nuclearPlantCompleteMapper.toNuclearPlantDTO(nuclearPlant);
    }

    public NuclearPlantCompleteDTO getNuclearPlantCompleteById(Long id) {
        NuclearPlant nuclearPlant = nuclearPlantRepository.findById(id)
                .orElseThrow(() -> NuclearPlantException.notFound("Nuclear Plant not found with id: " + id));
        return nuclearPlantCompleteMapper.toNuclearPlantCompleteDTO(nuclearPlant);
    }

    /*
     * public NuclearPlantDTO createNuclearPlant(NuclearPlantDTO nuclearPlantDTO) {
     * NuclearPlant nuclearPlant =
     * nuclearPlantMapper.toNuclearPlant(nuclearPlantDTO);
     * NuclearPlant savedNuclearPlant = nuclearPlantRepository.save(nuclearPlant);
     * return nuclearPlantMapper.toNuclearPlantDTO(savedNuclearPlant);
     * }
     * 
     * public NuclearPlantDTO updateNuclearPlant(Long id, NuclearPlantDTO
     * nuclearPlantDTO) {
     * if (!nuclearPlantRepository.existsById(id)) {
     * throw NuclearPlantException.notFound("Nuclear Plant not found with id: " +
     * id);
     * }
     * NuclearPlant nuclearPlant =
     * nuclearPlantMapper.toNuclearPlant(nuclearPlantDTO);
     * nuclearPlant.setId(id);
     * NuclearPlant updatedNuclearPlant = nuclearPlantRepository.save(nuclearPlant);
     * return nuclearPlantMapper.toNuclearPlantDTO(updatedNuclearPlant);
     * }
     * 
     * public void deleteNuclearPlant(Long id) {
     * if (!nuclearPlantRepository.existsById(id)) {
     * throw NuclearPlantException.notFound("Nuclear Plant not found with id: " +
     * id);
     * }
     * nuclearPlantRepository.deleteById(id);
     * }
     */
}
