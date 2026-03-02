package com.ubaclone.message.functions;

import com.ubaclone.message.dto.AccountMessageDto;
import com.ubaclone.message.dto.EmailSenderDto;
import com.ubaclone.message.service.IEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class MessageFunctions {
  private final IEmailService emailService;

  @Bean
  public Function<AccountMessageDto, String> email(){
    return accountMessageDto -> {
      EmailSenderDto emailDto =
          new EmailSenderDto(accountMessageDto.email(), "Account Created!", EmailContent(accountMessageDto));
      emailService.sendEmail(emailDto);
      return "Email Sent!";
    };
  }

  private String EmailContent(AccountMessageDto accountMessageDto){
    return """
          Hello %s,

          Your account %d has been successfully created.

          Thank you.
          """.formatted(accountMessageDto.name(), accountMessageDto.accountNumber());
  };

}
