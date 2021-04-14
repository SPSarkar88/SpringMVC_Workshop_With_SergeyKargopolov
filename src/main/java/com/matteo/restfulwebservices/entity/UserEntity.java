package com.matteo.restfulwebservices.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity(name = "UserEntity")
@Table(name = "users")
public class UserEntity implements Serializable {
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private static final long serialVersionUID = 3337854987879949888L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false, unique = true)
  private String userId;

  @Column(name = "first_name", nullable = false, length = 50)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 50)
  private String lastName;

  @Column(name = "email", nullable = false, length = 120, unique = true)
  private String email;

  @Column(name = "encrypted_password", nullable = false)
  private String encryptedPassword;

  @Column(name = "email_verification_token", unique = true)
  private String emailVerificationToken;

  @Column(name = "email_verification_status", nullable = false)
  private Boolean emailVerificationStatus = false;

  public UserEntity(
      String userId,
      String firstName,
      String lastName,
      String email,
      String encryptedPassword,
      String emailVerificationToken,
      Boolean emailVerificationStatus) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.encryptedPassword = encryptedPassword;
    this.emailVerificationToken = emailVerificationToken;
    this.emailVerificationStatus = emailVerificationStatus;
  }

  public UserEntity() {}
}
