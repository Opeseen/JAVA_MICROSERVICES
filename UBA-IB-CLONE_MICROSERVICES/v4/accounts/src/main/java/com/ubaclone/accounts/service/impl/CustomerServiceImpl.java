package com.ubaclone.accounts.service.impl;

import com.ubaclone.accounts.dto.*;
import com.ubaclone.accounts.entity.Account;
import com.ubaclone.accounts.entity.AccountTransaction;
import com.ubaclone.accounts.entity.Customer;
import com.ubaclone.accounts.enumeration.AccountType;
import com.ubaclone.accounts.enumeration.WithdrawalType;
import com.ubaclone.accounts.exception.BadRequestException;
import com.ubaclone.accounts.exception.ResourceAlreadyExists;
import com.ubaclone.accounts.exception.ResourceNotFound;
import com.ubaclone.accounts.mapper.AccountMapper;
import com.ubaclone.accounts.mapper.CustomerMapper;
import com.ubaclone.accounts.mapper.TransactionMapper;
import com.ubaclone.accounts.repository.AccountRepository;
import com.ubaclone.accounts.repository.CustomerRepository;
import com.ubaclone.accounts.repository.TransactionRepository;
import com.ubaclone.accounts.service.IAccountService;
import com.ubaclone.accounts.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements IAccountService, ITransactionService {

  private final AccountRepository accountRepository;
  private final CustomerRepository customerRepository;
  private final TransactionRepository transactionRepository;
  private final StreamBridge streamBridge;

  /**
   * Account Service Section
   */
  @Override
  @Transactional
  public void createCustomerAndAccount(CustomerDTO customerDTO) {
    // map the customer details first
    Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());
    // check if the email already exists
    customerRepository.findByEmail(customer.getEmail()).ifPresent(
        c -> {
          throw new ResourceAlreadyExists("Customer", "email", customer.getEmail());
        }
    );
    // check if the BVN already exists for a customer
    customerRepository.findByBvn(Long.parseLong(customerDTO.getBvn())).ifPresent(
        c -> {
          throw new ResourceAlreadyExists("Customer", "bvn", customerDTO.getBvn());
        }
    );
    // validate account type
    AccountType accountType = validateAccountType(customerDTO.getAccountType());
    // 1. save the customer data to the db
    Customer newCustomer = customerRepository.save(customer);
    // 2. create the customer account details
    Account accountInformation = createNewAccount(newCustomer, accountType);
    accountRepository.save(accountInformation);
    sendCommunication(accountInformation, newCustomer);
  }

  @Override
  public void createAdditionalAccountForCustomer(AccountDTO accountDTO) {
    Customer customer = customerRepository.findByBvn(Long.parseLong(accountDTO.getBvn())).orElseThrow(
        () -> new ResourceNotFound("Customer", "BVN", accountDTO.getBvn())
    );
    // validate account type
    AccountType accountType = validateAccountType(accountDTO.getAccountType());

    Account accountInformation = createNewAccount(customer, accountType);
    accountRepository.save(accountInformation);
    sendCommunication(accountInformation, customer);
  }


  @Override
  public AccountInformationDTO fetchAccountInformation(Long accountNumber) {
    // check if the account information exists
    Account account = accountRepository.findById(accountNumber).orElseThrow(
        () -> new ResourceNotFound("Account", "AccountNumber", accountNumber.toString())
    );
    // get the customer information
    Customer customer = customerRepository.findById(account.getCustomerId()).orElseThrow(
        () -> new ResourceNotFound("Customer", "customerId", account.getCustomerId().toString())
    );
    // return the account and customer information
    AccountInformationDTO accountInformationDTO = AccountMapper.mapToAccountDto(account, new AccountInformationDTO());
    CustomerDTO customerDTO = CustomerMapper.matToCustomerDto(customer, new CustomerDTO());
    // manually set the customer id as part of the response
    customerDTO.setCustomerId(customer.getCustomerId());
    accountInformationDTO.setCustomer(customerDTO);
    return accountInformationDTO;
  }

  @Override
  public List<AccountInformationDTO> fetchAllCustomerAccount(String email) {
    // fetch the customer id from the customer details
    Customer customer = findCustomerByEmail(email);
    List<Account> accounts = accountRepository.findByCustomerId(customer.getCustomerId());
    return  accounts.stream().map(acc -> {
      AccountInformationDTO dto = new AccountInformationDTO();
      return AccountMapper.mapToAccountDto(acc, dto);
    }).toList();
  }

  @Override
  public Account findByAccountNumber(Long accountNumber) {
    return accountRepository.findById(accountNumber).orElseThrow(
        () -> new ResourceNotFound("Account", "AccountNumber", accountNumber.toString())
    );
  }

  @Override
  public String verifyUserAccount(Long accountNumber) {
    AccountInformationDTO userDetails = fetchAccountInformation(accountNumber);
    return userDetails.getCustomer().getEmail();
  }

  /**
    * Transaction Service Section
   */

  @Override
  @Transactional
  public void withdrawal(TransactionDTO txDto) {
    // get the current balance;
    Account account = checkAccountBalance(Long.parseLong(txDto.getAccountNumber()), txDto.getTransactionAmount());
    // calculate the new balance
    int newBal = calculateClosingBalance(WithdrawalType.DEBIT, account.getBalance(),
        Integer.parseInt(txDto.getTransactionAmount()));
    // record the customer transaction
    AccountTransaction accountTx = TransactionMapper.mapToAccountTransaction(txDto, new AccountTransaction());
    accountTx.setTransactionType(WithdrawalType.DEBIT.toString());
    accountTx.setClosingBalance(newBal);
    accountTx.setCustomerId(account.getCustomerId());
    // update and save the customer account balance
    account.setBalance(newBal);
    // 1. save the customer transactions
    transactionRepository.save(accountTx);
    // 2. save new balance details
    accountRepository.save(account);
  }

  @Override
  @Transactional
  public void deposit(TransactionDTO txDto) {
    // fetch the account information
    Account account = findByAccountNumber(Long.parseLong(txDto.getAccountNumber()));
    // calculate the new balance
    int newBal = calculateClosingBalance(WithdrawalType.CREDIT, account.getBalance()
        ,Integer.parseInt(txDto.getTransactionAmount()));
    // record the customer transaction
    AccountTransaction accountTx = TransactionMapper.mapToAccountTransaction(txDto, new AccountTransaction());
    accountTx.setTransactionType(WithdrawalType.CREDIT.toString());
    accountTx.setClosingBalance(newBal);
    accountTx.setCustomerId(account.getCustomerId());
    // update and save the customer account balance
    account.setBalance(newBal);
    // 1. save the customer transactions
    transactionRepository.save(accountTx);
    // 2. save new balance details
    accountRepository.save(account);
  }

  /*
    * Private helpers method
   */

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

  // helper method to compute new closing balance
  private Integer calculateClosingBalance(WithdrawalType type, int prevBal, int amount){
    return switch (type) {
      case CREDIT -> prevBal + amount;
      case DEBIT -> prevBal - amount;
    };
  }

  private AccountType validateAccountType(String accountType){
    return switch (accountType.toUpperCase()) {
      case "SAVINGS" -> AccountType.SAVINGS;
      case "CURRENT" -> AccountType.CURRENT;
      default -> throw new BadRequestException(
          "Invalid account type entered: Must be SAVINGS or CURRENT"
      );
    };
  }

  // helper method to create the account information
  private Account createNewAccount(Customer customer, AccountType accountType){
    Account newAccount = new Account();
    Long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
    newAccount.setAccountNumber(randomAccNumber);
    newAccount.setAccountType(accountType);
    newAccount.setCustomerId(customer.getCustomerId());
    newAccount.setBalance(0);
    return newAccount;
  }

  // helper method to find a customer by its email
  private Customer findCustomerByEmail(String email){
    Optional<Customer> customer = customerRepository.findByEmail(email);
    if(customer.isEmpty()){
      throw new ResourceNotFound("Customer", "Email", email);
    }
    return customer.get();

  }

  private Account checkAccountBalance(Long accountNumber, String txAmount){
    Account account = findByAccountNumber(accountNumber);
    // validate withdrawal balance
    if(Integer.parseInt(txAmount) > account.getBalance()){
      throw new BadRequestException("Insufficient balance for withdrawal");
    }
    return account;
  }

  // helper method to send email communication to queue
  private void sendCommunication(Account account, Customer customer){
    String fullName = customer.getFirstName() + " " + customer.getLastName();
    AccountMessageDto accountMessageDto = new AccountMessageDto
        (account.getAccountNumber(), fullName, customer.getEmail());
    boolean result = streamBridge.send("sendCommunication-out-0", accountMessageDto);
    log.debug("Is the communication request successfully triggered: {}", result);
  }
}
