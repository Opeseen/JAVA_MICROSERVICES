package com.ubaclone.accounts.controller;

import com.ubaclone.accounts.dto.SuccessResponseDTO;
import com.ubaclone.accounts.dto.TransactionDTO;
import com.ubaclone.accounts.service.ITransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class TransactionController {
  private final ITransactionService iTransactionService;

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
