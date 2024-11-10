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

  @Test
  void ifGetSupplierById_whenIdIsNull_thenBadRequestStatusIsReturned() throws Exception {
    String token = getJwtToken(); // Obtener el token

    Long id = null;

    mockMvc.perform(get("/api/suppliers/" + id)
        .header("Authorization", "Bearer " + token)) // Agregar el token al encabezado
        .andExpect(status().isBadRequest());
  }

  @Test
  @Transactional
  void whenCreateSupplier_thenCreatedSupplierIsReturned() throws Exception {

    String token = getJwtToken();
    SupplierDTO supplierDTO = new SupplierDTO(null, "Proveedor 5", "Contacto 5", "+5-555-555-5555");

    String newSupplierJson = objectMapper.writeValueAsString(supplierDTO);

    mockMvc.perform(post("/api/suppliers")
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(newSupplierJson))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").isNotEmpty())
        .andExpect(jsonPath("$.name").value("Proveedor 5"))
        .andExpect(jsonPath("$.contact").value("Contacto 5"))
        .andExpect(jsonPath("$.phone").value("+5-555-555-5555"));

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
          .andExpect(jsonPath("$.name").value("El nombre es obligatorio"))
          .andExpect(jsonPath("$.contact").value("El contacto es obligatorio"))
          .andExpect(jsonPath("$.phone").value("El teléfono es obligatorio"));

    }

}
