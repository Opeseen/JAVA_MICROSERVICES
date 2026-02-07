package com.ubaclone.eservice.service;

import com.ubaclone.eservice.dto.UserRecord;

public interface IUserService {
  void registerUser(UserRecord userRecord); // this method creates a new user in the keycloak auth server
}
