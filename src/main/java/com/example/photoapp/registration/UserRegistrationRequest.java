package com.example.photoapp.registration;

import com.example.photoapp.validation.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@PasswordMatch(groups = BasicRegistrationValidation.class)
public record UserRegistrationRequest(
    @NotNull(groups = BasicRegistrationValidation.class)
    @Size(min = 4, groups = BasicRegistrationValidation.class)
    @UniqueUsername(groups = AdvanceRegistrationValidation.class)
    String username,
    @NotNull(groups = BasicRegistrationValidation.class)
    @Email(groups = BasicRegistrationValidation.class)
    @UniqueEmail(groups = AdvanceRegistrationValidation.class)
    String email,

    @PasswordPolicy(groups = BasicRegistrationValidation.class)
    String password,
    String confirmPassword
) {
}
