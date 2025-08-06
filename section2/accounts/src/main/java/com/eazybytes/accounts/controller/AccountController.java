package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountConstants;
import com.eazybytes.accounts.dto.CustomerDTO;
import com.eazybytes.accounts.dto.ResponseDTO;
import com.eazybytes.accounts.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class AccountController {

  private final IAccountService iAccountService;

  @PostMapping("/create")
  public ResponseEntity<ResponseDTO> createAccount(@RequestBody CustomerDTO customerDTO){
    iAccountService.createAccount(customerDTO);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ResponseDTO(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
  }
}
