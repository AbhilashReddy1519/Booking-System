package com.app.bs.booking_system.modules.payment;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.bs.booking_system.modules.payment.dto.VerifyPayment;
import com.app.bs.booking_system.utils.APIResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("/verify")
  public APIResponse<Object> verifyPayment(@RequestBody VerifyPayment verifyPayment) {
    boolean isValid = paymentService.verifyPayment(verifyPayment);
    if(isValid) {
      return APIResponse.success("Booking Success");
    }
    return APIResponse.error("Booking failed");
  }
}
