package com.lian.marketing.usermicroservice.domain.api;

import com.nimbusds.jose.JOSEException;

public interface ITokenServicePort {
  String generateToken(String username) throws JOSEException;
}
