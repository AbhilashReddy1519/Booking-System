package com.app.bs.booking_system.modules.bookings.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingDTO {
  @NotNull(message = "Show ID should not be empty")
  private UUID showId;

  @NotEmpty(message = "At least one seat must be selected")
  private List<UUID> seatIds;
}
