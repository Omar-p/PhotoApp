package com.example.photoapp.validation;

import jakarta.validation.GroupSequence;

@GroupSequence({BasicRegistrationValidation.class, AdvanceRegistrationValidation.class})
public interface CompleteRegistrationValidation {
}
