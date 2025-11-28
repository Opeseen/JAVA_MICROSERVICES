package com.ubaclone.accounts.mapper;

import com.ubaclone.accounts.dto.AccountDTO;
import com.ubaclone.accounts.entity.Account;

public class AccountMapper {
  public static AccountDTO mapToAccountDto(Account account, AccountDTO accountDTO){
    accountDTO.setAccountNumber(account.getAccountNumber().toString());
    return accountDTO;
  }

  public static Account mapToAccount(AccountDTO accountDTO, Account account){
    account.setAccountNumber(Long.valueOf(accountDTO.getAccountNumber()));
    return account;
  }
}
