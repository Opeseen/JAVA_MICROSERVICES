package com.ubaclone.eservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class SuccessResponseDTO {

  private Boolean success;
  private Object data;
  private LocalDateTime timestamp;
}
