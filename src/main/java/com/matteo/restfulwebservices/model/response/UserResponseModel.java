package com.matteo.restfulwebservices.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponseModel {

  private String userId;
  private String firstName;
  private String lastName;
  private String email;
  private List<AddressResponseModel> addresses;

  public UserResponseModel(String userId, String firstName, String lastName, String email, List<AddressResponseModel> addresses) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.addresses = addresses;
  }

  public UserResponseModel() {}
}
