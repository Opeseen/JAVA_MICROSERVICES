package com.ubaclone.eservice.service.client;

import feign.FeignException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class AccountsFallbackFactory implements FallbackFactory<AccountFeignClient> {
  @Override
  public AccountFeignClient create(Throwable cause) {

    if (cause instanceof FeignException.NotFound) {
      throw (FeignException.NotFound) cause;
    }

    return ignored -> {
      throw new RuntimeException("Account service is currently unavailable");
    };

  }
}
