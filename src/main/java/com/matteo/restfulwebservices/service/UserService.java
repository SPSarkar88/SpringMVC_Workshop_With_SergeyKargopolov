package com.matteo.restfulwebservices.service;

import com.matteo.restfulwebservices.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

  UserDto createUser(UserDto userDetails);
  UserDto getUser(String email);
  UserDto getUserByUserId(String userId);
  UserDto updateUser(String userId, UserDto userDetails);
  void deleteUser(String userId);
  List<UserDto> getAllUsers(int page, int limit);
}
