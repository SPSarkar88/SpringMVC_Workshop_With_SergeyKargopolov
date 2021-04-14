package com.matteo.restfulwebservices.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseModel {

  private String userId;
  private String firstName;
  private String lastName;
  private String email;

  public UserResponseModel(String userId, String firstName, String lastName, String email) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public UserResponseModel() {}
}
