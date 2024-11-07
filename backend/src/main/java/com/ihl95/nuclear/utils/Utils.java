package com.ihl95.nuclear.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

  private Utils() {
    throw new IllegalStateException("Utility class");
  }

  public static ResponseEntity<Object> validateFields(BindingResult result) {
    if (result.hasErrors()) {
      Map<String, String> errores = result.getFieldErrors().stream()
          .collect(Collectors.toMap(
              FieldError::getField,
              FieldError::getDefaultMessage));
      return ResponseEntity.badRequest().body(errores);
    }
    return null; // No errors
  }
}
