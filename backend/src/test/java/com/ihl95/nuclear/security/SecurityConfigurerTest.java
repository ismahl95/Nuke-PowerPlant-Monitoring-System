package com.ihl95.nuclear.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // Add this import statement
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // Add this import statement
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SecurityConfigurerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPublicEndpointAuthenticate() throws Exception {
        // Este endpoint debe estar permitido sin autenticación
        mockMvc.perform(post("/api/auth/authenticate")
                .contentType("application/json")
                .content("{\"username\": \"Admin\", \"password\": \"admin\"}"))
                .andExpect(status().isOk()); // Ajusta según el código esperado
    }

    @Test
    void testPublicEndpointAuthenticateFailure() throws Exception {
        // Prueba con credenciales incorrectas para esperar un 401 y mensaje específico
        mockMvc.perform(post("/api/auth/authenticate")
                .contentType("application/json")
                .content("{\"username\": \"wrongUser\", \"password\": \"wrongPassword\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Incorrect username or password")); // Verifica el mensaje de error
    }

    @Test
    void testPublicSwaggerEndpoint() throws Exception {
        // Swagger debe ser accesible sin autenticación
        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk());
    }

    @Test
    void testPublicApiDocsEndpoint() throws Exception {
        // Documentación de API (v3/api-docs) debe ser accesible sin autenticación
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk());
    }

    @Test
    void testPrivateEndpointRequiresAuthentication() throws Exception {
        // Cualquier otro endpoint debe requerir autenticación
        mockMvc.perform(get("/api/nuclear-plants"))
                .andExpect(status().isForbidden()); // Se espera un 401 si no hay autenticación
    }

}
