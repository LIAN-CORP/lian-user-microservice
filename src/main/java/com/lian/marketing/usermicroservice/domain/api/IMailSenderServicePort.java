package com.lian.marketing.usermicroservice.domain.api;

import jakarta.mail.MessagingException;

public interface IMailSenderServicePort {
    void sendVerificationEmail(String to, String code) throws MessagingException;
}
