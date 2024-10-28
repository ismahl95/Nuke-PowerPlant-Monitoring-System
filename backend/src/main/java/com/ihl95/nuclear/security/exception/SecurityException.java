package com.ihl95.nuclear.security.exception;

import org.springframework.http.HttpStatus;

public class SecurityException extends RuntimeException {

  private final HttpStatus status;

  public static final String UNAUTHORIZED_MESSAGE = "Incorrect username or password";

  public SecurityException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public static SecurityException notAcceptable(String message) {
    return new SecurityException(message, HttpStatus.UNAUTHORIZED);
  }

}
