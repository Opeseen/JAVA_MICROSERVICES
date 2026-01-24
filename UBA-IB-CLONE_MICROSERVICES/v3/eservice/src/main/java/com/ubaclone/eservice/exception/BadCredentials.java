package com.ubaclone.eservice.exception;

public class BadCredentials extends RuntimeException{

  public BadCredentials(String message){
    super(message);
  }
}
