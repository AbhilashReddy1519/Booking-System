package com.app.bs.booking_system.modules.seats.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSeatsDTO {
  @NotNull(message = "Screen ID should not be empty")
  private UUID screen_id;
}
