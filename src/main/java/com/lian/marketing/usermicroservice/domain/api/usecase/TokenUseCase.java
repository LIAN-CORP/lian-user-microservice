package com.lian.marketing.usermicroservice.domain.api.usecase;

import com.lian.marketing.usermicroservice.domain.api.ITokenServicePort;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenUseCase implements ITokenServicePort {

  private final RSAKey rsaKey;

  @Override
  public String generateToken(String username) throws JOSEException {
    JWSSigner signer = new RSASSASigner(rsaKey);

    JWTClaimsSet claims = new JWTClaimsSet.Builder()
      .subject(username)
      .issuer("user-microservice")
      .expirationTime(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
      .build();

    JWSObject jwsObject = new JWSObject(
      new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaKey.getKeyID()).build(),
      new Payload(claims.toJSONObject())
    );

    jwsObject.sign(signer);

    return jwsObject.serialize();
  }
}
