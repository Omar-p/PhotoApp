package com.example.photoapp.registration;

import com.example.photoapp.validation.CompleteRegistrationValidation;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = {
    ValidationAutoConfiguration.class
})
class UserRegistrationRequestTest {

  private Validator validator;

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

    Set<ConstraintViolation<UserRegistrationRequest>> constraintViolations = validator.validate(request, CompleteRegistrationValidation.class);

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


}