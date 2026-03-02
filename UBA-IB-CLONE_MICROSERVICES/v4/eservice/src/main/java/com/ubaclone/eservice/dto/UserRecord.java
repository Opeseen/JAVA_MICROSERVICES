package com.ubaclone.eservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRecord(
    @NotBlank String username,
    @NotBlank String firstname,
    @NotBlank String lastname,
    @NotBlank String password,
    @NotBlank String email,

    @Pattern(
        regexp = "^[0-9]{10}$",
        message = "Account number entered is invalid!"
    )
    String accountNumber) {
}
