package com.example.photoapp.registration;

import com.example.photoapp.UserRepository;
import com.example.photoapp.UserSecurityRepository;
import com.example.photoapp.domain.User;
import com.example.photoapp.domain.UserSecurity;
import com.example.photoapp.validation.CompleteRegistrationValidation;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * This class serves as a test suite for the execution order of validation annotations to ensure that it runs as expected.
 * It focuses on the validation of the {@link UserRegistrationRequest} class using the Spring Boot testing framework.
 * The purpose of these tests is to verify the behavior of the validation process under different scenarios.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = {
    ValidationAutoConfiguration.class
})
class UserRegistrationRequestTest {

  private Validator validator;

  @MockBean
  UserRepository userRepository;

  @MockBean
  UserSecurityRepository userSecurityRepository;

  @Autowired
  LocalValidatorFactoryBean localValidatorFactoryBean;
  static UserRegistrationRequest validRequest = new UserRegistrationRequest(
      "omar",
      "omar@email.com",
      "strongPassword?!123",
      "strongPassword?!123"
  );

  @BeforeEach
  void setUp() {
    this.validator = localValidatorFactoryBean.getValidator();
  }

  @Test
  void newRequestWithValidFieldsShouldProduceZeroViolation() {
    var request = new UserRegistrationRequest(
        validRequest.username(),
        validRequest.email(),
        validRequest.password(),
        validRequest.confirmPassword()
    );


    BDDMockito.willReturn(Optional.empty()).given(userRepository).findByUsername(anyString());
    BDDMockito.willReturn(Optional.empty()).given(userSecurityRepository).findByEmail(anyString());

    Set<ConstraintViolation<UserRegistrationRequest>> constraintViolations = validator.validate(request, CompleteRegistrationValidation.class);

    verify(userRepository, times(1)).findByUsername(anyString());
    verify(userSecurityRepository, times(1)).findByEmail(anyString());

    BDDAssertions.then(constraintViolations.size()).isEqualTo(0);
  }

  @Test
  void newRequestWithUnMatchedPasswordShouldProduceOneConstraintViolation() {
    var request = new UserRegistrationRequest(
        validRequest.username(),
        validRequest.email(),
        validRequest.password(),
        "12345678"
    );

    Set<ConstraintViolation<UserRegistrationRequest>> constraintViolations = validator.validate(request, CompleteRegistrationValidation.class);

    BDDAssertions.then(constraintViolations.size()).isEqualTo(1);
  }

  @Test
  void newRequestWithBasicConstraintViolationShouldNotTouchTheDatabase() {
    var request = new UserRegistrationRequest(
        validRequest.username(),
        validRequest.email(),
        validRequest.password(),
        "12345678"
    );

    Set<ConstraintViolation<UserRegistrationRequest>> constraintViolations = validator.validate(request, CompleteRegistrationValidation.class);

    // verify that the database is not touched
    verify(userRepository, never()).findByUsername(anyString());
    verify(userSecurityRepository, never()).findByEmail(anyString());

    BDDAssertions.then(constraintViolations.size()).isEqualTo(1);
  }

  @Test
  void newRequestWithTakenEmailAndUsernameShouldProduceTwoConstraintViolation() {
    var request = new UserRegistrationRequest(
        validRequest.username(),
        validRequest.email(),
        validRequest.password(),
        validRequest.confirmPassword()
    );

    BDDMockito.willReturn(Optional.of(Optional.of(new User()))).given(userRepository).findByUsername(anyString());
    BDDMockito.willReturn(Optional.of(Optional.of(new UserSecurity()))).given(userSecurityRepository).findByEmail(anyString());

    Set<ConstraintViolation<UserRegistrationRequest>> constraintViolations = validator.validate(request, CompleteRegistrationValidation.class);

    verify(userRepository, times(1)).findByUsername(anyString());
    verify(userSecurityRepository, times(1)).findByEmail(anyString());

    BDDAssertions.then(constraintViolations.size()).isEqualTo(2);

  }


}