package com.app.bs.booking_system.modules.screens.DTO;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateScreenRequestDTO {
  @NotBlank(message = "Name should not be empty")
  @Size(max = 100, message = "Name cannot exceed 100 characters")
  private String name;

  @NotBlank(message = "Theater Id should not be empty")
  private UUID theater_id;
}
