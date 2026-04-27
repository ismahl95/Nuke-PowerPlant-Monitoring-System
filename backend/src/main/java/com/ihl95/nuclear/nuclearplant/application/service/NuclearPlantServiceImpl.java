package com.ihl95.nuclear.nuclearplant.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ihl95.nuclear.nuclearplant.application.dto.NuclearPlantDTO;
import com.ihl95.nuclear.nuclearplant.application.exception.NuclearPlantException;
import com.ihl95.nuclear.nuclearplant.application.mapper.NuclearPlantCompleteMapper;
import com.ihl95.nuclear.nuclearplant.application.observer.NuclearPlantObserver;
import com.ihl95.nuclear.nuclearplant.application.validator.NuclearPlantValidator;
import com.ihl95.nuclear.nuclearplant.application.validator.ValidationResult;
import com.ihl95.nuclear.nuclearplant.infraestructure.NuclearPlantRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Qualifier;

@Service
@Transactional
public class NuclearPlantServiceImpl implements NuclearPlantService {

    private NuclearPlantRepository nuclearPlantRepository;

    private NuclearPlantCompleteMapper nuclearPlantCompleteMapper;

    private List<NuclearPlantObserver> observers;

    private NuclearPlantValidator validatorChain;

    private static final Logger logger = LoggerFactory.getLogger(NuclearPlantServiceImpl.class);

    public NuclearPlantServiceImpl(
            NuclearPlantRepository nuclearPlantRepository,
            NuclearPlantCompleteMapper nuclearPlantCompleteMapper,
            List<NuclearPlantObserver> observers,
            @Qualifier("nuclearPlantValidatorChain") NuclearPlantValidator validatorChain) {
        this.nuclearPlantRepository = nuclearPlantRepository;
        this.nuclearPlantCompleteMapper = nuclearPlantCompleteMapper;
        this.observers = observers;
        this.validatorChain = validatorChain;
    }

    @Override
    public List<NuclearPlantDTO> getAllNuclearPlants() {
        logger.info("Fetching all nuclear plants");

        return nuclearPlantRepository.findAll().stream()
        .map(nuclearPlant -> {
            logger.debug("Fetched NuclearPlant with ID: {}", nuclearPlant.getId());
            return nuclearPlantCompleteMapper.toNuclearPlantDTO(nuclearPlant);
        })
        .map(dto -> {
            logger.debug("Mapped NuclearPlant to DTO: {}", dto);
            return dto;
        })
        .toList();
    }

    @Override
    public NuclearPlantDTO getNuclearPlantById(Long id) {
        logger.info("Fetching nuclear plant with ID: {}", id);

        if (id == null) {
            logger.error("The provided ID is null");
            throw NuclearPlantException.badRequest(NuclearPlantException.BAD_REQUEST + id);
        }
        return Optional.of(id)
                .map(nuclearPlantRepository::findById)
                .flatMap(opt -> opt)
                .map(nuclearPlantCompleteMapper::toNuclearPlantDTO)
                .map(dto -> {
                    logger.debug("Mapped NuclearPlant to DTO: {}", dto);
                    return dto;
                })
                .orElseThrow(() -> {
                    logger.error(NuclearPlantException.NOT_FOUND_MESSAGE, id);
                    return NuclearPlantException.notFound(NuclearPlantException.NOT_FOUND_MESSAGE + id);
                });
    }

    @Override
    public NuclearPlantDTO createNuclearPlant(NuclearPlantDTO nuclearPlantDTO) {
        logger.info("Creating new nuclear plant");

        // ── CHAIN OF RESPONSIBILITY: Validate through validator chain ──
        // Validators: Name → Location → Unique
        ValidationResult validationResult = validatorChain.validate(nuclearPlantDTO);
        
        if (!validationResult.isValid()) {
            logger.warn("Validation failed: {}", validationResult.getMessage());
            throw NuclearPlantException.badRequest(validationResult.getMessage());
        }

        return Optional.ofNullable(nuclearPlantDTO)
                .map(nuclearPlantCompleteMapper::toNuclearPlant)
                .map(nuclearPlantRepository::save)
                .map(savedPlant -> {
                    // ── NOTIFY OBSERVERS ──
                    notifyObserversCreated(savedPlant);
                    return nuclearPlantCompleteMapper.toNuclearPlantDTO(savedPlant);
                })
                .map(dto -> {
                    logger.debug("Created NuclearPlant DTO: {}", dto);
                    return dto;
                })
                .orElseThrow(() -> {
                    logger.error("Unexpected error while saving nuclear plant");
                    return NuclearPlantException.internalError(NuclearPlantException.UNEXPECTING_ERROR_WHILE_SAVING);
                });
    }

    @Override
    public NuclearPlantDTO updateNuclearPlant(Long id, NuclearPlantDTO nuclearPlantDTO) {
        logger.info("Updating nuclear plant with ID: {}", id);
        return Optional.of(id)
                .map(nuclearPlantRepository::findById)
                .flatMap(opt -> opt)
                .map(existingPlant -> {
                    existingPlant.setName(nuclearPlantDTO.name());
                    existingPlant.setLocation(nuclearPlantDTO.location());
                    return nuclearPlantRepository.save(existingPlant);
                })
                .map(updatedPlant -> {
                    // ── NOTIFY OBSERVERS ──
                    notifyObserversUpdated(updatedPlant);
                    return nuclearPlantCompleteMapper.toNuclearPlantDTO(updatedPlant);
                })
                .map(dto -> {
                    logger.debug("Updated NuclearPlant DTO: {}", dto);
                    return dto;
                })
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
                .map(nuclearPlantRepository::findById)
                .flatMap(opt -> opt)
                .ifPresentOrElse(nuclearPlant -> {
                    nuclearPlantRepository.delete(nuclearPlant);
                    // ── NOTIFY OBSERVERS ──
                    notifyObserversDeleted(nuclearPlant);
                    logger.debug("Deleted NuclearPlant with ID: {}", id);
                }, () -> {
                    logger.error(NuclearPlantException.NOT_FOUND_MESSAGE, id);
                    throw NuclearPlantException.notFound(NuclearPlantException.NOT_FOUND_MESSAGE + id);
                });
    }

    // ── OBSERVER NOTIFICATION METHODS ──
    /**
     * Notify all registered observers that a plant was created.
     * Observers are called asynchronously to prevent blocking on observer failures.
     */
    private void notifyObserversCreated(com.ihl95.nuclear.nuclearplant.domain.NuclearPlant plant) {
        observers.forEach(observer -> {
            try {
                observer.onNuclearPlantCreated(plant);
            } catch (Exception e) {
                logger.error("Error in observer onNuclearPlantCreated", e);
                // Don't rethrow - observer failure shouldn't break service
            }
        });
    }

    /**
     * Notify all registered observers that a plant was updated.
     */
    private void notifyObserversUpdated(com.ihl95.nuclear.nuclearplant.domain.NuclearPlant plant) {
        observers.forEach(observer -> {
            try {
                observer.onNuclearPlantUpdated(plant);
            } catch (Exception e) {
                logger.error("Error in observer onNuclearPlantUpdated", e);
            }
        });
    }

    /**
     * Notify all registered observers that a plant was deleted.
     */
    private void notifyObserversDeleted(com.ihl95.nuclear.nuclearplant.domain.NuclearPlant plant) {
        observers.forEach(observer -> {
            try {
                observer.onNuclearPlantDeleted(plant);
            } catch (Exception e) {
                logger.error("Error in observer onNuclearPlantDeleted", e);
            }
        });
    }

}
