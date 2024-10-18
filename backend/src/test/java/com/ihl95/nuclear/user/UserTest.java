package com.ihl95.nuclear.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserCreation() {
        // Crear una nueva instancia de User
        User user = new User(1L, "username", "password", "ROLE_USER");

        // Verificar que se haya creado correctamente
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("ROLE_USER", user.getRole());
    }

    @Test
    void testUserDefaultConstructor() {
        // Crear una nueva instancia de User usando el constructor por defecto
        User user = new User();

        // Verificar que los valores por defecto son null
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getRole());
    }

    @Test
    void testUserSettersAndGetters() {
        // Crear una nueva instancia de User
        User user = new User();

        // Usar los setters
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setRole("ROLE_ADMIN");

        // Verificar que los getters devuelven los valores correctos
        assertEquals(1L, user.getId());
        assertEquals("testUser", user.getUsername());
        assertEquals("testPassword", user.getPassword());
        assertEquals("ROLE_ADMIN", user.getRole());
    }

    @Test
    void testUserToString() {
        User user = new User(1L, "username", "password", "ROLE_USER");

        // Verificar que el método toString es correcto (esto depende de cómo se genere)
        String expectedString = "User(id=1, username=username, password=password, role=ROLE_USER)";
        assertEquals(expectedString, user.toString());
    }

    @Test
    void testUserEqualsAndHashCode() {
        User user1 = new User(1L, "username", "password", "ROLE_USER");
        User user2 = new User(1L, "username", "password", "ROLE_USER");
        User user3 = new User(2L, "username2", "password2", "ROLE_ADMIN");

        // Verificar que user1 es igual a user2 (mismo id)
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());

        // Verificar que user1 no es igual a user3 (diferente id)
        assertNotEquals(user1, user3);
    }
}


