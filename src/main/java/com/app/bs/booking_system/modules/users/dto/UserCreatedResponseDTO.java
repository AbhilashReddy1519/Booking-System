package com.app.bs.booking_system.modules.users.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class UserCreatedResponseDTO {
  private UUID userId;
  private String email;
  private String role;
}
