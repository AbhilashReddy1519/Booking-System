package com.app.bs.booking_system.exceptions;

import org.springframework.http.HttpStatus;

public class SeatAlreadyBookedException extends APIException {
  public SeatAlreadyBookedException(String message) {
    super(message, HttpStatus.CONFLICT);
  }
}
