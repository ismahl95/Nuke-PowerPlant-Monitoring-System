package com.ihl95.nuclear.security;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;

class JwtRequestFilterTest {

  @Mock
  private JwtUtil jwtUtil;

  @Mock
  private UserDetailsService userDetailsService;

  @Mock
  private FilterChain filterChain;

  @Mock
  private UserDetails userDetails;

  @InjectMocks
  private JwtRequestFilter jwtRequestFilter;

  private MockHttpServletRequest request;
  private MockHttpServletResponse response;

  @BeforeEach
  public void setUp() {
      MockitoAnnotations.openMocks(this);
      request = new MockHttpServletRequest();
      response = new MockHttpServletResponse();
      SecurityContextHolder.clearContext(); // Limpiar el contexto antes de cada prueba
  }

  @Test
  void doFilterInternal_NoTokenInRequest() throws ServletException, IOException {
      jwtRequestFilter.doFilterInternal(request, response, filterChain);

      // Verificar que no se establezca autenticación en el contexto
      assertNull(SecurityContextHolder.getContext().getAuthentication());
      // Verificar que el filtro sigue con la cadena
      verify(filterChain, times(1)).doFilter(request, response);
  }

  @Test
  void doFilterInternal_InvalidTokenFormat() throws ServletException, IOException {
      // Configurar un token con formato incorrecto
      request.addHeader("Authorization", "InvalidToken");

      jwtRequestFilter.doFilterInternal(request, response, filterChain);

      // Verificar que no se establezca autenticación en el contexto
      assertNull(SecurityContextHolder.getContext().getAuthentication());
      verify(filterChain, times(1)).doFilter(request, response);
  }

  @Test
  void doFilterInternal_ExpiredToken() throws ServletException, IOException {
      // Configurar un token con el prefijo correcto
      request.addHeader("Authorization", "Bearer expiredToken");

      // Simular excepción de token expirado
      when(jwtUtil.extractUsername("expiredToken")).thenThrow(new ExpiredJwtException(null, null, "Token expirado"));

      jwtRequestFilter.doFilterInternal(request, response, filterChain);

      // Verificar que no se establezca autenticación en el contexto
      assertNull(SecurityContextHolder.getContext().getAuthentication());
      verify(filterChain, times(1)).doFilter(request, response);
  }

  @Test
  void doFilterInternal_ValidTokenAndUserNotAuthenticated() throws ServletException, IOException {
      String token = "validToken";
      String username = "testUser";

      request.addHeader("Authorization", "Bearer " + token);

      // Configurar JwtUtil para devolver un nombre de usuario válido
      when(jwtUtil.extractUsername(token)).thenReturn(username);
      when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
      when(jwtUtil.validateToken(token, userDetails)).thenReturn(true);

      jwtRequestFilter.doFilterInternal(request, response, filterChain);

      // Verificar que se ha configurado la autenticación en el contexto de seguridad
      assertNotNull(SecurityContextHolder.getContext().getAuthentication());
      verify(filterChain, times(1)).doFilter(request, response);
  }

  @Test
  void doFilterInternal_ValidTokenAndUserAlreadyAuthenticated() throws ServletException, IOException {
      String token = "validToken";
      String username = "testUser";

      request.addHeader("Authorization", "Bearer " + token);

      // Configurar JwtUtil para devolver un nombre de usuario válido
      when(jwtUtil.extractUsername(token)).thenReturn(username);
      when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
      when(jwtUtil.validateToken(token, userDetails)).thenReturn(true);

      // Configurar una autenticación previa en el contexto de seguridad
      SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));

      jwtRequestFilter.doFilterInternal(request, response, filterChain);

      // Verificar que no se establece una nueva autenticación
      verify(userDetailsService, never()).loadUserByUsername(username);
      verify(filterChain, times(1)).doFilter(request, response);
  }
}
