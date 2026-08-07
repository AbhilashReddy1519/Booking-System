package com.app.bs.booking_system.modules.payment;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.app.bs.booking_system.modules.bookings.Booking;
import com.app.bs.booking_system.modules.bookings.BookingRepository;
import com.app.bs.booking_system.modules.payment.services.RazorpayService;
import com.razorpay.Order;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
  private final RazorpayService razorpayService;
  private final PaymentRepository paymentRepository;
  private final BookingRepository bookingRepository;

  @Transactional
  public Order createOrder(UUID bookingId) throws Exception {
    Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new RuntimeException("Booking not found"));
    
    Order order = razorpayService.createOrder(booking.getAmount());

    Payment payment = Payment.builder()
        .booking(booking)
        .amount(booking.getAmount())
        .status(PaymentStatus.PROCESSING)
        .razorpayOrderId(order.get("id"))
        .build();
    
    paymentRepository.save(payment);

    return order;
  }
}
