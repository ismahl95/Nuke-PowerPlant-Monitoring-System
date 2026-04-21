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
  void authenticationProvider_WithProperConfiguration_SupportsUsernamePasswordTokens() {
      UserDetailsService userDetailsService = mock(UserDetailsService.class);

      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userDetailsService);
      PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      authProvider.setPasswordEncoder(passwordEncoder);

      assertNotNull(authProvider);
      assertTrue(authProvider.supports(UsernamePasswordAuthenticationToken.class));
  }

  @Test
  void authenticationProvider_WithBCryptEncoder_IsConfiguredCorrectly() {
      UserDetailsService userDetailsService = mock(UserDetailsService.class);
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder);

      assertNotNull(authProvider);
      assertTrue(authProvider.supports(UsernamePasswordAuthenticationToken.class));
  }

  @Test
  void authenticationProvider_WithoutConfiguration_IsNotNull() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      assertNotNull(authProvider);
  }
}
