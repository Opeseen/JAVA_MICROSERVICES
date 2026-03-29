package com.ubaclone.accounts.controller;

import com.ubaclone.accounts.dto.*;
import com.ubaclone.accounts.service.IAccountService;
import com.ubaclone.accounts.service.ITransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CustomerController {
  private final IAccountService iAccountService;
  private final ITransactionService iTransactionService;

  @Value("${build.version}")
  private String buildVersion;

  @GetMapping("/public/build/info")
  public ResponseEntity<String> getBuildInfo(){
    return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
  }

  @PostMapping("/public/onboard")
  public ResponseEntity<SuccessResponseDTO> createCustomerAndAccount(@Valid @RequestBody CustomerDTO customerDTO){
    iAccountService.createCustomerAndAccount(customerDTO);
    return new ResponseEntity<>(new SuccessResponseDTO(true, "Customer account created successfully",
        LocalDateTime.now()), HttpStatus.CREATED);
  }

  @PostMapping("/public/open")
  public ResponseEntity<SuccessResponseDTO>createAdditionalAccountForCustomer(@Valid @RequestBody AccountDTO accountDTO) {
    iAccountService.createAdditionalAccountForCustomer(accountDTO);
    return new ResponseEntity<>(new SuccessResponseDTO(true, "Customer account created successfully",
        LocalDateTime.now()), HttpStatus.CREATED);
  }


  @GetMapping("/accountDetails")
  public ResponseEntity<AccountInformationDTO> fetchAccount(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
      message = "Account number must be 10 digit") String accountNumber){
    AccountInformationDTO accountInformationDTO = iAccountService.fetchAccountInformation(Long.valueOf(accountNumber));
    return new ResponseEntity<>(accountInformationDTO,HttpStatus.OK);
  }

  @GetMapping("/customerDetails")
  public ResponseEntity<List<AccountInformationDTO>> fetchAllCustomerAccount(
      @Email(message = "Enter a valid email") @RequestParam String email){
    List<AccountInformationDTO> customerAccounts = iAccountService.fetchAllCustomerAccount(email.toLowerCase());
    return  new ResponseEntity<>(customerAccounts, HttpStatus.OK);
  }

  @GetMapping("/verifyAccount")
  public ResponseEntity<String> verifyAccount(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
      message = "Account number must be 10 digit") String accountNumber){
    String userEmail = iAccountService.verifyUserAccount(Long.valueOf(accountNumber));
    return new ResponseEntity<>(userEmail, HttpStatus.OK);
  }

  @GetMapping("/ibanking/verifyAccount")
  public ResponseEntity<Object> verifyAccountForIBankingSetup(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
      message = "Account number must be 10 digit") String accountNumber){
    AccountInformationDTO accountInformationDTO = iAccountService.fetchAccountInformation(Long.valueOf(accountNumber));
    CustomerDTO customerInfo = accountInformationDTO.getCustomer();
    Map<String, String> userInfo = new HashMap<>();
    userInfo.put("firstName", customerInfo.getFirstName());
    userInfo.put("lastName", customerInfo.getLastName());
    return  new ResponseEntity<>(userInfo, HttpStatus.OK);
  }

  @PostMapping("/deposit")
  public ResponseEntity<SuccessResponseDTO>depositFund(@Valid @RequestBody TransactionDTO transactionDTO){
    iTransactionService.deposit(transactionDTO);
    return new ResponseEntity<>(
        new SuccessResponseDTO(true, "deposit processed successfully", LocalDateTime.now()),
        HttpStatus.CREATED
    );
  }

  @PostMapping("/withdrawal")
  public ResponseEntity<SuccessResponseDTO>withdrawalFund(@Valid @RequestBody TransactionDTO transactionDTO){
    iTransactionService.withdrawal(transactionDTO);
    return new ResponseEntity<>(
        new SuccessResponseDTO(true, "withdrawal processed successfully", LocalDateTime.now()),
        HttpStatus.CREATED
    );
  }

  @GetMapping("/fetch")
  public ResponseEntity<List<TransactionDTO>>fetchAccountTransactions(
      @RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
          message = "Account number must be 10 digit")String accountNumber){
    List<TransactionDTO> transactionDTO = iTransactionService.fetchAccountTransaction(Long.parseLong(accountNumber));
    return new ResponseEntity<>(transactionDTO,HttpStatus.OK);
  }

}
