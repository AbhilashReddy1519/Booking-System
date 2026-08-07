package com.app.bs.booking_system.modules.payment;

import org.springframework.stereotype.Service;

import com.app.bs.booking_system.modules.bookings.Booking;
import com.app.bs.booking_system.modules.payment.services.RazorpayService;
import com.razorpay.Order;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
  private final RazorpayService razorpayService;
  private final PaymentRepository paymentRepository;

  @Transactional
  public Payment createPayment(Booking booking) {
    try {
      Order order = createOrder(booking);

      Payment payment = Payment.builder()
          .booking(booking)
          .amount(booking.getAmount())
          .status(PaymentStatus.PROCESSING)
          .razorpayOrderId(order.get("id"))
          .build();

      return paymentRepository.save(payment);

    } catch (Exception e) {
      throw new RuntimeException("Payment creation failed", e);
    }
  }

  @Transactional
  public Order createOrder(Booking booking) throws Exception {
    Order order = razorpayService.createOrder(booking.getAmount());
    return order;
  }
}
