package com.matteo.restfulwebservices.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequestModel {

  private String city;
  private String country;
  private String streetName;
  private String postalCode;
  private String type;

  public AddressRequestModel(
      String city, String country, String streetName, String postalCode, String type) {
    this.city = city;
    this.country = country;
    this.streetName = streetName;
    this.postalCode = postalCode;
    this.type = type;
  }

  public AddressRequestModel() {
  }
}
