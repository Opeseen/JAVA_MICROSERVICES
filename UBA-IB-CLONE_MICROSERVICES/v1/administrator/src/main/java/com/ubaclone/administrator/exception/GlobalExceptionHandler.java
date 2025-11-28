package com.ubaclone.administrator.exception;

import com.ubaclone.administrator.dto.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception exception, WebRequest webRequest){
    ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
        false,webRequest.getDescription(false),exception.getMessage(), LocalDateTime.now()
    );

    return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
