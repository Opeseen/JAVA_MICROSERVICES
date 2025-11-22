package com.ubaclone.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CustomerDTO {

  @NotEmpty(message = "customer email address can't be empty")
  @Email(message = "kindly provide a valid email address")
  private String email;

  @NotEmpty(message = "customer fullname is required")
  private String fullName;

  @NotEmpty(message = "customer phone number is required")
  private String phone;

}
