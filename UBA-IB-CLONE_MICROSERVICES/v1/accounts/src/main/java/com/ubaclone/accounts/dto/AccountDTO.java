package com.ubaclone.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountDTO {

  private String accountNumber;
  private CustomerDTO customer;
}
