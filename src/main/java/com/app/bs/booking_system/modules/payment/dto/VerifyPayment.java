package com.app.bs.booking_system.modules.payment.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyPayment {
  private String razorpayOrderId;
  private String razorpayPaymentId;
  private String signature;
  private UUID bookingId;
}
