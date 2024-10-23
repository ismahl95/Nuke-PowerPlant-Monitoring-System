package com.ihl95.nuclear.supplier.service;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ihl95.nuclear.supplier.infraestructure.SupplierRepository;
import com.ihl95.nuclear.supplier.application.dto.SupplierDTO;
import com.ihl95.nuclear.supplier.application.mapper.SupplierMapper;
import com.ihl95.nuclear.supplier.domain.Supplier;

public class SupplierServiceTestMocks {

  @Mock
  protected SupplierRepository supplierRepository;

  @Mock
  protected SupplierMapper supplierMapper;

  protected Supplier supplier;
  protected SupplierDTO supplierDTO;

  public SupplierServiceTestMocks() {
    MockitoAnnotations.openMocks(this);
    setUpTestEntities();
  }

  private void setUpTestEntities() {
    supplier = Supplier.builder()
      .id(1L)
      .name("Supplier 1")
      .contact("Contact 1")
      .phone("+1-111-111-1111")
      .build();

    supplierDTO = SupplierDTO.builder()
      .id(1L)
      .name("Supplier 1")
      .contact("Contact 1")
      .phone("+1-111-111-1111")
      .build();
    
  }
  
}
