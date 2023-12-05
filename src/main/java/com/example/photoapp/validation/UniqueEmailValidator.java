package com.example.photoapp.validation;

import com.example.photoapp.UserSecurityRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

  private final UserSecurityRepository repository;

  public UniqueEmailValidator(UserSecurityRepository repository) {
    this.repository = repository;
  }

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    return email != null && repository.findByEmail(email).isEmpty() ;
  }

}