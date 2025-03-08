package com.lian.marketing.usermicroservice.domain.api.usecase;

import com.lian.marketing.usermicroservice.domain.api.IMailSenderServicePort;
import com.lian.marketing.usermicroservice.domain.constants.MailConstants;
import com.lian.marketing.usermicroservice.domain.spi.IMailSenderPersistencePort;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MailSenderUseCase implements IMailSenderServicePort {

    private final IMailSenderPersistencePort mailSenderPersistencePort;

    @Override
    public void sendVerificationEmail(String to, String code) throws MessagingException {
        mailSenderPersistencePort.sendVerificationEmail(to,
                MailConstants.SUBJECT_VERIFICATION_MAIL,
                String.format(MailConstants.CONTENT_VERIFICATION_MAIL_HTML, code)
        );
    }

}
