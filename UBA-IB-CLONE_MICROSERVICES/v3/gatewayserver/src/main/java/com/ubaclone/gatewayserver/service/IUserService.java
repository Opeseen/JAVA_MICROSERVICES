package com.ubaclone.gatewayserver.service;

import com.ubaclone.gatewayserver.dto.UserRecord;

public interface IUserService {
  // this method creates a new user in the keycloak auth server
  void createUser(UserRecord userRecord);
}
