package com.ubaclone.eservice.service.impl;

import com.ubaclone.eservice.exception.KeycloakException;
import com.ubaclone.eservice.dto.RolesRecord;
import com.ubaclone.eservice.service.IAdminService;
import jakarta.ws.rs.WebApplicationException;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements IAdminService {

  private final Keycloak keycloak;

  @Override
  public void createRole(RolesRecord rolesRecord) {
    RoleRepresentation newRole = new RoleRepresentation();
    newRole.setName(rolesRecord.roleName().toUpperCase());
    newRole.setDescription(rolesRecord.description());
    newRole.setComposite(false);
    try {
      keycloak.realm(rolesRecord.realm())
          .roles().create(newRole);
    } catch (WebApplicationException ex) {
      throw new KeycloakException("Failed to create realm role", ex.getResponse().getStatus());
    }
  }

  @Override
  public void assignRoleToUser(RolesRecord rolesRecord) {
    try {
      // 1.) get the role first
      RoleRepresentation role = keycloak.realm(rolesRecord.realm())
          .roles().get(rolesRecord.roleName().toUpperCase()).toRepresentation();
      // 2.) assign the role to the specified user
      keycloak.realm(rolesRecord.realm()).users()
          .get(rolesRecord.userId()).roles().realmLevel().add(List.of(role));
    }catch (WebApplicationException ex){
      throw new KeycloakException("Failed to assign role to user", ex.getResponse().getStatus());
    }
  }
}
