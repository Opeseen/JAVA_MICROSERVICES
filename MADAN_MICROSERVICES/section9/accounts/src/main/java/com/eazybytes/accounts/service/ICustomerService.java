package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDetailsDTO;

public interface ICustomerService {
  /**
   *
   * @param mobileNumber Input Mobile Number
   * @return Customer Details based on a given mobileNumber
   */
  CustomerDetailsDTO fetchCustomerDetails(String mobileNumber);
}
