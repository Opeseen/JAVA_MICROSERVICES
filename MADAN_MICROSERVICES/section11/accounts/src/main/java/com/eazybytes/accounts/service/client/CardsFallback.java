package com.eazybytes.accounts.service.client;

import com.eazybytes.accounts.dto.CardsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient{
  @Override
  public ResponseEntity<CardsDTO> fetchCardDetails(String correlationId, String mobileNumber) {
    return null;
  }
}
