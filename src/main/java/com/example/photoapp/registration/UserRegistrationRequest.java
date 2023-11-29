package com.example.photoapp.registration;

import com.example.photoapp.validation.BasicRegistrationValidation;
import com.example.photoapp.validation.PasswordMatch;
import com.example.photoapp.validation.PasswordPolicy;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@PasswordMatch(groups = BasicRegistrationValidation.class)
public record UserRegistrationRequest(
    @NotNull(groups = BasicRegistrationValidation.class)
    @Size(min = 4, groups = BasicRegistrationValidation.class)
    String username,
    @NotNull(groups = BasicRegistrationValidation.class)
    @Email(groups = BasicRegistrationValidation.class)
    String email,

    @PasswordPolicy(groups = BasicRegistrationValidation.class)
    String password,
    String confirmPassword
) {
}
