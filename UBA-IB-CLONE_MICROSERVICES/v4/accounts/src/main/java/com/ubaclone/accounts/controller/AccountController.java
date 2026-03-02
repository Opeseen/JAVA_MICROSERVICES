package com.ubaclone.accounts.controller;

import com.ubaclone.accounts.dto.AccountDTO;
import com.ubaclone.accounts.dto.CustomerDTO;
import com.ubaclone.accounts.dto.SuccessResponseDTO;
import com.ubaclone.accounts.service.IAccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountController {
  private final IAccountService iAccountService;
  private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

  @Value("${build.version}")
  private String buildVersion;

  @PostMapping("/public/create")
  public ResponseEntity<SuccessResponseDTO> createCustomerAndAccount(@Valid @RequestBody CustomerDTO customerDTO){
    iAccountService.createCustomerAndAccount(customerDTO);
    return new ResponseEntity<>(new SuccessResponseDTO(true, "Customer account created successfully",
        LocalDateTime.now()), HttpStatus.CREATED);
  }


  @GetMapping("/accountDetails")
  public ResponseEntity<AccountDTO> fetchAccount(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
      message = "Account number must be 10 digit") String accountNumber){
    AccountDTO accountDTO = iAccountService.fetchAccountInformation(Long.valueOf(accountNumber));
    return new ResponseEntity<>(accountDTO,HttpStatus.OK);
  }

  @GetMapping("/customerDetails")
  public ResponseEntity<List<AccountDTO>> fetchAllCustomerAccount(
      @Email(message = "Enter a valid email") @RequestParam String email){
    List<AccountDTO> customerAccounts = iAccountService.fetchAllCustomerAccount(email.toLowerCase());
    return  new ResponseEntity<>(customerAccounts, HttpStatus.OK);
  }

  @GetMapping("/verifyAccount")
  public ResponseEntity<Boolean> verifyAccount(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
      message = "Account number must be 10 digit") String accountNumber){
    logger.debug("verifyUserAccountDetails method start");
    iAccountService.verifyUserAccount(Long.valueOf(accountNumber));
    logger.debug("verifyUserAccountDetails method end");
    return new ResponseEntity<>(true, HttpStatus.OK);
  }

  @GetMapping("/build/info")
  public ResponseEntity<String> getBuildInfo(){
    return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
  }
}
