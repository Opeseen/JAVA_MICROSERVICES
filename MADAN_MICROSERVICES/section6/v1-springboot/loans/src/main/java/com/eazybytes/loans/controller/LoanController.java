package com.eazybytes.loans.controller;

import com.eazybytes.loans.constants.LoansConstants;
import com.eazybytes.loans.dto.ErrorResponseDTO;
import com.eazybytes.loans.dto.LoansContactInfoDTO;
import com.eazybytes.loans.dto.LoansDTO;
import com.eazybytes.loans.dto.ResponseDTO;
import com.eazybytes.loans.service.ILoansService;
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
    name = "CRUD REST API for Loans in EazyBank",
    description  = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH and DELETE loan details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class LoanController {

  private final ILoansService iLoanService;
  private final Environment environment;
  private final LoansContactInfoDTO loansContactInfoDTO;

  @Value("${build.version}")
  private String buildVersion;

  @Operation(
      summary = "Create Loan REST API",
      description = "REST API to create new loan inside EazyBank"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "201",
          description = "HTTP Status CREATED"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "HTTP Status Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDTO.class)
          )
      )
  })
  @PostMapping("/create")
  public ResponseEntity<ResponseDTO> createLoan(
      @RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){
    iLoanService.createLoan(mobileNumber);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ResponseDTO(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
  }

  @Operation(
      summary = "Fetch Loan Details REST API",
      description = "REST API to fetch loan details based on a mobile number"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "201",
          description = "HTTP Status CREATED"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "HTTP Status Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDTO.class)
          )
      )
  })
  @GetMapping("/fetch")
  public ResponseEntity<LoansDTO> fetchLoanDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
      message = "Mobile number must be 10 digits") String mobileNumber){
    LoansDTO loansDTO = iLoanService.fetchLoan(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK).body(loansDTO);
  }

  @Operation(
      summary = "Update Loan Details REST API",
      description = "REST API to update loan details based on a loan number"
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
  public ResponseEntity<ResponseDTO> updateLoanDetails(@Valid @RequestBody LoansDTO loansDTO){
    iLoanService.updateLoan(loansDTO);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new ResponseDTO(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
  }

  @Operation(
      summary = "Delete Loan Details REST API",
      description = "REST API to delete Loan details based on a mobile number"
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
  public ResponseEntity<ResponseDTO> deleteLoanDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
      message = "Mobile number must be 10 digits") String mobileNumber){
    iLoanService.deleteLoan(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new ResponseDTO(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));

  }

  @Operation(
      summary = "Get build information", description = "Get build information that is deployed into loans microservice"
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
  public ResponseEntity<LoansContactInfoDTO> getContactInfo(){
    return ResponseEntity.status(HttpStatus.OK).body(loansContactInfoDTO);
  }
}
