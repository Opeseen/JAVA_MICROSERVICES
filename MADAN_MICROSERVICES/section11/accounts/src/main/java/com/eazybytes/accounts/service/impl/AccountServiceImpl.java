package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.constants.AccountConstants;
import com.eazybytes.accounts.dto.AccountDTO;
import com.eazybytes.accounts.dto.CustomerDTO;
import com.eazybytes.accounts.entities.Accounts;
import com.eazybytes.accounts.entities.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {
  private final AccountRepository accountRepository;
  private final CustomerRepository customerRepository;

  /**
   * @param customerDTO - CustomerDTO Object
   */
  @Override
  public void createAccount(CustomerDTO customerDTO) {
    Customer customer = CustomerMapper.mapToCustomer(customerDTO, new Customer());
    Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDTO.getMobileNumber());
    if(optionalCustomer.isPresent()){
      throw  new CustomerAlreadyExistsException("Customer already registered with the given mobile number: "
          + customerDTO.getMobileNumber());
    }
    Customer savedCustomer = customerRepository.save(customer);
    accountRepository.save(createNewAccount(savedCustomer));
  }

  @Override
  public CustomerDTO fetchAccount(String mobileNumber) {
    Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
        () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
    );
    Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
        () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
    );
    CustomerDTO customerDTO = CustomerMapper.mapToCustomerDTO(customer, new CustomerDTO());
    customerDTO.setAccount(AccountMapper.mapToAccountDTO(accounts, new AccountDTO()));
    return customerDTO;
  }

  @Override
  public boolean updateAccount(CustomerDTO customerDTO) {
    boolean isUpdated = false;
    AccountDTO accountDTO = customerDTO.getAccount();
    if(accountDTO !=null ){
      Accounts accounts = accountRepository.findById(accountDTO.getAccountNumber()).orElseThrow(
          () -> new ResourceNotFoundException("Account", "AccountNumber", Long.toString(accountDTO.getAccountNumber()))
      );
      AccountMapper.mapToAccount(accountDTO, accounts);
      accounts = accountRepository.save(accounts);

      Long customerId = accounts.getCustomerId();
      Customer customer = customerRepository.findById(customerId).orElseThrow(
          () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
      );
      CustomerMapper.mapToCustomer(customerDTO,customer);
      customerRepository.save(customer);
      isUpdated = true;
    }
    return  isUpdated;
  }

  @Override
  public boolean deleteAccount(String mobileNumber) {
    Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
        () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
    );
    accountRepository.deleteByCustomerId(customer.getCustomerId());
    customerRepository.deleteById(customer.getCustomerId());
    return true;
  }

  /**
   *
   * @param customer - Customer Object
   * @return the new account details
   */
  private Accounts createNewAccount(Customer customer) {
    Accounts newAccount = new Accounts();
    newAccount.setCustomerId(customer.getCustomerId());
    long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

    newAccount.setAccountNumber(randomAccNumber);
    newAccount.setAccountType(AccountConstants.SAVINGS);
    newAccount.setBranchAddress(AccountConstants.ADDRESS);
    return newAccount;
  }


}
