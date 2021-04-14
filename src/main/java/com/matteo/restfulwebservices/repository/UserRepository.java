package com.matteo.restfulwebservices.repository;

import com.matteo.restfulwebservices.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

  Optional<UserEntity> findUserEntityByEmail(String email);
  Optional<UserEntity> findUserEntityByUserId(String id);
}
