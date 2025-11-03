package com.eazybytes.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(name = "ErrorResponse")
public class ErrorResponseDTO {

  @Schema(description = "API path invoked by the client")
  private String apiPath;

  @Schema(description = "Error code representing what happened", example = "500")
  private HttpStatus errorCode;

  @Schema(description = "Error message representing when the error happened", example = "An error has occurred")
  private String errorMessage;

  @Schema(description = "Time representing when the error happened")
  private LocalDateTime errorTime;
}
