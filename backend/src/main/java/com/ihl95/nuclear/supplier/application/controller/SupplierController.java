package com.ihl95.nuclear.supplier.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ihl95.nuclear.supplier.application.dto.SupplierDTO;
import com.ihl95.nuclear.supplier.application.service.SupplierServiceImpl;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

  @Autowired
  SupplierServiceImpl supplierService;

  @GetMapping
  public ResponseEntity<List<SupplierDTO>> getAllNuclearPlants() {
    List<SupplierDTO> suppliers = supplierService.getAllSuppliers();
    return ResponseEntity.ok(suppliers);
  }

}
