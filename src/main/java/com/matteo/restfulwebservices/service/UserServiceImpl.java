package com.matteo.restfulwebservices.service;

import com.matteo.restfulwebservices.dto.UserDto;
import com.matteo.restfulwebservices.entity.UserEntity;
import com.matteo.restfulwebservices.exception.UserServiceException;
import com.matteo.restfulwebservices.repository.UserRepository;
import com.matteo.restfulwebservices.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private Utils utils;
  private BCryptPasswordEncoder passwordEncoder;

  public UserServiceImpl(
      UserRepository userRepository, Utils utils, BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.utils = utils;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDto createUser(UserDto user) {

    UserEntity userEntity = new UserEntity();
    BeanUtils.copyProperties(user, userEntity);

    String userId = utils.generateUserId(30);
    userEntity.setUserId(userId);
    userEntity.setEncryptedPassword(passwordEncoder.encode(user.getPassword()));

    UserEntity savedUser = userRepository.save(userEntity);

    UserDto returnValue = new UserDto();
    BeanUtils.copyProperties(savedUser, returnValue);

    return returnValue;
  }

  @Override
  public UserDto getUser(String email) {
    Optional<UserEntity> maybeUser = userRepository.findUserEntityByEmail(email);
    UserEntity userActual =
        maybeUser.orElseThrow(
            () ->
                new UsernameNotFoundException(
                    String.format("The user with username: %s can't be found", email)));
    UserDto returnValue = new UserDto();
    BeanUtils.copyProperties(userActual, returnValue);
    return returnValue;
  }

  @Override
  public UserDto getUserByUserId(String id) {
    Optional<UserEntity> maybeUser = userRepository.findUserEntityByUserId(id);
    UserEntity userActual =
        maybeUser.orElseThrow(
            () ->
                new UserServiceException("The user with id: " + id + " can't be found"));
    UserDto returnValue = new UserDto();
    BeanUtils.copyProperties(userActual, returnValue);
    return returnValue;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<UserEntity> maybeUser = userRepository.findUserEntityByEmail(email);
    UserEntity userActual =
        maybeUser.orElseThrow(
            () ->
                new UsernameNotFoundException(
                    String.format("The user with username: %s can't be found", email)));

    return new User(userActual.getEmail(), userActual.getEncryptedPassword(), new ArrayList<>());
  }
}
