package com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.adapter;

import com.lian.marketing.usermicroservice.domain.spi.IMailSenderPersistencePort;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;

@RequiredArgsConstructor
public class MailSenderAdapter implements IMailSenderPersistencePort {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}") private String from;

    @Override
    public void sendVerificationEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.setFrom(new InternetAddress("verfiy@demomailtrap.co"));
        message.setRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(subject);
        message.setContent(body, "text/html;charset=utf-8");
        mailSender.send(message);
    }
}
