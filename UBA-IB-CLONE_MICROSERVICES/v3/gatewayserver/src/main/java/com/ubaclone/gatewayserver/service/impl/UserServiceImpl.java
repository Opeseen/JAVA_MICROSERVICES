package com.ubaclone.gatewayserver.service.impl;

import com.ubaclone.gatewayserver.dto.UserRecord;
import com.ubaclone.gatewayserver.service.IUserService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

  @Value("${keycloak.realm}")
  private String realm;

  private final Keycloak keycloak;

  @Override
  public void createUser(UserRecord userRecord) {
    // set the user details
    UserRepresentation newUser = new UserRepresentation();
    newUser.setUsername(userRecord.username());
    newUser.setFirstName(userRecord.firstname());
    newUser.setLastName(userRecord.lastname());
    newUser.setEmail(userRecord.email());
    newUser.setEnabled(true);
    newUser.setEmailVerified(true);

    // set the user password
    CredentialRepresentation userCreds = new CredentialRepresentation();
    userCreds.setType(CredentialRepresentation.PASSWORD);
    userCreds.setTemporary(false);
    userCreds.setValue(userRecord.password());
    List<CredentialRepresentation> credInfo = new ArrayList<>();
    credInfo.add(userCreds);
    newUser.setCredentials(credInfo);

    // check for error status code
    Response response = keycloak.realm(realm).users().create(newUser);
    if(response.getStatus() != 201){
      throw new RuntimeException("Failed to create user: " + response.getStatus());
    }

  }
}
