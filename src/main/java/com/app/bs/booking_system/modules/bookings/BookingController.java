package com.app.bs.booking_system.modules.bookings;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.bs.booking_system.config.RazorpayConfig;
import com.app.bs.booking_system.modules.bookings.dto.BookingResponse;
import com.app.bs.booking_system.modules.bookings.dto.CreateBookingDTO;
import com.app.bs.booking_system.modules.payment.Payment;
import com.app.bs.booking_system.modules.payment.PaymentService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/booking")
public class BookingController {
  private final BookingService bookingService;
  private final PaymentService paymentService;
  private final RazorpayConfig razorpayConfig;

  public BookingController(BookingService bookingService, PaymentService paymentService, RazorpayConfig razorpayConfig) {
    this.bookingService = bookingService;
    this.paymentService = paymentService;
    this.razorpayConfig = razorpayConfig;
  }

  @PostMapping("")
  public BookingResponse createBooking(@Valid @RequestBody CreateBookingDTO createBookingDTO, @AuthenticationPrincipal UUID userId) {
    Booking booking = bookingService.createBooking(createBookingDTO, userId);
    Payment payment = paymentService.createPayment(booking.getId());
    
    return BookingResponse.builder()
        .amount(booking.getAmount())
        .bookingId(booking.getId())
        .razorpayOrderId(payment.getRazorpayOrderId())
        .razorpayKeyId(razorpayConfig.getKeyId())
        .build();
  }

  @GetMapping("")
  public List<Booking> getBookings() {
    return bookingService.getBookings();
  }
  
  
}
