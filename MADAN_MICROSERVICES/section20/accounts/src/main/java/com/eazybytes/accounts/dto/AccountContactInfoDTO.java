package com.eazybytes.accounts.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

// this will create a bean, so it can be injectable
@ConfigurationProperties(prefix = "accounts")
@Getter @Setter
public class AccountContactInfoDTO{
  private String message;
  private Map<String, String> contactDetails;
  private List<String> onCallSupport;
}
