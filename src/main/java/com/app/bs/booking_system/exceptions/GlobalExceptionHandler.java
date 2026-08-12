package com.app.bs.booking_system.exceptions;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.bs.booking_system.utils.APIResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(APIException.class)
  public ResponseEntity<APIResponse<Object>> handle(APIException ex) {
    return ResponseEntity.status(ex.getStatus()).body(APIResponse.error(ex.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<APIResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {
    String msg = ex.getBindingResult().getFieldErrors().stream()
        .map(f -> f.getField() + ": " + f.getDefaultMessage())
        .collect(Collectors.joining(", "));
    return ResponseEntity.badRequest().body(APIResponse.error(msg));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<APIResponse<Object>> handleAny(Exception ex) {
    return ResponseEntity.internalServerError().body(APIResponse.error("Something went wrong"));
  }
}