package com.ubaclone.eservice.service.impl;

import com.ubaclone.eservice.dto.AccountDTO;
import com.ubaclone.eservice.dto.UserDTO;
import com.ubaclone.eservice.entity.Users;
import com.ubaclone.eservice.exception.BadCredentials;
import com.ubaclone.eservice.exception.ResourceAlreadyExists;
import com.ubaclone.eservice.mapper.UserMapper;
import com.ubaclone.eservice.repository.UserRepository;
import com.ubaclone.eservice.service.ICustomerService;
import com.ubaclone.eservice.service.IEservice;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EserviceImpl implements IEservice {
  private final ICustomerService iCustomerService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void register(UserDTO userDTO) {
    // check if the username already exists
    if(userRepository.existsByUsername(userDTO.getUsername())){
      throw new ResourceAlreadyExists("User", "username", userDTO.getUsername());
    }
    // fetch the customer account details
    AccountDTO accountDTO = iCustomerService.fetchAccountInformation(userDTO.getAccountNumber());
    Long customerId = accountDTO.getCustomer().getCustomerId();
    // check if the customer is already registered
    if(userRepository.existsByCustomerId(customerId)){
      throw new RuntimeException("User already registered for this customer");
    }
    // map to the user object and save the user record to the db
    Users users = UserMapper.mapToUser(userDTO, new Users());
    users.setCustomerId(customerId);
    users.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    userRepository.save(users);
  }

  @Override
  public void login(String username, String password) {
    Users user = userRepository.findByUsername(username).orElseThrow(
        () -> new BadCredentials("Invalid username or password")
    );
    if(!passwordEncoder.matches(password, user.getPassword())){
      throw new BadCredentials("Invalid username or password");
    }
  }
}
