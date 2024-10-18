package com.ihl95.nuclear.supplier.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ihl95.nuclear.supplier.application.dto.SupplierDTO;
import com.ihl95.nuclear.supplier.application.service.SupplierServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

  @Autowired
  SupplierServiceImpl supplierService;

/**
 * This function retrieves all suppliers and returns them as a list of SupplierDTO objects.
 * 
 * @return A ResponseEntity object containing a list of SupplierDTO objects is being returned.
 */
  @Operation(summary = "Obtener todos los proveedores", description = "Devuelve una lista de todos los proveedores registrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Listado de proveedores obtenido con éxito", content = @Content(array = @ArraySchema(schema = @Schema(implementation = SupplierDTO.class)))),
      @ApiResponse(responseCode = "403", description = "No tiene permisos para acceder a este recurso")
  })
  @GetMapping
  public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
    List<SupplierDTO> suppliers = supplierService.getAllSuppliers();
    return ResponseEntity.ok(suppliers);
  }

/**
 * This Java function retrieves a supplier by ID and returns it as a ResponseEntity.
 * 
 * @param id The `id` parameter in the code snippet represents the unique identifier of the supplier or
 * provider that you want to retrieve. This ID is used to look up the specific supplier in the system
 * and return its details in the response.
 * @return The `getSupplierById` method returns a `ResponseEntity` object containing a
 * `SupplierDTO` object. If the supplier is found successfully, it returns a response with status code
 * 200 and the supplier information. If the supplier is not found, it returns a response with status
 * code 404.
 */
  @Operation(summary = "Obtener proveedor por ID", description = "Devuelve un proveedor según su ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Proveedor obtenido con éxito", content = @Content(schema = @Schema(implementation = SupplierDTO.class))),
      @ApiResponse(responseCode = "404", description = "Proveedor no encontrado"),
      @ApiResponse(responseCode = "403", description = "No tiene permisos para acceder a este recurso")
  })
  @GetMapping("/{id}")
  public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable Long id) {
    SupplierDTO supplier = supplierService.getSupplierbyId(id);
    if (supplier == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok(supplier);
  }

}
