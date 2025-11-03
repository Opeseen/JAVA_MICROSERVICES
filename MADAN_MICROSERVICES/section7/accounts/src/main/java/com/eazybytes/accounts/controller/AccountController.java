package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountConstants;
import com.eazybytes.accounts.dto.AccountContactInfoDTO;
import com.eazybytes.accounts.dto.CustomerDTO;
import com.eazybytes.accounts.dto.ErrorResponseDTO;
import com.eazybytes.accounts.dto.ResponseDTO;
import com.eazybytes.accounts.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "CRUD REST API for Accounts in EazyBank",
    description  = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH and DELETE account details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class AccountController {

  private final IAccountService iAccountService;
  private final Environment environment;
  private final AccountContactInfoDTO accountContactInfoDTO;

  @Value("${build.version}")
  private String buildVersion;

  @Operation(
      summary = "Create Account REST API",
      description = "REST API to create new customer & account inside EazyBank"
  )
  @ApiResponse(responseCode = "201", description = "Http status created")
  @PostMapping("/create")
  public ResponseEntity<ResponseDTO> createAccount(@Valid @RequestBody CustomerDTO customerDTO){
    iAccountService.createAccount(customerDTO);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ResponseDTO(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
  }

  @Operation(
      summary = "Fetch account details REST API", description = "REST API to fetch customer & account on a mobile number"
  )
  @ApiResponse(responseCode = "200", description = "Https StatusOK ")
  @GetMapping("/fetch")
  public ResponseEntity<CustomerDTO> fetchAccountDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
      message = "Mobile number must be 10 digits") String mobileNumber){
    CustomerDTO customerDTO = iAccountService.fetchAccount(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
  }

  @Operation(
      summary = "Update Account Details REST API",
      description = "REST API to update Customer &  Account details based on a account number"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "HTTP Status OK"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "HTTP Status Internal Server Error",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
      )
  })
  @PutMapping("/update")
  public ResponseEntity<ResponseDTO> updateAccountDetails(@Valid @RequestBody CustomerDTO customerDTO){
    iAccountService.updateAccount(customerDTO);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new ResponseDTO(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
  }

  @Operation(
      summary = "Delete Account Details REST API",
      description = "REST API to delete Customer &  Account details based on a account number"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "HTTP Status OK"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "HTTP Status Internal Server Error",
          content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))
      )
  })
  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDTO> deleteAccountDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
      message = "Mobile number must be 10 digits") String mobileNumber){
    iAccountService.deleteAccount(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new ResponseDTO(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));

  }

  @Operation(
      summary = "Get build information", description = "Get build information that is deployed into accounts microservice"
  )
  @ApiResponse(responseCode = "200", description = "Https StatusOK ")
  @GetMapping("/build/info")
  public ResponseEntity<String> getBuildInfo(){
    return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
  }

  @GetMapping("/java/version")
  public ResponseEntity<String> getJavaVersion(){
    return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
  }

  @GetMapping("/contact/info")
  public ResponseEntity<AccountContactInfoDTO> getContactInfo(){
    return ResponseEntity.status(HttpStatus.OK).body(accountContactInfoDTO);
  }
}
