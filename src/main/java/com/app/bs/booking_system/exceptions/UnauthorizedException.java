package com.app.bs.booking_system.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends APIException {

  public UnauthorizedException(String message) {
    super(message, HttpStatus.UNAUTHORIZED);
  }
}