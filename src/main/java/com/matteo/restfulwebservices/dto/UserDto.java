package com.matteo.restfulwebservices.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserDto implements Serializable {
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private static final long serialVersionUID = 7752522459832885453L;

  private Long id;
  private String userId;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String encryptedPassword;
  private String emailVerificationToken;
  private Boolean emailVerificationStatus = false;

  public UserDto() {
  }
}
