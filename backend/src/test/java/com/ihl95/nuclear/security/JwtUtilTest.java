package com.ihl95.nuclear.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;

class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    @Mock
    private UserDetails userDetails;

    private String secretKey = "mySecretKey"; // Usa un valor real o de prueba en lugar de un valor fijo en producción

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtUtil = new JwtUtil();
        jwtUtil.secretKey = secretKey;
    }

    private String createTestToken(String username, long expirationOffsetMillis) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationOffsetMillis))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    @Test
    void testValidateToken_ValidToken() {
        String username = "testUser";
        when(userDetails.getUsername()).thenReturn(username);

        String token = createTestToken(username, 1000 * 60 * 60); // Token válido por 1 hora

        assertTrue(jwtUtil.validateToken(token, userDetails));
    }

    @Test
    void testValidateToken_InvalidUsername() {
        when(userDetails.getUsername()).thenReturn("anotherUser");

        String token = createTestToken("testUser", 1000 * 60 * 60); // Token válido por 1 hora

        assertFalse(jwtUtil.validateToken(token, userDetails));
    }

    @Test
    void testValidateToken_ExpiredToken() {
        String username = "testUser";
        when(userDetails.getUsername()).thenReturn(username);

        // Crear un token que haya expirado hace 1 hora
        String token = createTestToken(username, -1000 * 60 * 60);

        // Verificar que validateToken devuelve false para el token expirado
        assertFalse(jwtUtil.validateToken(token, userDetails));
    }

    @Test
    void validateToken_GenericException_ReturnsFalse() {
        String token = "invalidToken";

        // Configurar UserDetails para evitar errores de null
        when(userDetails.getUsername()).thenReturn("testUser");

        // Simular una excepción genérica (por ejemplo, MalformedJwtException) en
        // extractUsername
        JwtUtil jwtUtilSpy = spy(jwtUtil);
        doThrow(new MalformedJwtException("Token mal formado")).when(jwtUtilSpy).extractUsername(token);

        // Verificar que validateToken devuelve false cuando ocurre una excepción
        // genérica
        assertFalse(jwtUtilSpy.validateToken(token, userDetails));
    }
}
