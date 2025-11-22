package com.ubaclone.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountDTO {

  @NotEmpty(message = "account number can't be empty")
  private Long accountNumber;

  @NotEmpty(message = "user bvn is required")
  @Size(min = 10, max = 10, message = "bvn length must be 10 digit ")
  private Long bvn;

  @NotEmpty(message = "account type is required")
  private String accountType;

  @NotEmpty(message = "branch address can't be empty")
  private String branchAddress;

}
