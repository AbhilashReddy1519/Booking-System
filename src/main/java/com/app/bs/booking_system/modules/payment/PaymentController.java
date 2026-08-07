package com.app.bs.booking_system.modules.payment;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
  private final PaymentService paymentService;

  @PostMapping("/create-order")
  public ResponseEntity<?> createOrder(@RequestParam UUID bookingId) {
    try {
      return ResponseEntity.ok(paymentService.createOrder(bookingId));
    } catch (Exception e) {
      return ResponseEntity.ok(e);
    }
  }

  // @PostMapping("/verify")
  // public ResponseEntity<?> verify(@RequestBody  request){
  //   paymentService.verifyPayment(request);
  //   return ResponseEntity.ok("Verified");
  // }
}
