package com.ubaclone.eservice.controller;

import com.ubaclone.eservice.dto.LoginRequestDTO;
import com.ubaclone.eservice.dto.SuccessResponseDTO;
import com.ubaclone.eservice.dto.UserDTO;
import com.ubaclone.eservice.service.ICustomerService;
import com.ubaclone.eservice.service.IEservice;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class UserController {

  private final ICustomerService iCustomerService;
  private final IEservice iEservice;

  @PostMapping("/register")
  public ResponseEntity<SuccessResponseDTO> register(@Valid @RequestBody UserDTO userDTO){
    iEservice.register(userDTO);
    return new ResponseEntity<>(new SuccessResponseDTO(true, "User registration successfully",
            LocalDateTime.now()), HttpStatus.CREATED
    );
  }

  @PostMapping("/login")
  public ResponseEntity<SuccessResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest){
    iEservice.login(loginRequest.username(), loginRequest.password());
    return new ResponseEntity<>(new SuccessResponseDTO(true, "Login success",
        LocalDateTime.now()), HttpStatus.OK);
  }
}
