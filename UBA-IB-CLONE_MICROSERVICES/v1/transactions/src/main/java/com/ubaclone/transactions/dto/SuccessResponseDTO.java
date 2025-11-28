package com.ubaclone.transactions.dto;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class SuccessResponseDTO {

  private Boolean success;
  private Object data;
  private LocalDateTime timestamp;
}
