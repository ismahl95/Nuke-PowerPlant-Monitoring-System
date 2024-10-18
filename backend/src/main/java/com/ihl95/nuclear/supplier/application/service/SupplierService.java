package com.ihl95.nuclear.supplier.application.service;

import java.util.List;

import com.ihl95.nuclear.supplier.application.dto.SupplierDTO;

public interface SupplierService {

  public List<SupplierDTO> getAllSuppliers();

  public SupplierDTO getSupplierbyId(Long id);
  
}
