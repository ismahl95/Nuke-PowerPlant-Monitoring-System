package com.ihl95.nuclear.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NuclearPlantException.class)
  public ResponseEntity<Object> handleNuclearPlantException(NuclearPlantException ex) {
    return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
  }

}
