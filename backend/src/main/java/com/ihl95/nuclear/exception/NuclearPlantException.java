package com.ihl95.nuclear.exception;

import org.springframework.http.HttpStatus;

public class NuclearPlantException extends RuntimeException {

  private final HttpStatus status;

  public NuclearPlantException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public static NuclearPlantException notFound(String message) {
    return new NuclearPlantException(message, HttpStatus.NOT_FOUND);
  }

  public static NuclearPlantException badRequest(String message) {
    return new NuclearPlantException(message, HttpStatus.BAD_REQUEST);
  }

  public static NuclearPlantException conflict(String message) {
    return new NuclearPlantException(message, HttpStatus.CONFLICT);
  }
}
