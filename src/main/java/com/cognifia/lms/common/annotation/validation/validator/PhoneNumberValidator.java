package com.cognifia.lms.common.annotation.validation.validator;

import com.cognifia.lms.common.annotation.validation.validation.PhoneNumberValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberValidation, String> {
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        return phoneNumber != null &&
                phoneNumber.matches("[0-9]+") &&
                phoneNumber.length() > 8 &&
                phoneNumber.length() < 14;
    }
}
