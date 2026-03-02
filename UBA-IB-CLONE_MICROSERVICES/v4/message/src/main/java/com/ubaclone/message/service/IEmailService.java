package com.ubaclone.message.service;

import com.ubaclone.message.dto.EmailSenderDto;

public interface IEmailService {
  void sendEmail(EmailSenderDto emailSenderDto);
}
