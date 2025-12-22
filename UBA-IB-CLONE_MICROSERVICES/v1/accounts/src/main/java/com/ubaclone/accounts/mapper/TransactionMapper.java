package com.ubaclone.accounts.mapper;

import com.ubaclone.accounts.dto.TransactionDTO;
import com.ubaclone.accounts.entity.AccountTransaction;

import java.time.LocalDateTime;

public class TransactionMapper {
  public static TransactionDTO mapToTransactionDTO(
      AccountTransaction accountTransaction, TransactionDTO transactionDTO){
    transactionDTO.setAccountNumber(accountTransaction.getAccountNumber().toString());
    transactionDTO.setTransactionSummary(accountTransaction.getTransactionSummary());
    transactionDTO.setTransactionAmount(accountTransaction.getTransactionAmount().toString());
    return transactionDTO;
  }

  public static AccountTransaction mapToAccountTransaction(
      TransactionDTO transactionDTO, AccountTransaction accountTransaction){
    accountTransaction.setTransactionSummary(transactionDTO.getTransactionSummary());
    accountTransaction.setTransactionAmount(Integer.parseInt(transactionDTO.getTransactionAmount()));
    accountTransaction.setAccountNumber(Long.parseLong(transactionDTO.getAccountNumber()));
    accountTransaction.setTransactionDate(LocalDateTime.now());
    return accountTransaction;
  }

}
