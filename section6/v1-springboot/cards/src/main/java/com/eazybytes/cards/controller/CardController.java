package com.eazybytes.cards.controller;

import com.eazybytes.cards.constants.CardsConstants;
import com.eazybytes.cards.dto.ErrorResponseDTO;
import com.eazybytes.cards.dto.CardsDTO;
import com.eazybytes.cards.dto.ResponseDTO;
import com.eazybytes.cards.service.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "CRUD REST API for Cards in EazyBank",
    description  = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH and DELETE card details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class CardController {

  private final ICardsService iCardsService;

  @Operation(
      summary = "Create card REST API",
      description = "REST API to create new card inside EazyBank"
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
  public ResponseEntity<ResponseDTO> createCard(
      @RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber){
    iCardsService.createCard(mobileNumber);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ResponseDTO(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
  }

  @Operation(
      summary = "Fetch Card Details REST API",
      description = "REST API to fetch card details based on a mobile number"
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
  public ResponseEntity<CardsDTO> fetchCardDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
      message = "Mobile number must be 10 digits") String mobileNumber){
    CardsDTO cardsDTO = iCardsService.fetchCard(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK).body(cardsDTO);
  }

  @Operation(
      summary = "Update Card Details REST API",
      description = "REST API to update card details based on a card number"
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
  public ResponseEntity<ResponseDTO> updateCardDetails(@Valid @RequestBody CardsDTO cardsDTO){
    iCardsService.updateCard(cardsDTO);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new ResponseDTO(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
  }

  @Operation(
      summary = "Delete Card Details REST API",
      description = "REST API to delete Card details based on a mobile number"
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
  public ResponseEntity<ResponseDTO> deleteCardDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
      message = "Mobile number must be 10 digits") String mobileNumber){
    iCardsService.deleteCard(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new ResponseDTO(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));

  }
}
