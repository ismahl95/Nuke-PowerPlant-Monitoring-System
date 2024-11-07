package com.ihl95.nuclear.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

class UtilsUnitTest {

  @Test
  void testValidateFields_WithErrors() {
    // Mock BindingResult
    BindingResult mockBindingResult = mock(BindingResult.class);

    // Simulate validation errors in NuclearPlantDTO
    FieldError nameError = new FieldError("nuclearPlantDTO", "name", "El nombre es obligatorio");
    FieldError locationError = new FieldError("nuclearPlantDTO", "location", "La localización es obligatoria");

    when(mockBindingResult.hasErrors()).thenReturn(true);
    when(mockBindingResult.getFieldErrors()).thenReturn(List.of(nameError, locationError));

    // Call the method
    ResponseEntity<Object> response = Utils.validateFields(mockBindingResult);

    // Assert the response
    assertNotNull(response);
    assertEquals(400, response.getStatusCodeValue());

    // Verify body content
    Object responseBody = response.getBody();
    assertNotNull(responseBody);

    // Safe cast using instanceof to avoid unchecked cast warning
    if (responseBody instanceof Map<?, ?>) {
      @SuppressWarnings("unchecked")
      Map<String, String> body = (Map<String, String>) responseBody;
      assertEquals("El nombre es obligatorio", body.get("name"));
      assertEquals("La localización es obligatoria", body.get("location"));
    } else {
      fail("Expected response body to be of type Map<String, String>");
    }
  }

  @Test
  void testValidateFields_NoErrors() {
    // Mock BindingResult
    BindingResult mockBindingResult = mock(BindingResult.class);

    when(mockBindingResult.hasErrors()).thenReturn(false);

    // Call the method
    ResponseEntity<Object> response = Utils.validateFields(mockBindingResult);

    // Assert no errors, response should be null
    assertNull(response);
  }

}
