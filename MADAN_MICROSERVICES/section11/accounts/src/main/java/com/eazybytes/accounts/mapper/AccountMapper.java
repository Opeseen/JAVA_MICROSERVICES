package com.eazybytes.accounts.mapper;

import com.eazybytes.accounts.dto.AccountDTO;
import com.eazybytes.accounts.entities.Accounts;

public class AccountMapper {
  public static AccountDTO mapToAccountDTO(Accounts accounts, AccountDTO accountDTO) {
    accountDTO.setAccountNumber(accounts.getAccountNumber());
    accountDTO.setAccountType(accounts.getAccountType());
    accountDTO.setBranchAddress(accounts.getBranchAddress());
    return accountDTO;
  }

  public static Accounts mapToAccount(AccountDTO accountDTO, Accounts accounts) {
    accounts.setAccountNumber(accountDTO.getAccountNumber());
    accounts.setAccountType(accountDTO.getAccountType());
    accounts.setBranchAddress(accountDTO.getBranchAddress());
    return accounts;
  }
}
