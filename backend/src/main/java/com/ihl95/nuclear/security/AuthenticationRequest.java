package com.ihl95.nuclear.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {
  private String username;
  private String password;
}
