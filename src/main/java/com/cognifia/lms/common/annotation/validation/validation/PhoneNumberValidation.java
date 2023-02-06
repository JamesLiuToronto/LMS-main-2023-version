package com.cognifia.lms.common.annotation.validation.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = com.cognifia.lms.common.annotation.validation.validator.PhoneNumberValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumberValidation {
    String message() default "validation.phone_number.code";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}