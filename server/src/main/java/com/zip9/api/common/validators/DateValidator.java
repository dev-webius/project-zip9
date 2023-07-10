package com.zip9.api.common.validators;

import com.zip9.api.common.contraints.DateConstraints;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator implements ConstraintValidator<DateConstraints, String> {
    private DateConstraints annotation;

    @Override
    public void initialize(DateConstraints constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (ObjectUtils.isEmpty(value)) {
            return this.annotation.nullable();
        }

        try {
            LocalDate.from(LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
