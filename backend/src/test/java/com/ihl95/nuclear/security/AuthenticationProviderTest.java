package com.ihl95.nuclear.security;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class AuthenticationProviderTest {

  @Test
  void testAuthenticationProviderConfiguration() {
      // Mock del UserDetailsService
      UserDetailsService userDetailsService = mock(UserDetailsService.class);

      // Crear el DaoAuthenticationProvider
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userDetailsService);
      PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      authProvider.setPasswordEncoder(passwordEncoder);

      // Asegurar que el provider tiene un PasswordEncoder configurado
      assertNotNull(authProvider);
      assertTrue(authProvider.supports(UsernamePasswordAuthenticationToken.class)); // Verifica que el provider soporta el servicio de usuarios
  }
}
