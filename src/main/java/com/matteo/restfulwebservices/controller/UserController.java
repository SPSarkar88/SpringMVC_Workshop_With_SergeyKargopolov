package com.matteo.restfulwebservices.controller;

import com.matteo.restfulwebservices.dto.UserDto;
import com.matteo.restfulwebservices.model.request.UserDetailsRequestModel;
import com.matteo.restfulwebservices.model.response.UserResponseModel;
import com.matteo.restfulwebservices.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/users")
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
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

    UserResponseModel response = new UserResponseModel();

    UserDto userToSave = new UserDto();
    BeanUtils.copyProperties(userDetails, userToSave);

    UserDto createdUser = userService.createUser(userToSave);
    BeanUtils.copyProperties(createdUser, response);

    return response;
  }

  public UserResponseModel updateUser() {
    return null;
  }

  public UserResponseModel deleteUser() {
    return null;
  }
}
