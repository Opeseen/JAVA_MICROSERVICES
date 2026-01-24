package com.ubaclone.accounts.mapper;

import com.ubaclone.accounts.dto.CustomerDTO;
import com.ubaclone.accounts.entity.Customer;

public class CustomerMapper {
  public static CustomerDTO matToCustomerDto(Customer customer, CustomerDTO customerDTO){
    customerDTO.setEmail(customer.getEmail().toLowerCase());
    customerDTO.setFullName(customer.getFullName());
    customerDTO.setBvn(customer.getBvn().toString());
    customerDTO.setPhone(customer.getPhone());
    customerDTO.setAccountType(customer.getAccountType());
    customerDTO.setBranchAddress(customer.getBranchAddress());
    return customerDTO;
  }

  public static Customer mapToCustomer(CustomerDTO customerDTO, Customer customer){
    customer.setEmail(customerDTO.getEmail().toLowerCase());
    customer.setBvn(Long.valueOf(customerDTO.getBvn()));
    customer.setFullName(customerDTO.getFullName());
    customer.setPhone(customerDTO.getPhone());
    customer.setAccountType(customerDTO.getAccountType());
    customer.setBranchAddress(customerDTO.getBranchAddress());
    return customer;
  }
}
