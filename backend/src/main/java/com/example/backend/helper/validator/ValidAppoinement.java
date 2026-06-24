package com.example.backend.helper.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AppoinetmentValidator.class)
public @interface ValidAppoinement {
    String message() default "Invalid appointment time. Must be a future date during business hours.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
