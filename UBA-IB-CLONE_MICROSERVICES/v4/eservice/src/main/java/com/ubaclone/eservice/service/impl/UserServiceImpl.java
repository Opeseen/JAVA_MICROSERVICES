package com.ubaclone.eservice.service.impl;

import com.ubaclone.eservice.exception.KeycloakException;
import com.ubaclone.eservice.exception.ResourceNotFound;
import com.ubaclone.eservice.dto.UserRecord;
import com.ubaclone.eservice.service.IUserService;
import com.ubaclone.eservice.service.client.AccountFeignClient;
import feign.FeignException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

  @Value("${keycloak.realm}")
  private String realm;

  private final Keycloak keycloak;
  private final AccountFeignClient accountFeignClient;

  @Override
  public void registerUser(UserRecord userRecord) {
    verifyUserAccount(userRecord.accountNumber()); // verify the accountNumber provided by the user
    UserRepresentation newUser = getUserRepresentation(userRecord);
    Response response = keycloak.realm(realm).users().create(newUser);
    if (response.getStatus() != 201){
      log.info("Failed to create user with status code of: {}", response.getStatus());
      if(response.getStatus() == 409) {
        throw new KeycloakException("User already exists", response.getStatus());
      }
      throw new KeycloakException("Failed to create user", response.getStatus());
    }
    // assign the user to the "CUSTOMER" role
    String userId = CreatedResponseUtil.getCreatedId(response);
    assignRealmRole(userId);
  }

  // Set user representation details
  private UserRepresentation getUserRepresentation(UserRecord userRecord) {
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
    // add the user account details
    Map<String, List<String>> attributes = new HashMap<>();
    attributes.put("accountNumber", List.of(userRecord.accountNumber()));
    newUser.setAttributes(attributes);
    return newUser;
  }

  private void assignRealmRole(String userId){
    RoleRepresentation role = keycloak.realm(realm).roles().get("CUSTOMER").toRepresentation();
    keycloak.realm(realm).users().get(userId).roles().realmLevel().add(List.of(role));
  }

  private void verifyUserAccount(String accountNumber){
    try{
      accountFeignClient.verifyUserAccount(accountNumber);
    }catch (FeignException.NotFound ex) {
      // 404 from ACCOUNT service only
      throw new ResourceNotFound("account", "accountNumber", accountNumber);
    }
  }
}
