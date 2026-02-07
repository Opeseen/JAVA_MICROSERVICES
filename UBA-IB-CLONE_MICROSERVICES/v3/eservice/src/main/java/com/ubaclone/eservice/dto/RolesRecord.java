package com.ubaclone.eservice.dto;

import jakarta.validation.constraints.NotBlank;

public record RolesRecord(
    @NotBlank String clientId, @NotBlank String realm,
    @NotBlank String roleName, @NotBlank String description,
    @NotBlank String userId) {
}
