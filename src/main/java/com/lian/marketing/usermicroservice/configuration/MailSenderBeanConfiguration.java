package com.lian.marketing.usermicroservice.configuration;

import com.lian.marketing.usermicroservice.domain.api.IMailSenderServicePort;
import com.lian.marketing.usermicroservice.domain.api.usecase.MailSenderUseCase;
import com.lian.marketing.usermicroservice.domain.spi.IMailSenderPersistencePort;
import com.lian.marketing.usermicroservice.infrastructure.driven.jpa.postgresql.adapter.MailSenderAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class MailSenderBeanConfiguration {

    @Value("${spring.mail.host}") String host;
    @Value("${spring.mail.port}") Integer port;
    @Value("${spring.mail.username}") String username;
    @Value("${spring.mail.password}") String password;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);

        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return javaMailSender;
    }

    @Bean
    public IMailSenderPersistencePort mailSenderPersistencePort() {
        return new MailSenderAdapter(javaMailSender());
    }

    @Bean
    public IMailSenderServicePort mailSenderServicePort() {
        return new MailSenderUseCase(mailSenderPersistencePort());
    }

}
