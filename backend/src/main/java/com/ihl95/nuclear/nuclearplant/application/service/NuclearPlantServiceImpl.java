package com.ihl95.nuclear.nuclearplant.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearplant.application.exception.NuclearPlantException;
import com.ihl95.nuclear.nuclearplant.application.mapper.NuclearPlantCompleteMapper;
import com.ihl95.nuclear.nuclearplant.infraestructure.NuclearPlantRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
@Transactional
public class NuclearPlantServiceImpl implements NuclearPlantService {

    private NuclearPlantRepository nuclearPlantRepository;

    private NuclearPlantCompleteMapper nuclearPlantCompleteMapper;

    private static final Logger logger = LoggerFactory.getLogger(NuclearPlantServiceImpl.class);

    public NuclearPlantServiceImpl(
            NuclearPlantRepository nuclearPlantRepository,
            NuclearPlantCompleteMapper nuclearPlantCompleteMapper) {
        this.nuclearPlantRepository = nuclearPlantRepository;
        this.nuclearPlantCompleteMapper = nuclearPlantCompleteMapper;
    }

    @Override
    public List<NuclearPlantDTO> getAllNuclearPlants() {
        logger.info("Fetching all nuclear plants");

        return nuclearPlantRepository.findAll().stream()
        .map(nuclearPlant -> {
            logger.debug("Fetched NuclearPlant with ID: {}", nuclearPlant.getId()); // Log antes de mapear
            return nuclearPlantCompleteMapper.toNuclearPlantDTO(nuclearPlant); // Convertir a DTO
        })
        .map(dto -> {
            logger.debug("Mapped NuclearPlant to DTO: {}", dto); // Log después de mapear
            return dto; // Devolver el DTO mapeado
        })
        .toList(); // Recogerlas en una lista
    }

    @Override
    public NuclearPlantDTO getNuclearPlantById(Long id) {
        logger.info("Fetching nuclear plant with ID: {}", id);

        if (id == null) {
            logger.error("The provided ID is null");
            throw NuclearPlantException.badRequest(NuclearPlantException.BAD_REQUEST + id);
        }
        // Utilizamos Optional para el manejo funcional
        return Optional.of(id)
                .map(nuclearPlantRepository::findById) // Buscamos la planta
                .flatMap(opt -> opt) // Recuperamos la planta si está presente
                .map(nuclearPlantCompleteMapper::toNuclearPlantDTO) // Mapeamos a DTO
                .map(dto -> {
                    logger.debug("Mapped NuclearPlant to DTO: {}", dto);
                    return dto;
                }) // Log para traza
                .orElseThrow(() -> {
                    logger.error(NuclearPlantException.NOT_FOUND_MESSAGE, id);
                    return NuclearPlantException.notFound(NuclearPlantException.NOT_FOUND_MESSAGE + id);
                });
    }

    @Override
    public NuclearPlantDTO createNuclearPlant(NuclearPlantDTO nuclearPlantDTO) {
        logger.info("Creating new nuclear plant");

        return Optional.ofNullable(nuclearPlantDTO)
                .map(nuclearPlantCompleteMapper::toNuclearPlant) // Convertimos DTO a entidad
                .map(nuclearPlantRepository::save) // Guardamos la planta
                .map(nuclearPlantCompleteMapper::toNuclearPlantDTO) // Convertimos la entidad de nuevo a DTO
                .map(dto -> {
                    logger.debug("Created NuclearPlant DTO: {}", dto);
                    return dto;
                }) // Log para traza
                .orElseThrow(() -> {
                    logger.error("Unexpected error while saving nuclear plant");
                    return NuclearPlantException.internalError(NuclearPlantException.UNEXPECTING_ERROR_WHILE_SAVING);
                });
    }

    @Override
    public NuclearPlantDTO updateNuclearPlant(Long id, NuclearPlantDTO nuclearPlantDTO) {
        logger.info("Updating nuclear plant with ID: {}", id);
        // Usamos Optional y map para hacer el proceso funcional
        return Optional.of(id)
                .map(nuclearPlantRepository::findById) // Buscamos la planta
                .flatMap(opt -> opt) // Convertimos el Optional en valor si existe
                .map(existingPlant -> {
                    existingPlant.setName(nuclearPlantDTO.name()); // Actualizamos el nombre
                    existingPlant.setLocation(nuclearPlantDTO.location()); // Actualizamos la ubicación
                    return nuclearPlantRepository.save(existingPlant); // Guardamos la planta
                })
                .map(nuclearPlantCompleteMapper::toNuclearPlantDTO) // Convertimos la planta guardada en DTO
                .map(dto -> {
                    logger.debug("Updated NuclearPlant DTO: {}", dto);
                    return dto;
                }) // Log para traza
                .orElseThrow(() -> {
                    logger.error(NuclearPlantException.NOT_FOUND_MESSAGE, id);
                    return NuclearPlantException.notFound(NuclearPlantException.NOT_FOUND_MESSAGE + id);
                });
    }

    @Override
    @Transactional
    public void deleteNuclearPlant(Long id) {
        logger.info("Deleting nuclear plant with ID: {}", id);

        Optional.of(id)
                .map(nuclearPlantRepository::findById) // Buscamos la planta por ID
                .flatMap(opt -> opt) // Recuperamos la planta si existe
                .ifPresentOrElse(nuclearPlant -> {
                    nuclearPlantRepository.delete(nuclearPlant); // Eliminamos la planta
                    logger.debug("Deleted NuclearPlant with ID: {}", id); // Log para traza
                }, () -> {
                    logger.error(NuclearPlantException.NOT_FOUND_MESSAGE, id);
                    throw NuclearPlantException.notFound(NuclearPlantException.NOT_FOUND_MESSAGE + id);
                });
    }

}
