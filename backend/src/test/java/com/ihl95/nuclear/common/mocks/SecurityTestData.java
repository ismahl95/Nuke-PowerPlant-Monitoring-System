package com.ihl95.nuclear.common.mocks;

import com.ihl95.nuclear.user.User;

/**
 * Datos de prueba compartidos para Autenticación y Seguridad.
 * Reutilizable en tests de integración y E2E.
 */
public class SecurityTestData {

    // ── USUARIOS ─────────────────────────────────────

    public static User createAdminUser() {
        return new User(1L, "admin", "$2a$10$encoded_password_hash", "ADMIN");
    }

    public static User createOperatorUser() {
        return new User(2L, "operator", "$2a$10$encoded_password_hash", "OPERATOR");
    }

    public static User createViewerUser() {
        return new User(3L, "viewer", "$2a$10$encoded_password_hash", "VIEWER");
    }

    // ── CREDENCIALES ─────────────────────────────────

    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "admin123";
    public static final String OPERATOR_USERNAME = "operator";
    public static final String OPERATOR_PASSWORD = "operator123";
    public static final String VIEWER_USERNAME = "viewer";
    public static final String VIEWER_PASSWORD = "viewer123";

    public static final String INVALID_USERNAME = "nonexistent";
    public static final String INVALID_PASSWORD = "wrong_password";

    // ── TOKENS JWT ───────────────────────────────────

    public static final String VALID_JWT_TOKEN_ADMIN =
        "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTUxNjIzOTAyMn0.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    public static final String INVALID_JWT_TOKEN =
        "Bearer invalid_token_xyz";

    public static final String EXPIRED_JWT_TOKEN =
        "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJleHBpcmVkIiwiZXhwIjoxNTE2MjM5MDIyfQ.7Z8V_KZv9qV8kZ7Z_pZ9Z";
}


