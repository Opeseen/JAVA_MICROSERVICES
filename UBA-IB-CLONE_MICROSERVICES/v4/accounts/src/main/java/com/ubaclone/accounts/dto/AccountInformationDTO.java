package com.ubaclone.accounts.dto;

import lombok.Data;

@Data
public class AccountInformationDTO {

  private String accountNumber;
  private Integer balance;
  private String accountType;
  private CustomerDTO customer;

}
