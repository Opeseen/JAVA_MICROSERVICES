package com.ubaclone.eservice.service;

import com.ubaclone.eservice.dto.UserDTO;

public interface IEservice {
  void register(UserDTO userDTO);
  void login (String username, String password);
}
