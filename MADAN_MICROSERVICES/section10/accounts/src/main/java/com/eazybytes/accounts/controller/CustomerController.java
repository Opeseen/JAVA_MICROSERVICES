package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.dto.CustomerDetailsDTO;
import com.eazybytes.accounts.dto.ErrorResponseDTO;
import com.eazybytes.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "CRUD REST API for Customers in EazyBank",
    description  = "REST APIs in EazyBank to FETCH customer details"
)

@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@RestController
@RequiredArgsConstructor
public class CustomerController {
  private final ICustomerService iCustomerService;
  private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

  @Operation(
      summary = "Fetch customer details REST API",
      description = "REST API to fetch customer details based on a mobile number"
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
  @GetMapping("/fetchCustomerDetails")
  public ResponseEntity<CustomerDetailsDTO> fetchCustomerDetails(
      @RequestHeader("eazybank-correlation-id") String correlationId,
      @RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
      message = "Mobile number must be 10 digits") String mobileNumber){
    logger.debug("eazyBank-correlation-id found: {} ", correlationId);
    CustomerDetailsDTO customerDetailsDTO = iCustomerService.fetchCustomerDetails(mobileNumber, correlationId);
    return new ResponseEntity<>(customerDetailsDTO, HttpStatus.OK);
  }
}
