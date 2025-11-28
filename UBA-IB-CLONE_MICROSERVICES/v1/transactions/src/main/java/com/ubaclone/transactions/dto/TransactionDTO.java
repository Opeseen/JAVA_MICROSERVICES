package com.ubaclone.transactions.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;


public class TransactionDTO {

  @NotEmpty(message = "Account number can't be empty")
  @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digit")
  private String accountNumber;

  @NotEmpty(message = "CustomerId must not be empty")
  private String customerId;

  @NotEmpty(message = "transaction summary must not be empty")
  private String transactionSummary;

  @NotEmpty(message = "transaction type must not be empty")
  private String transactionType;

  @NotEmpty(message = "transaction amount must not be empty")
  private Integer transactionAmount;
}
