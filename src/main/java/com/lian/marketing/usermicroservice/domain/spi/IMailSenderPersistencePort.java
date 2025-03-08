package com.lian.marketing.usermicroservice.domain.spi;

import jakarta.mail.MessagingException;

public interface IMailSenderPersistencePort {
    void sendVerificationEmail(String to, String subject, String body) throws MessagingException;
}
