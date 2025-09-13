package com.lian.marketing.usermicroservice.configuration.security;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JWKSet jwkSet;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    logger.info("JWT Auth Filter -> Request URI: " + request.getRequestURI());

    String authorization = request.getHeader("Authorization");
    if(authorization == null || !authorization.startsWith("Bearer ")){
      filterChain.doFilter(request, response);
      return;
    }

    String token = authorization.substring(7);
    ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
    JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(JWSAlgorithm.RS256, new ImmutableJWKSet<>(jwkSet));
    jwtProcessor.setJWSKeySelector(keySelector);

    try {
      var claims = jwtProcessor.process(token, null);
      String userId = claims.getSubject();
      String role = claims.getStringClaim("role");
      var auth = new UsernamePasswordAuthenticationToken(userId, null, Collections.singleton(() -> role));

      auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(auth);

    } catch (Exception ignored) {
      SecurityContextHolder.clearContext();
    }

    filterChain.doFilter(request, response);

  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String path = request.getRequestURI();
    return path.startsWith("/auth") || path.startsWith("/.well-known/jwks.json");
  }

}
