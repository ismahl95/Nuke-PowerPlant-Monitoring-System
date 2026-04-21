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

    private String secretKey = "mySecretKey";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtUtil = new JwtUtil();
        jwtUtil.secretKey = secretKey;
    }

    /**
     * Creates a test JWT token with the specified username and expiration offset.
     *
     * @param username the subject (username) of the token
     * @param expirationOffsetMillis the milliseconds until token expiration (negative for expired tokens)
     * @return a valid JWT token string
     */
    private String createTestToken(String username, long expirationOffsetMillis) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationOffsetMillis))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * Validates that a token with correct username and valid expiration returns true.
     */
    @Test
    void testValidateToken_ValidToken() {
        String username = "testUser";
        when(userDetails.getUsername()).thenReturn(username);

        String token = createTestToken(username, 1000 * 60 * 60);

        assertTrue(jwtUtil.validateToken(token, userDetails));
    }

    /**
     * Validates that a token with mismatched username returns false.
     */
    @Test
    void testValidateToken_InvalidUsername() {
        when(userDetails.getUsername()).thenReturn("anotherUser");

        String token = createTestToken("testUser", 1000 * 60 * 60);

        assertFalse(jwtUtil.validateToken(token, userDetails));
    }

    /**
     * Validates that an expired token returns false.
     */
    @Test
    void testValidateToken_ExpiredToken() {
        String username = "testUser";
        when(userDetails.getUsername()).thenReturn(username);

        String token = createTestToken(username, -1000 * 60 * 60);

        assertFalse(jwtUtil.validateToken(token, userDetails));
    }

    /**
     * Validates that a malformed token with invalid format returns false.
     */
    @Test
    void validateToken_GenericException_ReturnsFalse() {
        String token = "invalidToken";

        when(userDetails.getUsername()).thenReturn("testUser");

        JwtUtil jwtUtilSpy = spy(jwtUtil);
        doThrow(new MalformedJwtException("Token mal formado")).when(jwtUtilSpy).extractUsername(token);

        assertFalse(jwtUtilSpy.validateToken(token, userDetails));
    }
}
