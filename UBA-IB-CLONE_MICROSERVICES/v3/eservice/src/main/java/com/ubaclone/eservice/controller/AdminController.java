package com.ubaclone.eservice.controller;

import com.ubaclone.eservice.dto.RolesRecord;
import com.ubaclone.eservice.service.IAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/admin", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class AdminController {

  private final IAdminService iAdminService;

  @PostMapping("/createRole")
  public ResponseEntity<String> createRole(@RequestBody RolesRecord rolesRecord){
    iAdminService.createRole(rolesRecord);
    return new ResponseEntity<>("Role created successfully", HttpStatus.CREATED);
  }

  @PostMapping("/assignRole")
  public ResponseEntity<String> assignRoleToUser(@RequestBody RolesRecord rolesRecord){
    iAdminService.assignRoleToUser(rolesRecord);
    return new ResponseEntity<>("Role assign successfully to user", HttpStatus.CREATED);
  }
}
