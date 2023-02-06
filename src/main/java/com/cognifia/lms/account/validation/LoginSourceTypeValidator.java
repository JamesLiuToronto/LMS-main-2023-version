package com.cognifia.lms.account.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cognifia.lms.account.model.LoginSourceType;

public class LoginSourceTypeValidator implements ConstraintValidator<LoginSourceTypeValidation, String> {

    public boolean isValid(String loginSourceType, ConstraintValidatorContext cxt) {
        try {
            LoginSourceType.valueOf(loginSourceType);
        }
        catch(Exception e) {
            return false;
        }
        return true;
    }

}
