package com.ihl95.nuclear.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ihl95.nuclear.user.CustomUserDetailsService;
import com.ihl95.nuclear.user.User;
import com.ihl95.nuclear.user.UserRepository;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private User user;

    @BeforeEach
    void setUp() {
        // Inicializar el usuario de prueba
        user = new User();
        user.setUsername("testUser");
        user.setPassword("password"); // Puedes encriptar si es necesario
        user.setRole("ROLE_USER");
    }

    @Test
    void whenLoadUserByUsername_thenUserDetailsShouldBeReturned() {
        // Configurar el mock del repositorio
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        // Actuar
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testUser");

        // Verificar
        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertFalse(userDetails.getAuthorities().isEmpty());
        assertEquals("ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    void whenLoadUserByUsername_userNotFound_thenThrowUsernameNotFoundException() {
        // Configurar el mock del repositorio para devolver vacío
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // Actuar y verificar que se lanza la excepción
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("nonExistentUser");
        });
    }
}
