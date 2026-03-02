package com.ubaclone.message.service.impl;

import com.ubaclone.message.dto.EmailSenderDto;
import com.ubaclone.message.service.IEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements IEmailService {

  private final JavaMailSender javaMailSender;

  @Override
  public void sendEmail(EmailSenderDto emailSenderDto) {
    try{
      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(emailSenderDto.to());
      message.setSubject(emailSenderDto.subject());
      message.setText(emailSenderDto.body());
      message.setFrom("no-reply@ubaclone.com");
      javaMailSender.send(message);

      log.info("Email sent successfully to {}", emailSenderDto.to());
    }catch (Exception ex){
      log.error("Error sending email with error: {}", ex.getMessage());
    }
  }
}
