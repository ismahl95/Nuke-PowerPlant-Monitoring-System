package com.ihl95.nuclear.nuclearplant.application.exception;

import org.springframework.http.HttpStatus;

public class NuclearPlantException extends RuntimeException {

  private final HttpStatus status;

  public static final String NOT_FOUND_MESSAGE = "Nuclear Plant not found with id: ";

  public static final String BAD_REQUEST = "The provided ID is not valid or null: ";

  public static final String UNEXPECTING_ERROR_WHILE_SAVING = "An unexpected error occurred while saving Nuclear Plant";

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

  public static NuclearPlantException internalError(String message) {
    return new NuclearPlantException(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
