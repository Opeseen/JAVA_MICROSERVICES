package com.ubaclone.gatewayserver.controller;

import com.ubaclone.gatewayserver.dto.UserRecord;
import com.ubaclone.gatewayserver.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserController {

  private final IUserService iUserService;

  @PostMapping("/register")
  ResponseEntity<String> createUser(UserRecord userRecord){
    iUserService.createUser(userRecord);
    return new ResponseEntity<>("User registration successful", HttpStatus.CREATED);
  }
}
