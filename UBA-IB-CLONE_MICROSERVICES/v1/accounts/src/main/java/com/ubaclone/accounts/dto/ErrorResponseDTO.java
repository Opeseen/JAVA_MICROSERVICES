package com.ubaclone.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
public class ErrorResponseDTO {

  private Boolean success;
  private String apiPath;
  private String errorMessage;
  private LocalDateTime errorTime;
}
