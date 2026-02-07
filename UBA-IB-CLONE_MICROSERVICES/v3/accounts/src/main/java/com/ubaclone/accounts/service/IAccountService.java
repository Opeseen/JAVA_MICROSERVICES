package com.ubaclone.accounts.service;

import com.ubaclone.accounts.dto.AccountDTO;
import com.ubaclone.accounts.dto.CustomerDTO;
import com.ubaclone.accounts.entity.Account;

import java.util.List;

public interface IAccountService {
  void createCustomerAndAccount(CustomerDTO customerDTO);
  AccountDTO fetchAccountInformation(Long accountNumber); // returns customer and account info
  List<AccountDTO> fetchAllCustomerAccount(String email);
  Account findByAccountNumber(Long accountNumber); // returns account info
  void verifyUserAccount(Long accountNumber); // verify if account exists
}
