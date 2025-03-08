package com.lian.marketing.usermicroservice.domain;

import com.lian.marketing.usermicroservice.domain.api.IMailSenderServicePort;
import com.lian.marketing.usermicroservice.domain.api.usecase.MailSenderUseCase;
import com.lian.marketing.usermicroservice.domain.constants.MailConstants;
import com.lian.marketing.usermicroservice.domain.spi.IMailSenderPersistencePort;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MailSenderUseCaseTest {

    @Mock
    private IMailSenderPersistencePort mailSenderPersistencePort;

    private IMailSenderServicePort mailSenderServicePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mailSenderServicePort = new MailSenderUseCase(mailSenderPersistencePort);
    }

    @Test
    void shouldSendVerificationMailSuccessfully() throws MessagingException {
        String to = "anyone@gmail.com";
        String code = "123456";

        mailSenderServicePort.sendVerificationEmail(to, code);

        verify(mailSenderPersistencePort, times(1)).sendVerificationEmail(
                to,
                MailConstants.SUBJECT_VERIFICATION_MAIL,
                String.format(MailConstants.CONTENT_VERIFICATION_MAIL_HTML, code));
    }

}
