package com.app.bs.booking_system.modules.payment;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.app.bs.booking_system.config.RazorpayConfig;
import com.app.bs.booking_system.exceptions.ResourceNotFoundException;
import com.app.bs.booking_system.modules.bookings.Booking;
import com.app.bs.booking_system.modules.bookings.BookingRepository;
import com.app.bs.booking_system.modules.bookings.BookingService;
import com.app.bs.booking_system.modules.payment.dto.VerifyPayment;
import com.app.bs.booking_system.modules.payment.services.RazorpayService;
import com.razorpay.Order;
import com.razorpay.Utils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
  private final BookingService bookingService;
  private final RazorpayService razorpayService;
  private final PaymentRepository paymentRepository;
  private final BookingRepository bookingRepository;
  private final RazorpayConfig razorpayConfig;

  @Transactional
  public Payment createPayment(UUID bookingId) {
    Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
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
    Order order = razorpayService.createOrder(booking);
    return order;
  }

  public boolean verifyPayment(VerifyPayment request) {
    try {
      // 1. Verify Razorpay signature
      String payload = request.getRazorpayOrderId() + "|" + request.getRazorpayPaymentId();
      boolean signatureValid = Utils.verifySignature(payload, request.getSignature(), razorpayConfig.getKeySecret());
      if (!signatureValid) {
        throw new IllegalStateException("Invalid Razorpay signature");
      }
      // 2. Find OUR payment using OUR Razorpay order ID
      Payment payment = paymentRepository.findByRazorpayOrderId(request.getRazorpayOrderId())
          .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
      // 3. Get OUR booking
      Booking booking = payment.getBooking();
      // 4. Make sure request booking matches
      if (!booking.getId().equals(request.getBookingId())) {
        throw new IllegalStateException("Booking does not match payment");
      }
      // 5. Idempotency
      if (payment.getStatus() == PaymentStatus.SUCCESS) {
        return true;
      }
      // 6. Fetch payment directly from Razorpay
      com.razorpay.Payment razorpayPayment = razorpayService.getPayment(request.getRazorpayPaymentId());
      // 7. Check payment ID
      if (!request.getRazorpayPaymentId().equals(razorpayPayment.get("id"))) {
        throw new IllegalStateException("Payment ID mismatch");
      }
      // 8. Check order ID
      if (!request.getRazorpayOrderId().equals(razorpayPayment.get("order_id"))) {
        throw new IllegalStateException("Order ID mismatch");
      }
      // 9. Check payment belongs to our order
      if (!payment.getRazorpayOrderId().equals(razorpayPayment.get("order_id"))) {
        throw new IllegalStateException("Payment does not belong to our order");
      }
      // 10. Check status
      if (!"captured".equals(razorpayPayment.get("status"))) {
        throw new IllegalStateException("Payment is not captured");
      }
      // 11. Check currency
      if (!"INR".equals(razorpayPayment.get("currency"))) {
        throw new IllegalStateException("Invalid currency");
      }
      // 12. Check amount
      long expectedAmount = booking.getAmount()
          .movePointRight(2)
          .longValueExact();
      long paidAmount = ((Number) razorpayPayment.get("amount")).longValue();
      if (expectedAmount != paidAmount) {
        throw new IllegalStateException("Payment amount mismatch");
      }
      return finalizeBooking(payment.getId(), booking.getId());
    } catch (Exception e) {
      throw new RuntimeException("Payment verification failed", e);
    }
  }

  @Transactional
  protected boolean finalizeBooking(UUID paymentId, UUID bookingId) {
    Payment payment = paymentRepository.findById(paymentId)
        .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
    Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
    if (!payment.getBooking().getId().equals(booking.getId())) {
      throw new IllegalStateException("Payment does not belong to the booking");
    }
    payment.setRazorpayPaymentId(booking.getId().toString());
    payment.setStatus(PaymentStatus.SUCCESS);
    paymentRepository.save(payment);
    bookingService.bookingSuccess(bookingId);
    return true;
  }

  public void confirmBooking(UUID bookingId) {
    bookingService.bookingSuccess(bookingId);
  }
}
