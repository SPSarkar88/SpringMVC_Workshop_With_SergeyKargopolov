package com.matteo.restfulwebservices.service;

import com.matteo.restfulwebservices.dto.AddressDto;

import java.util.List;

public interface AddressService {

  List<AddressDto> getUserAddresses(String userId);

  AddressDto getAddress(String addressId);
}
