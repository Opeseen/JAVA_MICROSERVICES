package com.ubaclone.accounts.service.impl;

import com.ubaclone.accounts.dto.TransactionDTO;
import com.ubaclone.accounts.entity.Account;
import com.ubaclone.accounts.entity.AccountTransaction;
import com.ubaclone.accounts.mapper.TransactionMapper;
import com.ubaclone.accounts.repository.AccountRepository;
import com.ubaclone.accounts.repository.TransactionRepository;
import com.ubaclone.accounts.service.IAccountService;
import com.ubaclone.accounts.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements ITransactionService {
  private final TransactionRepository transactionRepository;
  private final AccountRepository accountRepository;
  private final IAccountService iAccountService;

  @Override
  @Transactional
  public void withdrawal(TransactionDTO txDto) {
    // get the current balance;
    Account account = checkAccountBalance(Long.parseLong(txDto.getAccountNumber()), txDto.getTransactionAmount());
    // calculate the new balance
    int newBal = calculateClosingBalance("DEBIT", account.getBalance(),
        Integer.parseInt(txDto.getTransactionAmount()));
    // record the customer transaction
    AccountTransaction accountTx = TransactionMapper.mapToAccountTransaction(txDto, new AccountTransaction());
    accountTx.setTransactionType("DEBIT");
    accountTx.setClosingBalance(newBal);
    accountTx.setCustomerId(account.getCustomerId());
    // save the customer transactions
    recordTransaction(accountTx);
    // update and save the customer account balance
    account.setBalance(newBal);
    accountRepository.save(account);
  }

  @Override
  @Transactional
  public void deposit(TransactionDTO txDto) {
    // fetch the account information
    Account account = iAccountService.findByAccountNumber(Long.parseLong(txDto.getAccountNumber()));
    // calculate the new balance
    int newBal = calculateClosingBalance("CREDIT", account.getBalance()
        ,Integer.parseInt(txDto.getTransactionAmount()));
    // record the customer transaction
    AccountTransaction accountTx = TransactionMapper.mapToAccountTransaction(txDto, new AccountTransaction());
    accountTx.setTransactionType("CREDIT");
    accountTx.setClosingBalance(newBal);
    accountTx.setCustomerId(account.getCustomerId());
    // save the customer transactions
    recordTransaction(accountTx);
    // update and save the customer account balance
    account.setBalance(newBal);
    accountRepository.save(account);
  }

  @Override
  public List<TransactionDTO> fetchAccountTransaction(Long accountNumber) {
    // look up the db for all the customer transactions
    List<AccountTransaction> accountTransactions = transactionRepository.findByAccountNumber(accountNumber);
    return accountTransactions.stream().map(tx -> {
      TransactionDTO dto = new TransactionDTO();
      TransactionDTO accTxDto = TransactionMapper.mapToTransactionDTO(tx, dto);
      accTxDto.setClosingBalance(tx.getClosingBalance().toString());
      return accTxDto;
    }).toList();
  }

  // Get last transaction to derive previous closing balance
  private Integer getPreviousClosingBalance(Long accountNumber){
    return transactionRepository.findFirstByAccountNumberOrderByTransactionDateDesc(accountNumber)
        .map(AccountTransaction::getClosingBalance)
        .orElse(0);
  }

  // Compute new closing balance
  private Integer calculateClosingBalance(String type, int prevBal, int amount){
    return switch (type.toUpperCase()) {
      case "CREDIT" -> prevBal + amount;
      case "DEBIT" -> prevBal - amount;
      default -> throw new IllegalArgumentException
          (String.format("Invalid transaction type: '%s' ", type));
    };
  }

  // record the customer transaction
  private void recordTransaction(AccountTransaction accTx){
    transactionRepository.save(accTx);
  }

  private Account checkAccountBalance(Long accountNumber, String txAmount){
    Account account = iAccountService.findByAccountNumber(accountNumber);
    // validate withdrawal balance
    if(Integer.parseInt(txAmount) > account.getBalance()){
      throw new IllegalArgumentException("Insufficient balance for withdrawal");
    }
    return account;
  }

}
