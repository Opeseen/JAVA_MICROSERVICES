package com.ubaclone.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TransactionDTO {

  @NotEmpty(message = "Account number can't be empty")
  @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digit")
  private String accountNumber;

  @NotEmpty(message = "transaction summary must not be empty")
  private String transactionSummary;

  @NotEmpty(message = "transaction amount must not be empty")
  private String transactionAmount;

  private String closingBalance;
}
