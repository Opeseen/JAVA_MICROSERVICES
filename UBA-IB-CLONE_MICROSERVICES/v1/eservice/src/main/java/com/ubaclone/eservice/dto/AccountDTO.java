package com.ubaclone.eservice.dto;

import lombok.Data;

@Data
public class AccountDTO {

  private String accountNumber;
  private Integer balance;
  private CustomerDTO customer;
}
