package com.ubaclone.accounts.service;

import com.ubaclone.accounts.dto.AccountDTO;
import com.ubaclone.accounts.dto.CustomerDTO;
import com.ubaclone.accounts.entity.Account;

import java.util.List;

public interface IAccountService {
  void createCustomerAndAccount(CustomerDTO customerDTO);

  /**
   * Method to create an account for an existing customer
   *
   * @param bvn
   */
  void createAccount(Long bvn);
  AccountDTO fetchAccountInformation(Long accountNumber);
  List<AccountDTO> fetchAllCustomerAccount(String email);
  Account findByAccountNumber(Long accountNumber);
}
