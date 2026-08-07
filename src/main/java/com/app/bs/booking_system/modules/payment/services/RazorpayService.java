package com.app.bs.booking_system.modules.payment.services;

import java.math.BigDecimal;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RazorpayService {
  private final RazorpayClient razorpayClient;

  public Order createOrder(BigDecimal amount) throws Exception {
    JSONObject options = new JSONObject();
    // amount in paises
    options.put("amount", amount.multiply(BigDecimal.valueOf(100)).intValue());
    options.put("currency", "INR");
    options.put("receipt", UUID.randomUUID().toString());

    return razorpayClient.orders.create(options);
  }
}
