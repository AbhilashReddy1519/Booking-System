package com.app.bs.booking_system.modules.languages.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateLanguageDTO {
  @NotBlank(message = "Language should not be empty")
  private String language;
}
