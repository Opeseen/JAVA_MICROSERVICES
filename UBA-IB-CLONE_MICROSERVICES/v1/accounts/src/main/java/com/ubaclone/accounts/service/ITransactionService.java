package com.ubaclone.accounts.service;

import com.ubaclone.accounts.dto.TransactionDTO;

import java.util.List;

public interface ITransactionService {
  void withdrawal(TransactionDTO transactionDTO);
  void deposit(TransactionDTO transactionDTO);
  List<TransactionDTO> fetchAccountTransaction(Long accountNumber);
}
