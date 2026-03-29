package com.ubaclone.accounts.mapper;

import com.ubaclone.accounts.dto.AccountInformationDTO;
import com.ubaclone.accounts.entity.Account;

public class AccountMapper {
  public static AccountInformationDTO mapToAccountDto(Account account, AccountInformationDTO accountInformationDTO){
    accountInformationDTO.setAccountNumber(account.getAccountNumber().toString());
    accountInformationDTO.setAccountType(account.getAccountType().toString());
    accountInformationDTO.setBalance(account.getBalance());
    return accountInformationDTO;
  }

  public static Account mapToAccount(AccountInformationDTO accountInformationDTO, Account account){
    account.setAccountNumber(Long.valueOf(accountInformationDTO.getAccountNumber()));
    return account;
  }

}
