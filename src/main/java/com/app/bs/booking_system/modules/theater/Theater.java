package com.app.bs.booking_system.modules.theater;

import java.util.List;
import java.util.UUID;

import com.app.bs.booking_system.modules.screens.Screen;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="theaters")
public class Theater {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank(message = "Theater name cannot be empty")
  private String name;

  @NotBlank(message = "Address name cannot be empty")
  private String Address;

  @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
  @JsonManagedReference
  private List<Screen> screens;
}
