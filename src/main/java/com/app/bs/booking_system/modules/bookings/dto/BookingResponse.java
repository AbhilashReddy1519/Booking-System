package com.app.bs.booking_system.modules.bookings.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookingResponse {
  private UUID bookingId;
  private String razorpayOrderId;
  private BigDecimal amount;
  private String razorpayKeyId;
}
