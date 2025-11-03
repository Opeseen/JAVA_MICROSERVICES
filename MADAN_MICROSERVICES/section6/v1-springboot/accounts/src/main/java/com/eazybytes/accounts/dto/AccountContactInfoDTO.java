package com.eazybytes.accounts.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

// this will create a bean, so it can be injectable
@ConfigurationProperties(prefix = "accounts")
public record AccountContactInfoDTO(String message, Map<String, String> contactDetails, List<String> onCallSupport) {
}
