package com.example.photoapp.domain;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
    name = "authorities",
    uniqueConstraints = {@UniqueConstraint(name = "authorities_role_key", columnNames = "role")}
)
public class Authority implements GrantedAuthority {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorities_id_seq")
  @SequenceGenerator(name = "authorities_id_seq", sequenceName = "authorities_id_seq", allocationSize = 1)
  private Long id;

  @Column(nullable = false)
  private String role;

  @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
  private Set<UserSecurity> users = new HashSet<>();

  public Authority() {
  }

  public Authority(String role) {
    this.role = role;
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public String getAuthority() {
    return getRole();
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public Set<UserSecurity> getUsers() {
    return users;
  }

  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else if (obj instanceof Authority sga) {
      return this.role.equals(sga.getAuthority());
    } else {
      return false;
    }
  }

  public int hashCode() {
    return this.role.hashCode();
  }

}