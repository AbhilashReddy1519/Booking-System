package com.app.bs.booking_system.modules.payment;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
  // private final PaymentService paymentService;
  // @PostMapping("/verify")
  // public ResponseEntity<?> verify(@RequestBody  request){
  //   paymentService.verifyPayment(request);
  //   return ResponseEntity.ok("Verified");
  // }
}
