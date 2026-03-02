package com.ubaclone.eservice.exception;

import com.ubaclone.eservice.dto.ErrorResponseDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception exception){
    ErrorResponseDTO errorResponse = new ErrorResponseDTO(
        false,
        exception.getMessage(),
        LocalDateTime.now()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ResourceAlreadyExists.class)
  public ResponseEntity<ErrorResponseDTO> handleResourceAlreadyExistsException
      (ResourceAlreadyExists exception){
    ErrorResponseDTO errorResponse = new ErrorResponseDTO(
        false,
        exception.getMessage(),
        LocalDateTime.now()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFound.class)
  public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(
      ResourceNotFound exception){
    ErrorResponseDTO errorResponse = new ErrorResponseDTO(
        false,
        exception.getMessage(),
        LocalDateTime.now()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(KeycloakException.class)
  public ResponseEntity<ErrorResponseDTO> handleKeycloakException(KeycloakException exception) {
    ErrorResponseDTO errorResponse = new ErrorResponseDTO(
        false,
        exception.getMessage(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(exception.getStatusCode()).body(errorResponse);
  }


  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception ){
    Map<String, String> validationErrors = new HashMap<>();
    List<ObjectError> validationErrorList = exception.getBindingResult().getAllErrors();

    validationErrorList.forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String validationMsg = error.getDefaultMessage();
      validationErrors.put(fieldName, validationMsg);
    });
    Map<String, Object> response = new HashMap<>();
    response.put("error", true);
    response.put("message", validationErrors);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handleConstraintViolationException(
      ConstraintViolationException exception ){
    Map<String, String> errors = new HashMap<>();
    Set<ConstraintViolation<?>> constraintViolationSet = exception.getConstraintViolations();
    constraintViolationSet.forEach(constraintViolation ->
        errors.put(constraintViolation.getPropertyPath().toString(),constraintViolation.getMessage())
    );
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

}
