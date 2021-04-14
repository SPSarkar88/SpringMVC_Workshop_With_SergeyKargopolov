package com.matteo.restfulwebservices.exception;

import com.matteo.restfulwebservices.model.response.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserServiceException.class)
  public ResponseEntity<Object> handleUserServiceException(UserServiceException e, WebRequest req) {

    ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), LocalDateTime.now());

    return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleOtherExceptions(Exception e, WebRequest req) {

    ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), LocalDateTime.now());

    return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
