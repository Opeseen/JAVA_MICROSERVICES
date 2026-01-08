package com.ubaclone.eservice.service;

import com.ubaclone.eservice.dto.AccountDTO;

public interface ICustomerService {

  AccountDTO fetchAccountInformation(String accountNumber);
}
