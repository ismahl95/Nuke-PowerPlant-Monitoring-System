package com.ihl95.nuclear.supplier.application.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ihl95.nuclear.supplier.application.dto.SupplierDTO;
import com.ihl95.nuclear.supplier.application.exception.SupplierException;
import com.ihl95.nuclear.supplier.application.mapper.SupplierMapper;
import com.ihl95.nuclear.supplier.domain.Supplier;
import com.ihl95.nuclear.supplier.infraestructure.SupplierRepository;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {

  @Autowired
  SupplierRepository supplierRepository;

  @Autowired
  SupplierMapper supplierMapper;

  public List<SupplierDTO> getAllSuppliers() {

    List<Supplier> suppliers = supplierRepository.findAll();
    return suppliers.stream()
        .map(supplierMapper::toSupplierDTO)
        .collect(Collectors.toList());

  }

  @Override
  public SupplierDTO getSupplierbyId(Long id) {
    Supplier supplier = supplierRepository.findById(id)
        .orElseThrow(() -> SupplierException.notFound(SupplierException.NOT_FOUND_MESSAGE + id));
    return supplierMapper.toSupplierDTO(supplier);
  }

}
