package com.ubaclone.accounts.controller;

import com.ubaclone.accounts.dto.AccountDTO;
import com.ubaclone.accounts.dto.CustomerDTO;
import com.ubaclone.accounts.dto.SuccessResponseDTO;
import com.ubaclone.accounts.service.IAccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/account", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountController {
  private final IAccountService iAccountService;

  @PostMapping("/create/customer-account")
  public ResponseEntity<SuccessResponseDTO> createCustomerAndAccount(@Valid @RequestBody CustomerDTO customerDTO){
    iAccountService.createCustomerAndAccount(customerDTO);
    return new ResponseEntity<>(new SuccessResponseDTO(true, "Customer account created successfully",
        LocalDateTime.now()), HttpStatus.CREATED);
  }

  @PostMapping("/create/account")
  public ResponseEntity<SuccessResponseDTO> createAccount(
      @RequestBody Map<String, String>body) {
    String bvn = body.get("bvn");
    if (!bvn.matches("^[0-9]{11}$")) {
      throw new IllegalArgumentException("BVN must be exactly 11 digits");
    }
    iAccountService.createAccount(Long.valueOf(bvn));
    return new ResponseEntity<>(new SuccessResponseDTO(true, "Account created successfully",
        LocalDateTime.now()), HttpStatus.CREATED);
  }

  @GetMapping("/fetch")
  public ResponseEntity<SuccessResponseDTO> fetchAccount(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
      message = "Account number must be 10 digit") String accountNumber){
    AccountDTO accountDTO = iAccountService.fetchAccount(Long.valueOf(accountNumber));
    return new ResponseEntity<>(
        new SuccessResponseDTO(true, accountDTO, LocalDateTime.now()), HttpStatus.OK
    );
  }

  @GetMapping("fetch/customer")
  public ResponseEntity<SuccessResponseDTO> fetchAllCustomerAccount(
      @Email(message = "Enter a valid email") @RequestParam String email){
    List<AccountDTO> customerAccounts = iAccountService.fetchAllCustomerAccount(email.toLowerCase());
    return  new ResponseEntity<>(
        new SuccessResponseDTO(true, customerAccounts, LocalDateTime.now()), HttpStatus.OK
    );
  }
}
