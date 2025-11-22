package com.ubaclone.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
public class SuccessResponseDTO {

  private Boolean success;
  private Object data;
  private LocalDateTime timestamp;
}
