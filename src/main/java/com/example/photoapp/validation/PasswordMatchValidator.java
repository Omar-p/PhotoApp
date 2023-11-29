package com.example.photoapp.validation;

import com.example.photoapp.registration.UserRegistrationRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

  @Override
  public boolean isValid(Object request, ConstraintValidatorContext context) {
    if (request instanceof UserRegistrationRequest r) {
      return r.password().equals(r.confirmPassword());
    }
    return false;
  }

}