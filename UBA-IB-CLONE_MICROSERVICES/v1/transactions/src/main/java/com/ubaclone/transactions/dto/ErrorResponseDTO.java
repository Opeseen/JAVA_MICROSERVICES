package com.ubaclone.transactions.dto;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class ErrorResponseDTO {

  private Boolean success;
  private String apiPath;
  private String errorMessage;
  private LocalDateTime errorTime;
}
