package com.matteo.restfulwebservices.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponseModel {

  private String addressId;
  private String city;
  private String country;
  private String streetName;
  private String postalCode;
  private String type;

  public AddressResponseModel(
      String addressId,
      String city,
      String country,
      String streetName,
      String postalCode,
      String type) {
    this.city = city;
    this.country = country;
    this.streetName = streetName;
    this.postalCode = postalCode;
    this.type = type;
    this.addressId = addressId;
  }

  public AddressResponseModel() {}
}
