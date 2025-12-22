package com.ubaclone.eservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {

  private Long customerId;
  private String email;
  private String bvn;
  private String fullName;
  private String phone;
  private String accountType;
  private String branchAddress;

}
