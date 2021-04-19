package com.matteo.restfulwebservices.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class UserDetailsRequestModel {

  @NotNull(message = "First name can't be null")
  @Size(min = 2)
  private String firstName;

  @NotNull(message = "Last name can't be null")
  @Size(min = 2)
  private String lastName;

  @NotNull(message = "Email can't be null")
  @Email
  private String email;

  @NotNull(message = "Password can't be null")
  @Size(min = 8, max = 16)
  private String password;

  private List<AddressRequestModel> addresses;

  public UserDetailsRequestModel(
      String firstName,
      String lastName,
      String email,
      String password,
      List<AddressRequestModel> addresses) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.addresses = addresses;
  }

  public UserDetailsRequestModel() {}
}
