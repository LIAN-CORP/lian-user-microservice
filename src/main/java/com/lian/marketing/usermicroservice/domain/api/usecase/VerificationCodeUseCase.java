package com.lian.marketing.usermicroservice.domain.api.usecase;

import com.lian.marketing.usermicroservice.domain.api.IVerificationCodeServicePort;
import com.lian.marketing.usermicroservice.domain.constants.ExceptionConstants;
import com.lian.marketing.usermicroservice.domain.exceptions.ExpiredCodeException;
import com.lian.marketing.usermicroservice.domain.exceptions.InvalidCodeException;
import com.lian.marketing.usermicroservice.domain.exceptions.NoVerificationCodeIsAssociateWithUser;
import com.lian.marketing.usermicroservice.domain.model.VerificationCode;
import com.lian.marketing.usermicroservice.domain.spi.IVerificationCodePersistencePort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@RequiredArgsConstructor
public class VerificationCodeUseCase implements IVerificationCodeServicePort {

    private static final Random RANDOM = new Random();

    private final IVerificationCodePersistencePort verificationCodePersistencePort;

    @Override
    public String createCode(UUID userId) {
        VerificationCode code = new VerificationCode();
        code.setUserId(userId);
        code.setCode(generateCode());
        code.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        code.setIsVerified(false);
        verificationCodePersistencePort.saveCode(code);
        return code.getCode();
    }

    @Override
    public void changeVerifiedStatusCode(UUID userId) {
        Optional<VerificationCode> code = verificationCodePersistencePort.findVerificationCodeByUserId(userId);
        code.ifPresentOrElse(
                codeExists -> {
                    codeExists.setIsVerified(true);
                    verificationCodePersistencePort.changeIsVerifiedStatus(codeExists);
                },
                () -> {throw new NoVerificationCodeIsAssociateWithUser(ExceptionConstants.NO_VERIFICATION_CODE_IS_ASSOCIATE_WITH_USER);}
        );
    }

    @Override
    public void deleteCode(UUID userId) {
        verificationCodePersistencePort.deleteVerificationCodeByUserId(userId);
    }

    @Override
    public void findByEmailAndCode(String email, String code) {
        VerificationCode verifyCode = verificationCodePersistencePort.findByEmailAndCode(email, code)
                .orElseThrow(() -> new InvalidCodeException(ExceptionConstants.INVALID_VERIFICATION_CODE));

        if (verifyCode.getExpiresAt().isBefore(LocalDateTime.now())) {
           throw new ExpiredCodeException(ExceptionConstants.EXPIRED_VERIFICATION_CODE);
        }

        verifyCode.setIsVerified(true);
        verificationCodePersistencePort.saveCode(verifyCode);
    }

    private String generateCode() {
        int random1 = RANDOM.nextInt(99);
        int random2 = RANDOM.nextInt(99);
        int random3 = RANDOM.nextInt(99);
        return String.format("%d%d%d", random1, random2, random3);
    }
}
