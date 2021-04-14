package com.matteo.restfulwebservices;

import com.matteo.restfulwebservices.security.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class RestfulWebServicesApplication {

  public static void main(String[] args) {
    SpringApplication.run(RestfulWebServicesApplication.class, args);
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
