package com.ihl95.nuclear.nuclearplant.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearplant.application.exception.NuclearPlantException;
import com.ihl95.nuclear.nuclearplant.application.mapper.NuclearPlantCompleteMapper;
import com.ihl95.nuclear.nuclearplant.domain.NuclearPlant;
import com.ihl95.nuclear.nuclearplant.infraestructure.NuclearPlantRepository;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
@Transactional
public class NuclearPlantServiceImpl implements NuclearPlantService{

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
                .orElseThrow(() -> NuclearPlantException.notFound(NuclearPlantException.NOT_FOUND_MESSAGE + id));
        return nuclearPlantCompleteMapper.toNuclearPlantDTO(nuclearPlant);
    }

    public NuclearPlantDTO createNuclearPlant(NuclearPlantDTO nuclearPlantDTO) {
        NuclearPlant nuclearPlant = nuclearPlantCompleteMapper.toNuclearPlant(nuclearPlantDTO);
        NuclearPlant savedNuclearPlant = nuclearPlantRepository.save(nuclearPlant);
        return nuclearPlantCompleteMapper.toNuclearPlantDTO(savedNuclearPlant);
    }

    public NuclearPlantDTO updateNuclearPlant(Long id, NuclearPlantDTO nuclearPlantDTO) {
        // Verificar si la planta existe en la base de datos
        NuclearPlant existingPlant = nuclearPlantRepository.findById(id)
                .orElseThrow(() -> NuclearPlantException.notFound(NuclearPlantException.NOT_FOUND_MESSAGE + id));

        // Actualizar solo los campos simples que están presentes en el DTO
        existingPlant.setName(nuclearPlantDTO.name());
        existingPlant.setLocation(nuclearPlantDTO.location());

        // Guardar la entidad actualizada
        NuclearPlant updatedNuclearPlant = nuclearPlantRepository.save(existingPlant);

        // Retornar el DTO actualizado
        return nuclearPlantCompleteMapper.toNuclearPlantDTO(updatedNuclearPlant);
    }

    @Transactional
    public void deleteNuclearPlant(Long id) {
        // Verificar si la planta existe en la base de datos
        NuclearPlant nuclearPlant = nuclearPlantRepository.findById(id)
                .orElseThrow(() -> NuclearPlantException.notFound(NuclearPlantException.NOT_FOUND_MESSAGE + id));

        // Si necesitas manipular o verificar algo antes de la eliminación, puedes
        // hacerlo aquí

        // Eliminar la planta nuclear, lo que debería también eliminar las entidades
        // relacionadas
        nuclearPlantRepository.delete(nuclearPlant);
    }

}
