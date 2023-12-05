package com.example.photoapp.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {@UniqueConstraint(name = "users_username_key", columnNames = "username"), @UniqueConstraint(name = "users_user_id_key", columnNames = "user_id")}
)
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
  @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
  private Long id;

  @Column(name = "user_id", nullable = false, updatable = false)
  private UUID userId;
  @Column(nullable = false)
  private String username;

  @Column(name = "created_on", updatable = false)
  private Timestamp createdOn;

  @Column(name = "updated_on")
  private Timestamp updatedOn;

  @Column(name = "last_login")
  private Timestamp lastLogin;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private UserSecurity userSecurity;

  public User() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Timestamp getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Timestamp createdOn) {
    this.createdOn = createdOn;
  }

  public Timestamp getUpdatedOn() {
    return updatedOn;
  }

  public void setUpdatedOn(Timestamp updatedOn) {
    this.updatedOn = updatedOn;
  }

  public Timestamp getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(Timestamp lastLogin) {
    this.lastLogin = lastLogin;
  }

  public UserSecurity getUserSecurity() {
    return userSecurity;
  }

  public void setUserSecurity(UserSecurity userSecurity) {
    this.userSecurity = userSecurity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(userId, user.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId);
  }
}