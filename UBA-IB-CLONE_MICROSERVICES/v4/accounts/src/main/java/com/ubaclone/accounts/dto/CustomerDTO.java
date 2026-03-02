package com.ubaclone.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {

  private Long customerId;

  @NotEmpty(message = "customer email address can't be empty")
  @Email(message = "kindly provide a valid email address")
  private String email;

  @NotBlank(message = "user bvn is required")
  @Size(min = 11, max = 11, message = "bvn length must be 11 digit ")
  private String bvn;

  @NotEmpty(message = "customer firstname is required")
  private String firstName;

  @NotEmpty(message = "customer lastname is required")
  private String lastName;

  @NotEmpty(message = "customer phone number is required")
  @Size(min = 11, max = 11, message = "phone number length must be 11 digit ")
  private String phone;

  @NotEmpty(message = "account type is required")
  private String accountType;

}
