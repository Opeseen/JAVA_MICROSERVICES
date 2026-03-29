package com.ubaclone.eservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "accounts", fallbackFactory = AccountsFallbackFactory.class)
public interface AccountFeignClient {

  /**
    * @return userEmail
   */
  @GetMapping(value = "/api/verifyAccount", consumes = "application/json")
    String verifyUserAccount(@RequestParam String accountNumber);
}
