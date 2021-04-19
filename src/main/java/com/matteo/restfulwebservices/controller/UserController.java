package com.matteo.restfulwebservices.controller;

import com.matteo.restfulwebservices.dto.AddressDto;
import com.matteo.restfulwebservices.dto.UserDto;
import com.matteo.restfulwebservices.model.request.UserDetailsRequestModel;
import com.matteo.restfulwebservices.model.response.*;
import com.matteo.restfulwebservices.service.AddressService;
import com.matteo.restfulwebservices.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/users")
public class UserController {

  private UserService userService;
  private AddressService addressService;

  public UserController(UserService userService, AddressService addressService) {
    this.userService = userService;
    this.addressService = addressService;
  }

  @GetMapping(produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE})
  public List<UserResponseModel> getAllUsers(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "limit", defaultValue = "25") int limit) {
    List<UserResponseModel> response = new ArrayList<>();
    List<UserDto> users = userService.getAllUsers(page, limit);
    users.forEach(
        userDto -> {
          UserResponseModel userResponseModel = new UserResponseModel();
          BeanUtils.copyProperties(userDto, userResponseModel);
          response.add(userResponseModel);
        });
    return response;
  }

  @GetMapping(
      value = "/{userId}",
      produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE})
  public UserResponseModel getUser(@PathVariable String userId) {
    UserResponseModel response = new UserResponseModel();
    UserDto retrievedUser = userService.getUserByUserId(userId);
    BeanUtils.copyProperties(retrievedUser, response);
    return response;
  }

  @PostMapping(
      produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE},
      consumes = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE})
  public UserResponseModel createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) {

    ModelMapper mapper = new ModelMapper();
    UserDto userToSave = mapper.map(userDetails, UserDto.class);

    UserDto createdUser = userService.createUser(userToSave);
    UserResponseModel response = mapper.map(createdUser, UserResponseModel.class);

    return response;
  }

  @PutMapping(
      value = "/{userId}",
      produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE},
      consumes = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE})
  public UserResponseModel updateUser(
      @PathVariable String userId, @RequestBody UserDetailsRequestModel userDetails) {
    UserResponseModel response = new UserResponseModel();

    UserDto userToSave = new UserDto();
    BeanUtils.copyProperties(userDetails, userToSave);

    UserDto updatedUser = userService.updateUser(userId, userToSave);
    BeanUtils.copyProperties(updatedUser, response);

    return response;
  }

  @DeleteMapping(
      value = "/{userId}",
      produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE})
  public OperationStatusModel deleteUser(@PathVariable String userId) {
    OperationStatusModel response = new OperationStatusModel();
    response.setOperationName(RequestOperationName.DELETE.name());
    userService.deleteUser(userId);
    response.setOperationResult(RequestOperationStatus.SUCCESS.name());
    return response;
  }

  @GetMapping(value = "/{userId}/address", produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE})
  public List<AddressResponseModel> getUserAddresses(@PathVariable String userId) {
    List<AddressResponseModel> response;
    List<AddressDto> addressDto = addressService.getUserAddresses(userId);
    Type listType = new TypeToken<List<AddressResponseModel>>() {}.getType();
    response = new ModelMapper().map(addressDto, listType);
    return response;
  }

  @GetMapping(value = "/{userId}/address/{addressId}", produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE})
  public AddressResponseModel getOneUserAddress(@PathVariable String userId, @PathVariable String addressId) {
    AddressDto addressDto = addressService.getAddress(addressId);
    return new ModelMapper().map(addressDto, AddressResponseModel.class);
  }
}
