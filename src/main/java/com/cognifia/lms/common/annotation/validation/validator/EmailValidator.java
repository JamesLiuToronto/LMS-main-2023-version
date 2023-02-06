package com.cognifia.lms.common.annotation.validation.validator;

import com.cognifia.lms.common.annotation.validation.validation.EmailValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
public class EmailValidator implements ConstraintValidator<EmailValidation, String> {
    private String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isEmpty())
            return true;

        Pattern pat = Pattern.compile(regex);
        return pat.matcher(email).matches();
    }

}
