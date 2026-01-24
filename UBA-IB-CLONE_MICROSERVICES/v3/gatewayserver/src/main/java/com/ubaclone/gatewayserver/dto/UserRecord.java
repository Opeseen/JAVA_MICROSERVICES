package com.ubaclone.gatewayserver.dto;

public record UserRecord(
    String username, String firstname,
    String lastname, String password, String email) {
}
