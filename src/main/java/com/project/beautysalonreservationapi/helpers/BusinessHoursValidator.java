package com.project.beautysalonreservationapi.helpers;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;

public class BusinessHoursValidator implements ConstraintValidator<BusinessHours, LocalTime> {

    @Override
    public boolean isValid(LocalTime value, ConstraintValidatorContext context) {
        if(value.isAfter(LocalTime.parse("08:00")) && value.isBefore(LocalTime.parse("19:00")))return true;
        return false;
    }
}
