package com.ubaclone.gatewayserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(Exception.class)
  public Mono<ResponseEntity<String>>handleGlobalException(Exception exception){
    String errorMessage = exception.getMessage();
    return Mono.just(new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR));
  }

}
