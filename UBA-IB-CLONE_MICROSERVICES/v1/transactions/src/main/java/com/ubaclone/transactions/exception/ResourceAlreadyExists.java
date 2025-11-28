package com.ubaclone.transactions.exception;

public class ResourceAlreadyExists extends RuntimeException{

  public ResourceAlreadyExists(String resourceName, String fieldName, String fieldValue){
    super(String.format("%s already exists with the %s: '%s' ", resourceName, fieldName, fieldValue));
  }
}
