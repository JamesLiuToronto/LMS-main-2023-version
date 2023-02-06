package com.cognifia.lms.common.annotation.validation.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.TYPE_USE })
@Constraint(validatedBy = com.cognifia.lms.common.annotation.validation.validator.EmailValidator.class)
public @interface EmailValidation {
    String message() default "validation.email.code";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}