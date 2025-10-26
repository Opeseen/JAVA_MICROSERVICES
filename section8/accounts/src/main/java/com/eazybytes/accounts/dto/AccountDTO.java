package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
    name = "Accounts", description = "Schema to hold account info"
)
public class AccountDTO {

  @Schema(description = "Account number of EazyBank account", example = "1234567890")
  @NotEmpty(message = "AccountNumber cannot be null or empty")
  @Pattern(regexp = "(^$|[0-9]{10})", message = "AccountNumber must be 10 digits")
  private long accountNumber;

  @Schema(description = "Account type of EazyBank account", example = "Savings")
  @NotEmpty(message = "AccountType cannot be null or empty")
  private String accountType;

  @Schema(description = "EazyBank branch address", example = "12B NewYork Street")
  @NotEmpty(message = "BranchAddress cannot be null or empty")
  private String branchAddress;
}
