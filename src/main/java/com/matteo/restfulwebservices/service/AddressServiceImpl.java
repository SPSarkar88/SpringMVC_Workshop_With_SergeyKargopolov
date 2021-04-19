package com.matteo.restfulwebservices.service;

import com.matteo.restfulwebservices.dto.AddressDto;
import com.matteo.restfulwebservices.entity.AddressEntity;
import com.matteo.restfulwebservices.entity.UserEntity;
import com.matteo.restfulwebservices.exception.UserServiceException;
import com.matteo.restfulwebservices.repository.AddressRepository;
import com.matteo.restfulwebservices.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

  private UserRepository userRepository;
  private AddressRepository addressRepository;

  public AddressServiceImpl(UserRepository userRepository, AddressRepository addressRepository) {
    this.userRepository = userRepository;
    this.addressRepository = addressRepository;
  }

  @Override
  public List<AddressDto> getUserAddresses(String userId) {
    Optional<UserEntity> fetchedUser = userRepository.findUserEntityByUserId(userId);
    UserEntity actualUser =
        fetchedUser.orElseThrow(
            () -> new UserServiceException("The user with id: " + userId + " can't be found"));
    List<AddressDto> returnValue = new ArrayList<>();
    Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(actualUser);
    ModelMapper mapper = new ModelMapper();
    addresses.forEach(address -> returnValue.add(mapper.map(address, AddressDto.class)));
    return returnValue;
  }

  @Override
  public AddressDto getAddress(String addressId) {
    Optional<AddressEntity> fetchedAddress = addressRepository.findByAddressId(addressId);
    AddressEntity actualAddress =
        fetchedAddress.orElseThrow(
            () -> new UserServiceException("The user with id: " + addressId + " can't be found"));
    return new ModelMapper().map(actualAddress, AddressDto.class);
  }
}
