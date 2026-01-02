package com.ubaclone.accounts.service.impl;

import com.ubaclone.accounts.dto.AccountDTO;
import com.ubaclone.accounts.dto.CustomerDTO;
import com.ubaclone.accounts.entity.Account;
import com.ubaclone.accounts.entity.Customer;
import com.ubaclone.accounts.exception.ResourceAlreadyExists;
import com.ubaclone.accounts.exception.ResourceNotFound;
import com.ubaclone.accounts.mapper.AccountMapper;
import com.ubaclone.accounts.mapper.CustomerMapper;
import com.ubaclone.accounts.repository.AccountRepository;
import com.ubaclone.accounts.repository.CustomerRepository;
import com.ubaclone.accounts.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

  private final AccountRepository accountRepository;
  private final CustomerRepository customerRepository;

  @Override
  @Transactional
  public void createCustomerAndAccount(CustomerDTO customerDTO) {
    // map the customer details first
    Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());
    // check if the customer already exists
    Optional<Customer> searchCustomer = customerRepository.findByEmail(customer.getEmail());
    if(searchCustomer.isPresent()){
      throw new ResourceAlreadyExists("Customer", "email", customer.getEmail());
    }
    // check if the BVN already exists for a customer
    customerRepository.findByBvn(Long.parseLong(customerDTO.getBvn())).ifPresent(
        c -> {
          throw new ResourceAlreadyExists("Customer", "bvn", customerDTO.getBvn());
        }
    );
    // save the customer data to the db
    Customer newCustomer = customerRepository.save(customer);
    // create the customer account details
    Account accountInformation = createNewAccount(newCustomer);
    accountRepository.save(accountInformation);
  }
  
  @Override
  public void createAccount(Long bvn) {
    // get the customer information
    Customer customer = customerRepository.findByBvn(bvn).orElseThrow(
        () -> new ResourceNotFound("Customer", "BVN", bvn.toString())
    );
    // create the new account information
    Account accountInformation = createNewAccount(customer);
    accountRepository.save(accountInformation);
  }

  @Override
  public AccountDTO fetchAccountInformation(Long accountNumber) {
    // check if the account information exists
    Account account = accountRepository.findById(accountNumber).orElseThrow(
        () -> new ResourceNotFound("Account", "AccountNumber", accountNumber.toString())
    );
    // get the customer information
    Customer customer = customerRepository.findById(account.getCustomerId()).orElseThrow(
        () -> new ResourceNotFound("Customer", "customerId", account.getCustomerId().toString())
    );
    // return the account and customer information
    AccountDTO accountDTO = AccountMapper.mapToAccountDto(account, new AccountDTO());
    CustomerDTO customerDTO = CustomerMapper.matToCustomerDto(customer, new CustomerDTO());
    // manually set the customer id as part of the response
    customerDTO.setCustomerId(customer.getCustomerId());
    accountDTO.setCustomer(customerDTO);
    return accountDTO;
  }

  @Override
  public List<AccountDTO> fetchAllCustomerAccount(String email) {
    // fetch the customer id from the customer details
    Customer customer = findCustomerByEmail(email);
    List<Account> accounts = accountRepository.findByCustomerId(customer.getCustomerId());
    return  accounts.stream().map(acc -> {
      AccountDTO dto = new AccountDTO();
      return AccountMapper.mapToAccountDto(acc, dto);
    }).toList();
  }

  @Override
  public Account findByAccountNumber(Long accountNumber) {
    return accountRepository.findById(accountNumber).orElseThrow(
        () -> new ResourceNotFound("Account", "AccountNumber", accountNumber.toString())
    );
  }

  // helper method to create the account information
  private Account createNewAccount(Customer customer){
    Account newAccount = new Account();
    Long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
    newAccount.setAccountNumber(randomAccNumber);
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

}
