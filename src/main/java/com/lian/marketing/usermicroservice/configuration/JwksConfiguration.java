package com.lian.marketing.usermicroservice.configuration;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
public class JwksConfiguration {

  @Bean
  public RSAKey rsaKey() throws Exception {
    RSAPrivateKey privateKey = loadPrivateKey();
    RSAPublicKey publicKey = loadPublicKey();

    return new RSAKey.Builder(publicKey)
      .privateKey(privateKey)
      .keyID(UUID.randomUUID().toString())
      .algorithm(JWSAlgorithm.RS256)
      .keyUse(KeyUse.SIGNATURE)
      .build();
  }

  @Bean
  public JWKSet jwkSet(RSAKey rsaKey) {
    return new JWKSet(rsaKey.toPublicJWK());
  }

  private RSAPrivateKey loadPrivateKey() throws Exception {
    try(PEMParser pemParser = new PEMParser(new FileReader("private.pem"))) {
      Object object = pemParser.readObject();
      JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

      return switch (object) {
        case PEMKeyPair pemKeyPair -> (RSAPrivateKey) converter.getKeyPair(pemKeyPair).getPrivate();
        case PrivateKeyInfo privateKeyInfo -> (RSAPrivateKey) converter.getPrivateKey(privateKeyInfo);
        default -> throw new IllegalArgumentException("Private.pem format not supported: " + object);
      };

    }
  }

  private RSAPublicKey loadPublicKey() throws Exception {
    try(PEMParser pemParser = new PEMParser(new FileReader("public.pem"))) {
      Object object = pemParser.readObject();
      JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

      if(object instanceof SubjectPublicKeyInfo) {
        return (RSAPublicKey) converter.getPublicKey((SubjectPublicKeyInfo) object);
      } else {
        throw new IllegalArgumentException("Public.pem format not supported: " + object);
      }

    }
  }

}
