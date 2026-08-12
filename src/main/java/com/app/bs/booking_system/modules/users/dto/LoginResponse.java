package com.app.bs.booking_system.modules.users.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class LoginResponse {
  private String email;
  private String token;
}
