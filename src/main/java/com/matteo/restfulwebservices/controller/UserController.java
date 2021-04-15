package com.matteo.restfulwebservices.controller;

import com.matteo.restfulwebservices.dto.UserDto;
import com.matteo.restfulwebservices.model.request.UserDetailsRequestModel;
import com.matteo.restfulwebservices.model.response.OperationStatusModel;
import com.matteo.restfulwebservices.model.response.RequestOperationName;
import com.matteo.restfulwebservices.model.response.RequestOperationStatus;
import com.matteo.restfulwebservices.model.response.UserResponseModel;
import com.matteo.restfulwebservices.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/users")
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(produces = {APPLICATION_XML_VALUE, APPLICATION_JSON_VALUE})
  public List<UserResponseModel> getAllUsers(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "limit", defaultValue = "25") int limit) {
    List<UserResponseModel> response = new ArrayList<>();
    List<UserDto> users = userService.getAllUsers(page, limit);
    users.forEach(userDto -> {
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

    UserResponseModel response = new UserResponseModel();

    UserDto userToSave = new UserDto();
    BeanUtils.copyProperties(userDetails, userToSave);

    UserDto createdUser = userService.createUser(userToSave);
    BeanUtils.copyProperties(createdUser, response);

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
}
