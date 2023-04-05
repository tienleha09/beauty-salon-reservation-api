package com.project.beautysalonreservationapi.helpers;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BusinessHoursValidator.class)
public @interface BusinessHours {
    String message() default "Must be within 8:00 a.m and 19:00 p.m";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
