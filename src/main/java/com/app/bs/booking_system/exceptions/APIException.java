package com.app.bs.booking_system.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class APIException extends RuntimeException {
  private final HttpStatus status;

  protected APIException(String message, HttpStatus status) {
    super(message);
    this.status = status;
  }
}
