package com.ihl95.nuclear.supplier.application.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ihl95.nuclear.supplier.application.dto.SupplierDTO;
import com.ihl95.nuclear.supplier.application.exception.SupplierException;
import com.ihl95.nuclear.supplier.application.mapper.SupplierMapper;
import com.ihl95.nuclear.supplier.domain.Supplier;
import com.ihl95.nuclear.supplier.infraestructure.SupplierRepository;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {

  SupplierRepository supplierRepository;

  SupplierMapper supplierMapper;

  public SupplierServiceImpl(
      SupplierRepository supplierRepository,
      SupplierMapper supplierMapper) {
    this.supplierRepository = supplierRepository;
    this.supplierMapper = supplierMapper;
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
    return Optional.ofNullable(supplierDTO)
      .map(supplierMapper::toSupplier)
      .map(supplierRepository::save)
      .map(supplierMapper::toSupplierDTO)
      .orElseThrow(() -> SupplierException.internalError(SupplierException.UNEXPECTING_ERROR_WHILE_SAVING));
  }

  @Override
  public SupplierDTO updateSupplier(Long id, SupplierDTO supplierDTO) {
    return Optional.ofNullable(id)
      .flatMap(supplierRepository::findById)
      .map(existingSupplier -> {
        Supplier updatedSupplier = supplierMapper.toSupplier(supplierDTO);
        updatedSupplier.setId(existingSupplier.getId());
        return supplierRepository.save(updatedSupplier);
      })
      .map(supplierMapper::toSupplierDTO)
      .orElseThrow(() -> SupplierException.notFound(SupplierException.NOT_FOUND_MESSAGE + id));
  }

  @Override
  public void deleteSupplier(Long id) {
    Optional.ofNullable(id)
      .flatMap(supplierRepository::findById)
      .ifPresentOrElse(
        supplierRepository::delete,
        () -> { throw SupplierException.notFound(SupplierException.NOT_FOUND_MESSAGE + id); }
      );
  }

}
