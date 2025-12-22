package com.ubaclone.eservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserDTO {
  private Long Id;

  @NotEmpty(message = "username can't be empty")
  private String username;

  @NotEmpty(message = "user password is required")
  @Size(min = 8, message = "password must be 8 digit or greater")
  private String password;

  @NotEmpty(message = "customer accountNumber is required")
  @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digit")
  private String accountNumber;

  private String customerId;
}
