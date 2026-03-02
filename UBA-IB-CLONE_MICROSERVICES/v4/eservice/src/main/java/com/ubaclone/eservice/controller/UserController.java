package com.ubaclone.eservice.controller;

import com.ubaclone.eservice.dto.UserRecord;
import com.ubaclone.eservice.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserController {

  private final IUserService iUserService;

  @PostMapping("/public/register")
  public ResponseEntity<String> registerUser(@Valid @RequestBody UserRecord userRecord){
    // register user in keycloak for access to e-service
    iUserService.registerUser(userRecord);
    return new ResponseEntity<>("User registration successful", HttpStatus.CREATED);
  }

}
