package com.ihl95.nuclear.supplier.application.exception;

import org.springframework.http.HttpStatus;

public class SupplierException extends RuntimeException{

    private final HttpStatus status;

  public static final String NOT_FOUND_MESSAGE = "Supplier not found with id: ";

  public static final String BAD_REQUEST_MESSAGE = "The provided ID is not valid or null: ";

  public static final String UNEXPECTING_ERROR_WHILE_SAVING = "An unexpected error occurred while saving Nuclear Plant";

  public SupplierException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public static SupplierException notFound(String message) {
    return new SupplierException(message, HttpStatus.NOT_FOUND);
  }

  public static SupplierException badRequest(String message) {
    return new SupplierException(message, HttpStatus.BAD_REQUEST);
  }

  public static SupplierException internalError(String messsage){
    return new SupplierException(messsage, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
}
