package com.ubaclone.accounts.mapper;

import com.ubaclone.accounts.dto.CustomerDTO;
import com.ubaclone.accounts.entity.Customer;

public class CustomerMapper {
  public static CustomerDTO matToCustomerDto(Customer customer, CustomerDTO customerDTO){
    customerDTO.setEmail(customer.getEmail().toLowerCase());
    customerDTO.setFirstName(customer.getFirstName());
    customerDTO.setLastName(customer.getLastName());
    customerDTO.setBvn(customer.getBvn().toString());
    customerDTO.setPhone(customer.getPhone());
    customerDTO.setAccountType(customer.getAccountType());
    return customerDTO;
  }

  public static Customer mapToCustomer(CustomerDTO customerDTO, Customer customer){
    customer.setEmail(customerDTO.getEmail().toLowerCase());
    customer.setBvn(Long.valueOf(customerDTO.getBvn()));
    customer.setFirstName(customerDTO.getFirstName());
    customer.setLastName(customerDTO.getLastName());
    customer.setPhone(customerDTO.getPhone());
    customer.setAccountType(customerDTO.getAccountType());
    return customer;
  }
}
