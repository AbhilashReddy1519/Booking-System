package com.app.bs.booking_system.modules.payment.services;

import java.math.BigDecimal;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.app.bs.booking_system.modules.bookings.Booking;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RazorpayService {
  private final RazorpayClient razorpayClient;

  public Order createOrder(Booking booking) throws Exception {
    BigDecimal amount = booking.getAmount();
    JSONObject options = new JSONObject();
    // amount in paises
    options.put("amount", amount.multiply(BigDecimal.valueOf(100)).intValue());
    options.put("currency", "INR");
    options.put("receipt", booking.getId().toString());

    return razorpayClient.orders.create(options);
  }

  public Order getOrder(String orderId) throws Exception {
    return razorpayClient.orders.fetch(orderId);
  }

  public Payment getPayment(String paymentId) throws Exception {
    return razorpayClient.payments.fetch(paymentId);
  }

}
