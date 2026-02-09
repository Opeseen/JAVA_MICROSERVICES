package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.constants.AccountConstants;
import com.eazybytes.accounts.dto.AccountDTO;
import com.eazybytes.accounts.dto.AccountsMsgDto;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {
  private final AccountRepository accountRepository;
  private final CustomerRepository customerRepository;
  private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
  private final StreamBridge streamBridge;

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
    Accounts savedAccount = accountRepository.save(createNewAccount(savedCustomer));
    sendCommunication(savedAccount, savedCustomer);
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

  @Override
  public boolean updateCommunicationStatus(Long accountNumber) {
    boolean isUpdated = false;
    if(accountNumber !=null ){
      Accounts accounts = accountRepository.findById(accountNumber).orElseThrow(
          () -> new ResourceNotFoundException("Account", "AccountNumber", accountNumber.toString())
      );
      accounts.setCommunicationSw(true);
      accountRepository.save(accounts);
      isUpdated = true;
    }
    return  isUpdated;
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

  private void sendCommunication(Accounts account, Customer customer) {
    var accountsMsgDto = new AccountsMsgDto(account.getAccountNumber(), customer.getName(),
        customer.getEmail(), customer.getMobileNumber());
    log.info("Sending Communication request for the details: {}", accountsMsgDto);
    var result = streamBridge.send("sendCommunication-out-0", accountsMsgDto);
    log.info("Is the Communication request successfully triggered ? : {}", result);
  }
}
