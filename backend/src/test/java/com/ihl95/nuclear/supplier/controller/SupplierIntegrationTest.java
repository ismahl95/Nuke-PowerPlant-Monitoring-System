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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content; // Add this import statement
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // Add this import statement
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put; // Add this import statement
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete; // Add this import statement
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // Add this import statement

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SupplierIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private String getJwtToken() throws Exception {
    // Realiza la autenticación para obtener el token JWT
    AuthenticationRequest authRequest = new AuthenticationRequest();
    authRequest.setUsername("Admin");
    authRequest.setPassword("admin");

    String tokenResponse = mockMvc.perform(post("/api/auth/authenticate")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(authRequest)))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();

    return tokenResponse; // Devuelve el token JWT
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

}
