package com.folkadev.folka_tasks.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.folkadev.folka_tasks.domain.dto.ErrorResponse;
import com.folkadev.folka_tasks.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({ IllegalArgumentException.class })
  public ResponseEntity<ErrorResponse> handleExceptions(RuntimeException ex, WebRequest request) {

    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
        request.getDescription(false));

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({ ResourceNotFoundException.class })
  public ResponseEntity<ErrorResponse> handleNotFoundExceptions(RuntimeException ex, WebRequest request) {

    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(),
        request.getDescription(false));

    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
  public ResponseEntity<ErrorResponse> handleInvalidInputType(MethodArgumentTypeMismatchException ex,
      WebRequest request) {
    String customMessage = "Invalid ID format for parameter: " + ex.getName();
    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), customMessage,
        request.getDescription(false));

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

}
