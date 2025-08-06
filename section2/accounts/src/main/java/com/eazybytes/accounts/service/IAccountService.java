package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDTO;

public interface IAccountService {
  /**
   *
   * @param customerDTO - CustomerDTO Object
   */
  void createAccount(CustomerDTO customerDTO);
}
