package com.ubaclone.transactions.exception;

public class ResourceNotFoundException extends RuntimeException{

  public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue){
    super(String.format("%s not found with the %s: '%s' ", resourceName, fieldName, fieldValue));
  }
}
