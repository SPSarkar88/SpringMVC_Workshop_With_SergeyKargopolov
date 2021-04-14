package com.matteo.restfulwebservices.service;

import com.matteo.restfulwebservices.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  UserDto createUser(UserDto userDetails);
  UserDto getUser(String email);
  UserDto getUserByUserId(String id);
}
