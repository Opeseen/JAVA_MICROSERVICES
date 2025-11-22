package com.ubaclone.administrator.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AdminDTO {

  @NotEmpty(message = "admin fullname can't be empty")
  private String fullName;

  @NotEmpty(message = "admin username can't be empty")
  private String userName;
}
