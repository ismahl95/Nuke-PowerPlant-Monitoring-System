package com.ihl95.nuclear.security.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.ihl95.nuclear.security.AuthenticationRequest;
import com.ihl95.nuclear.security.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints para autenticación de usuarios")
public class AuthController {

  private AuthenticationManager authenticationManager;

  private JwtUtil jwtUtil;

  private UserDetailsService userDetailsService;

  public AuthController(
    AuthenticationManager authenticationManager,
    JwtUtil jwtUtil,
    UserDetailsService userDetailsService
    ) {
      this.authenticationManager = authenticationManager;
      this.jwtUtil = jwtUtil;
      this.userDetailsService = userDetailsService;
    }

  @Operation(summary = "Autenticación de usuario", description = "Autentica al usuario con sus credenciales (nombre de usuario y contraseña) y genera un token JWT")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Autenticación exitosa y token JWT generado", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "401", description = "Nombre de usuario o contraseña incorrectos", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "500", description = "Error en la autenticación", content = @Content(schema = @Schema(implementation = String.class)))
  })
  @PostMapping("/authenticate")
  public String createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
    try {
      // Autentica al usuario
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
              authenticationRequest.getPassword()));
    } catch (Exception e) {
      throw new Exception("Incorrect username or password", e);
    }

    // Carga los detalles del usuario y genera el token
    final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
    return jwtUtil.generateToken(userDetails); // Genera el token usando el nombre de usuario
  }
}
