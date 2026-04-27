package com.ihl95.nuclear.supplier.application.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ihl95.nuclear.supplier.application.dto.SupplierDTO;
import com.ihl95.nuclear.supplier.application.exception.SupplierException;
import com.ihl95.nuclear.supplier.application.mapper.SupplierMapper;
import com.ihl95.nuclear.supplier.application.observer.SupplierObserver;
import com.ihl95.nuclear.supplier.application.validator.SupplierValidator;
import com.ihl95.nuclear.supplier.application.validator.ValidationResult;
import com.ihl95.nuclear.supplier.domain.Supplier;
import com.ihl95.nuclear.supplier.infraestructure.SupplierRepository;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {

  private SupplierRepository supplierRepository;

  private SupplierMapper supplierMapper;

  private List<SupplierObserver> observers;

  private SupplierValidator validatorChain;

  private static final Logger logger = LoggerFactory.getLogger(SupplierServiceImpl.class);

  public SupplierServiceImpl(
      SupplierRepository supplierRepository,
      SupplierMapper supplierMapper,
      List<SupplierObserver> observers,
      @Qualifier("supplierValidatorChain") SupplierValidator validatorChain) {
    this.supplierRepository = supplierRepository;
    this.supplierMapper = supplierMapper;
    this.observers = observers;
    this.validatorChain = validatorChain;
  }

  public List<SupplierDTO> getAllSuppliers() {

    List<Supplier> suppliers = supplierRepository.findAll();
    return suppliers.stream()
        .map(supplierMapper::toSupplierDTO)
        .toList();

  }

  @Override
  public SupplierDTO getSupplierbyId(Long id) {
    if (id == null) {
      throw SupplierException.badRequest(SupplierException.BAD_REQUEST_MESSAGE + id);
    }
    
    try {
      Optional<Supplier> supplierOptional = supplierRepository.findById(id);
      if (!supplierOptional.isPresent()) {
        throw SupplierException.notFound(SupplierException.NOT_FOUND_MESSAGE + id);
      }
      return supplierMapper.toSupplierDTO(supplierOptional.get());
    } catch (SupplierException e) {
      throw e; // Re-lanzamos las excepciones de negocio sin modificar
    } catch (RuntimeException e) {
      throw SupplierException.internalError("Error retrieving supplier");
    }
  }

  @Override
  public SupplierDTO createSupplier(SupplierDTO supplierDTO) {
    logger.info("Creating new supplier");

    // ── CHAIN OF RESPONSIBILITY: Validate through validator chain ──
    // Validators: Name → Contact → Phone
    ValidationResult validationResult = validatorChain.validate(supplierDTO);

    if (!validationResult.isValid()) {
      logger.warn("Validation failed: {}", validationResult.getMessage());
      throw SupplierException.badRequest(validationResult.getMessage());
    }

    return Optional.ofNullable(supplierDTO)
      .map(supplierMapper::toSupplier)
      .map(supplierRepository::save)
      .map(savedSupplier -> {
        // ── NOTIFY OBSERVERS ──
        notifyObserversCreated(savedSupplier);
        return supplierMapper.toSupplierDTO(savedSupplier);
      })
      .orElseThrow(() -> SupplierException.internalError(SupplierException.UNEXPECTING_ERROR_WHILE_SAVING));
  }

  @Override
  public SupplierDTO updateSupplier(Long id, SupplierDTO supplierDTO) {
    logger.info("Updating supplier with ID: {}", id);

    return Optional.ofNullable(id)
      .flatMap(supplierRepository::findById)
      .map(existingSupplier -> {
        Supplier updatedSupplier = supplierMapper.toSupplier(supplierDTO);
        updatedSupplier.setId(existingSupplier.getId());
        return supplierRepository.save(updatedSupplier);
      })
      .map(savedSupplier -> {
        // ── NOTIFY OBSERVERS ──
        notifyObserversUpdated(savedSupplier);
        return supplierMapper.toSupplierDTO(savedSupplier);
      })
      .orElseThrow(() -> SupplierException.notFound(SupplierException.NOT_FOUND_MESSAGE + id));
  }

  @Override
  @Transactional
  public void deleteSupplier(Long id) {
    logger.info("Deleting supplier with ID: {}", id);

    Optional.ofNullable(id)
      .flatMap(supplierRepository::findById)
      .ifPresentOrElse(supplier -> {
        supplierRepository.delete(supplier);
        // ── NOTIFY OBSERVERS ──
        notifyObserversDeleted(supplier);
        logger.debug("Deleted Supplier with ID: {}", id);
      }, () -> {
        logger.error(SupplierException.NOT_FOUND_MESSAGE, id);
        throw SupplierException.notFound(SupplierException.NOT_FOUND_MESSAGE + id);
      });
  }

  // ── OBSERVER NOTIFICATION METHODS ──
  /**
   * Notify all registered observers that a supplier was created.
   * Observers are called independently to prevent blocking on observer failures.
   */
  private void notifyObserversCreated(Supplier supplier) {
    observers.forEach(observer -> {
      try {
        observer.onSupplierCreated(supplier);
      } catch (Exception e) {
        logger.error("Error in observer onSupplierCreated", e);
        // Don't rethrow - observer failure shouldn't break service
      }
    });
  }

  /**
   * Notify all registered observers that a supplier was updated.
   */
  private void notifyObserversUpdated(Supplier supplier) {
    observers.forEach(observer -> {
      try {
        observer.onSupplierUpdated(supplier);
      } catch (Exception e) {
        logger.error("Error in observer onSupplierUpdated", e);
      }
    });
  }

  /**
   * Notify all registered observers that a supplier was deleted.
   */
  private void notifyObserversDeleted(Supplier supplier) {
    observers.forEach(observer -> {
      try {
        observer.onSupplierDeleted(supplier);
      } catch (Exception e) {
        logger.error("Error in observer onSupplierDeleted", e);
      }
    });
  }

}
