package com.ubaclone.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountDTO {

  @NotEmpty(message = "bvn number can't be empty")
  @Pattern(regexp = "^[0-9]{11}$", message = "bvn number must be 11 digit")
  private String bvn;
  private String accountType;

}
