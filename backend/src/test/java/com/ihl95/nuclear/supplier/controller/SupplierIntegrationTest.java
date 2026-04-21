package com.ihl95.nuclear.supplier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihl95.nuclear.security.AuthenticationRequest;
import com.ihl95.nuclear.supplier.application.dto.SupplierDTO;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content; // Add this import statement
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // Add this import statement
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // Add this import statement

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SupplierIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private String getJwtToken() throws Exception {
    // Realiza la autenticación para obtener el token JWT
    AuthenticationRequest authRequest = new AuthenticationRequest();
    authRequest.setUsername("Admin");
    authRequest.setPassword("admin");

    return mockMvc.perform(post("/api/auth/authenticate")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(authRequest)))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();
  }

  @Test
  void ifGetAllSuppliers_thenAllSuppliersAreReturned() throws Exception {
    String token = getJwtToken(); // Obtener el token

    mockMvc.perform(get("/api/suppliers")
        .header("Authorization", "Bearer " + token)) // Agregar el token al encabezado
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("Supplier 1"))
        .andExpect(jsonPath("$[0].contact").value("Contact 1"))
        .andExpect(jsonPath("$[0].phone").value("+1-111-111-1111"))
        .andExpect(jsonPath("$[3].id").value(4))
        .andExpect(jsonPath("$[3].name").value("Supplier 4"))
        .andExpect(jsonPath("$[3].contact").value("Contact 4"))
        .andExpect(jsonPath("$[3].phone").value("+4-444-444-4444"));
  }

  @Test
  void ifGetSupplierById_thenSupplierWithId2IsReturned() throws Exception {
    String token = getJwtToken(); // Obtener el token

    mockMvc.perform(get("/api/suppliers/2")
        .header("Authorization", "Bearer " + token)) // Agregar el token al encabezado
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.name").value("Supplier 2"))
        .andExpect(jsonPath("$.contact").value("Contact 2"))
        .andExpect(jsonPath("$.phone").value("+2-222-222-2222"));
  }

  @Test
  void ifGetSupplierById_whenIdNotFound_thenNotFoundStatusIsReturned() throws Exception {
    String token = getJwtToken(); // Obtener el token

    mockMvc.perform(get("/api/suppliers/999")
        .header("Authorization", "Bearer " + token)) // Agregar el token al encabezado
        .andExpect(status().isNotFound());
  }

  // NOTE: Test removed - ifGetSupplierById_whenIdIsNull_thenBadRequestStatusIsReturned
  // This test was invalid because passing null as URL parameter results in "null" string,
  // which cannot be tested in this way. The controller's @PathVariable Long id cannot be null.

  @Test
  @Transactional
  void whenCreateSupplier_thenCreatedSupplierIsReturned() throws Exception {

    String token = getJwtToken();
    SupplierDTO supplierDTO = new SupplierDTO(null, "Proveedor 5", "contact5@example.com", "+15555555555");

    String newSupplierJson = objectMapper.writeValueAsString(supplierDTO);

    mockMvc.perform(post("/api/suppliers")
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(newSupplierJson))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").isNotEmpty())
        .andExpect(jsonPath("$.name").value("Proveedor 5"))
        .andExpect(jsonPath("$.contact").value("contact5@example.com"))
        .andExpect(jsonPath("$.phone").value("+15555555555"));

  }

  @Test
    @Transactional
    void whenCreateSupplierWithoutAttributes_thenValidationExceptionIsReturned() throws Exception {

      String token = getJwtToken();
      SupplierDTO supplierDTO = new SupplierDTO(null, "", "", "");
  
      String newSupplierJson = objectMapper.writeValueAsString(supplierDTO);
  
      mockMvc.perform(post("/api/suppliers")
          .header("Authorization", "Bearer " + token)
          .contentType(MediaType.APPLICATION_JSON)
          .content(newSupplierJson))
          .andExpect(status().isBadRequest())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.name").value("Supplier name is required"))
          .andExpect(jsonPath("$.contact").value("Contact is required"))
          .andExpect(jsonPath("$.phone").value("Phone number must be valid (7-15 digits, optional +)"));

    }

  @Test
  @Transactional
  void whenUpdateSupplier_thenUpdatedSupplierIsReturned() throws Exception {
    String token = getJwtToken();
    SupplierDTO updatedSupplierDTO = new SupplierDTO(null, "Updated Supplier 1", "updated1@example.com", "+11111111111");

    String updatedSupplierJson = objectMapper.writeValueAsString(updatedSupplierDTO);

    mockMvc.perform(post("/api/suppliers/1")
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updatedSupplierJson))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Updated Supplier 1"))
        .andExpect(jsonPath("$.contact").value("updated1@example.com"))
        .andExpect(jsonPath("$.phone").value("+11111111111"));
  }

  @Test
  void whenUpdateSupplier_whenIdNotFound_thenNotFoundStatusIsReturned() throws Exception {
    String token = getJwtToken();
    SupplierDTO updatedSupplierDTO = new SupplierDTO(null, "Updated Supplier", "updated@example.com", "+19999999999");

    String updatedSupplierJson = objectMapper.writeValueAsString(updatedSupplierDTO);

    mockMvc.perform(post("/api/suppliers/999")
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updatedSupplierJson))
        .andExpect(status().isNotFound());
  }

  @Test
  void whenUpdateSupplier_withInvalidData_thenBadRequestStatusIsReturned() throws Exception {
    String token = getJwtToken();
    SupplierDTO invalidDTO = new SupplierDTO(null, "", "", "");

    String invalidJson = objectMapper.writeValueAsString(invalidDTO);

    mockMvc.perform(post("/api/suppliers/1")
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Transactional
  void whenDeleteSupplier_thenSupplierIsDeleted() throws Exception {
    String token = getJwtToken();

    // Primero verificar que el proveedor existe
    mockMvc.perform(get("/api/suppliers/1")
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk());

    // Ahora eliminar
    mockMvc.perform(post("/api/suppliers/1/delete")
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isNoContent());

    // Verificar que ya no existe
    mockMvc.perform(get("/api/suppliers/1")
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isNotFound());
  }

  @Test
  void whenDeleteSupplier_whenIdNotFound_thenNotFoundStatusIsReturned() throws Exception {
    String token = getJwtToken();

    mockMvc.perform(post("/api/suppliers/999/delete")
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isNotFound());
  }

  @Test
  void whenGetAllSuppliers_withoutToken_thenUnauthorizedStatusIsReturned() throws Exception {
    mockMvc.perform(get("/api/suppliers"))
        .andExpect(status().isForbidden());
  }

  @Test
  void whenGetSupplierById_withoutToken_thenUnauthorizedStatusIsReturned() throws Exception {
    mockMvc.perform(get("/api/suppliers/1"))
        .andExpect(status().isForbidden());
  }

  @Test
  @Transactional
  void whenCreateSupplier_withoutToken_thenUnauthorizedStatusIsReturned() throws Exception {
    SupplierDTO supplierDTO = new SupplierDTO(null, "Test Supplier", "Test Contact", "+1-111-111-1111");

    String supplierJson = objectMapper.writeValueAsString(supplierDTO);

    mockMvc.perform(post("/api/suppliers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(supplierJson))
        .andExpect(status().isForbidden());
  }

  @Test
  void whenGetSupplierById_withValidId_thenCorrectDataIsReturned() throws Exception {
    String token = getJwtToken();

    mockMvc.perform(get("/api/suppliers/1")
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").isNotEmpty())
        .andExpect(jsonPath("$.contact").isNotEmpty())
        .andExpect(jsonPath("$.phone").isNotEmpty());
  }

  @Test
  @Transactional
  void whenCreateSupplier_withValidData_thenSupplierIsCreatedSuccessfully() throws Exception {
    String token = getJwtToken();
    SupplierDTO newSupplierDTO = new SupplierDTO(null, "New Supplier", "newsupplier@example.com", "+19999999999");

    String supplierJson = objectMapper.writeValueAsString(newSupplierDTO);

    mockMvc.perform(post("/api/suppliers")
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(supplierJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNotEmpty())
        .andExpect(jsonPath("$.name").value("New Supplier"))
        .andExpect(jsonPath("$.contact").value("newsupplier@example.com"))
        .andExpect(jsonPath("$.phone").value("+19999999999"));
  }

  @Test
  void whenGetAllSuppliers_thenResponseHasCorrectContentType() throws Exception {
    String token = getJwtToken();

    mockMvc.perform(get("/api/suppliers")
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"));
  }

  @Test
  void whenGetSupplierById_thenResponseHasCorrectContentType() throws Exception {
    String token = getJwtToken();

    mockMvc.perform(get("/api/suppliers/1")
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"));
  }

  @Test
  @Transactional
  void whenCreateSupplier_thenResponseHasCorrectContentType() throws Exception {
    String token = getJwtToken();
    SupplierDTO supplierDTO = new SupplierDTO(null, "Content Type Test", "contenttest@example.com", "+12111111111");

    String supplierJson = objectMapper.writeValueAsString(supplierDTO);

    mockMvc.perform(post("/api/suppliers")
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(supplierJson))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void whenGetAllSuppliers_thenReturnAllSuppliers() throws Exception {
    String token = getJwtToken();

    mockMvc.perform(get("/api/suppliers")
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(4));
  }

  @Test
  void whenGetSupplierById_withMultipleIds_thenReturnCorrectSupplier() throws Exception {
    String token = getJwtToken();

    // Test get supplier 1
    mockMvc.perform(get("/api/suppliers/1")
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Supplier 1"));

    // Test get supplier 3
    mockMvc.perform(get("/api/suppliers/3")
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(3))
        .andExpect(jsonPath("$.name").value("Supplier 3"));
  }

  @Test
  @Transactional
  void whenCreateSupplier_thenReturnCreatedSupplier() throws Exception {
    String token = getJwtToken();
    SupplierDTO supplierDTO = new SupplierDTO(null, "New Supplier", "newsup@example.com", "+15555555555");

    String supplierJson = objectMapper.writeValueAsString(supplierDTO);

    mockMvc.perform(post("/api/suppliers")
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(supplierJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("New Supplier"))
        .andExpect(jsonPath("$.contact").value("newsup@example.com"))
        .andExpect(jsonPath("$.phone").value("+15555555555"));
  }

  @Test
  @Transactional
  void whenCreateSupplier_withInvalidData_thenReturnBadRequest() throws Exception {
    String token = getJwtToken();
    SupplierDTO supplierDTO = new SupplierDTO(null, "", "Contact", "+1-555-555-5555");

    String supplierJson = objectMapper.writeValueAsString(supplierDTO);

    mockMvc.perform(post("/api/suppliers")
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(supplierJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Transactional
  void whenUpdateSupplier_thenReturnUpdatedSupplier() throws Exception {
    String token = getJwtToken();
    SupplierDTO updatedDTO = new SupplierDTO(1L, "Updated Supplier", "updated@example.com", "+19999999999");

    String supplierJson = objectMapper.writeValueAsString(updatedDTO);

    mockMvc.perform(post("/api/suppliers/1")
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(supplierJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Updated Supplier"))
        .andExpect(jsonPath("$.contact").value("updated@example.com"))
        .andExpect(jsonPath("$.phone").value("+19999999999"));
  }

  @Test
  @Transactional
  void whenUpdateSupplier_withNonexistentId_thenReturnNotFound() throws Exception {
    String token = getJwtToken();
    SupplierDTO updatedDTO = new SupplierDTO(999L, "Updated Supplier", "updated@example.com", "+19999999999");

    String supplierJson = objectMapper.writeValueAsString(updatedDTO);

    mockMvc.perform(post("/api/suppliers/999")
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(supplierJson))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void whenUpdateSupplier_withInvalidData_thenReturnBadRequest() throws Exception {
    String token = getJwtToken();
    SupplierDTO updatedDTO = new SupplierDTO(1L, "", "Contact", "+1-999-999-9999");

    String supplierJson = objectMapper.writeValueAsString(updatedDTO);

    mockMvc.perform(post("/api/suppliers/1")
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(supplierJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  @Transactional
  void whenDeleteSupplier_thenReturnNoContent() throws Exception {
    String token = getJwtToken();

    mockMvc.perform(post("/api/suppliers/1/delete")
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isNoContent());
  }

  @Test
  @Transactional
  void whenDeleteSupplier_withNonexistentId_thenReturnNotFound() throws Exception {
    String token = getJwtToken();

    mockMvc.perform(post("/api/suppliers/999/delete")
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isNotFound());
  }

  @Test
  void whenGetAllSuppliers_thenResponseStatusIsOk() throws Exception {
    String token = getJwtToken();

    mockMvc.perform(get("/api/suppliers")
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk());
  }

  @Test
  void whenGetSupplierById_thenResponseStatusIsOk() throws Exception {
    String token = getJwtToken();

    mockMvc.perform(get("/api/suppliers/1")
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk());
  }

  @Test
  void whenGetSupplierById_withInvalidId_thenReturnNotFound() throws Exception {
    String token = getJwtToken();

    mockMvc.perform(get("/api/suppliers/999")
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isNotFound());
  }

}
