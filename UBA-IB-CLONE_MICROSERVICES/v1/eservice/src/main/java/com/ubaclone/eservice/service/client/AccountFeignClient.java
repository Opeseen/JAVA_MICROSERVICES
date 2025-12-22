package com.ubaclone.eservice.service.client;

import com.ubaclone.eservice.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("accounts")
public interface AccountFeignClient {

  @GetMapping(value = "/api/fetch/account", consumes = "application/json")
  public ResponseEntity<AccountDTO> fetchAccountInformation(@RequestParam String accountNumber);

}
