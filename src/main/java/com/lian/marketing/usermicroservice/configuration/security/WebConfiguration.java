package com.lian.marketing.usermicroservice.configuration.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins("*")
      .allowedMethods("*")
      .allowedHeaders("*");
  }

}
