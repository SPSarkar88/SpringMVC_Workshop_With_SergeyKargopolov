package com.matteo.restfulwebservices.service;

import com.matteo.restfulwebservices.dto.AddressDto;
import com.matteo.restfulwebservices.dto.UserDto;
import com.matteo.restfulwebservices.entity.UserEntity;
import com.matteo.restfulwebservices.exception.UserServiceException;
import com.matteo.restfulwebservices.model.response.ErrorMessages;
import com.matteo.restfulwebservices.repository.UserRepository;
import com.matteo.restfulwebservices.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    Optional<UserEntity> maybeUserCheck = userRepository.findUserEntityByEmail(user.getEmail());
    if (maybeUserCheck.isPresent()) {
      throw new UserServiceException("This user already exists");
    }

    user.getAddresses().forEach(address -> {
      address.setUserDetails(user);
      address.setAddressId(utils.generateAddressId(30));
    });

    ModelMapper mapper = new ModelMapper();
    UserEntity userEntity = mapper.map(user, UserEntity.class);

    String userId = utils.generateUserId(30);
    userEntity.setUserId(userId);
    userEntity.setEncryptedPassword(passwordEncoder.encode(user.getPassword()));

    UserEntity savedUser = userRepository.save(userEntity);

    return mapper.map(savedUser, UserDto.class);
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
            () -> new UserServiceException("The user with id: " + id + " can't be found"));
    UserDto returnValue = new UserDto();
    BeanUtils.copyProperties(userActual, returnValue);
    return returnValue;
  }

  @Override
  public UserDto updateUser(String userId, UserDto userDetails) {
    Optional<UserEntity> maybeUser = userRepository.findUserEntityByUserId(userId);
    UserEntity userActual =
        maybeUser.orElseThrow(
            () -> new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
    userActual.setFirstName(userDetails.getFirstName());
    userActual.setLastName(userDetails.getLastName());
    UserEntity updatedUser = userRepository.save(userActual);
    UserDto returnValue = new UserDto();
    BeanUtils.copyProperties(updatedUser, returnValue);
    return returnValue;
  }

  @Override
  public void deleteUser(String userId) {
    Optional<UserEntity> maybeUser = userRepository.findUserEntityByUserId(userId);
    UserEntity userActual =
        maybeUser.orElseThrow(
            () -> new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
    userRepository.delete(userActual);
  }

  @Override
  public List<UserDto> getAllUsers(int page, int limit) {
    List<UserDto> returnValue = new ArrayList<>();
    PageRequest pageableRequest = PageRequest.of(page, limit);
    Page<UserEntity> thePage = userRepository.findAll(pageableRequest);
    List<UserEntity> users = thePage.getContent();
    users.forEach(
        userEntity -> {
          UserDto userDto = new UserDto();
          BeanUtils.copyProperties(userEntity, userDto);
          returnValue.add(userDto);
        });
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
