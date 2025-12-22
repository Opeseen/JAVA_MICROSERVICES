package com.ubaclone.eservice.service.impl;

import com.ubaclone.eservice.dto.AccountDTO;
import com.ubaclone.eservice.service.ICustomerService;
import com.ubaclone.eservice.service.client.AccountFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
  private final AccountFeignClient accountFeignClient;

  @Override
  public AccountDTO fetchAccountInformation(String accountNumber) {
    ResponseEntity<AccountDTO> accountDTOResponseEntity =  accountFeignClient.fetchAccountInformation(accountNumber);
    return accountDTOResponseEntity.getBody();
  }
}
