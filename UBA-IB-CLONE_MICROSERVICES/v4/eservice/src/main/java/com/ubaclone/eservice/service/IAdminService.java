package com.ubaclone.eservice.service;

import com.ubaclone.eservice.dto.RolesRecord;

public interface IAdminService {
  void createRole(RolesRecord rolesRecord); // create a realm role
  void assignRoleToUser(RolesRecord rolesRecord);
}
