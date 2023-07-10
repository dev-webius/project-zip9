package com.zip9.api.common.validators;

import com.zip9.api.common.contraints.EnumConstraints;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class EnumsValidator implements ConstraintValidator<EnumConstraints, List<String>> {
    private EnumConstraints annotation;

    @Override
    public void initialize(EnumConstraints constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        if (values.isEmpty()) {
            return this.annotation.nullable();
        }

        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        if (enumValues != null) {
            for (String value: values) {
                for (Object enumValue : enumValues) {
                    if (value.equals(enumValue.toString()) || (this.annotation.ignoreCase() && value.equalsIgnoreCase(enumValue.toString()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
