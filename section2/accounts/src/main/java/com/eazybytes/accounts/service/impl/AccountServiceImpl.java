package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.constants.AccountConstants;
import com.eazybytes.accounts.dto.CustomerDTO;
import com.eazybytes.accounts.entities.Accounts;
import com.eazybytes.accounts.entities.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
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
    customer.setCreatedAt(LocalDateTime.now());
    customer.setCreatedBy("Anonymous");
    Customer savedCustomer = customerRepository.save(customer);
    accountRepository.save(createNewAccount(savedCustomer));
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
    newAccount.setCreatedAt(LocalDateTime.now());
    newAccount.setCreatedBy("Anonymous");
    return newAccount;
  }
}
