package com.matteo.restfulwebservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
/* 'extends' necessary for WAR deployment */
public class RestfulWebServicesApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(RestfulWebServicesApplication.class, args);
  }

  /* Necessary for WAR deployment */
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(RestfulWebServicesApplication.class);
  }

  @Bean
  public BCryptPasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SpringApplicationContext getApplicationContext() {
    return new SpringApplicationContext();
  }

}
