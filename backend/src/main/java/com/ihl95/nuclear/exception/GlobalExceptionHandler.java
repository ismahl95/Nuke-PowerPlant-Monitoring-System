package com.ihl95.nuclear.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ihl95.nuclear.nuclearPlant.application.exception.NuclearPlantException;
import com.ihl95.nuclear.supplier.application.exception.SupplierException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NuclearPlantException.class)
  public ResponseEntity<Object> handleNuclearPlantException(NuclearPlantException ex) {
    return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
  }

  @ExceptionHandler(SupplierException.class)
  public ResponseEntity<Object> handleSupplierException(SupplierException ex) {
    return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
  }

}
