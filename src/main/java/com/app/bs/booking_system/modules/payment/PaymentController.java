package com.app.bs.booking_system.modules.payment;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.bs.booking_system.modules.payment.dto.VerifyPayment;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("/verify")
  private String verifyPayment(@RequestBody VerifyPayment verifyPayment) {
    boolean isValid = paymentService.verifyPayment(verifyPayment);
    if(isValid) {
      paymentService.confirmBooking(verifyPayment.getBookingId());
      return new String("Booking Success");
    }
    return new String("Booking failed");
  }
}
