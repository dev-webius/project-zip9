package com.zip9.api.common.contraints;

import com.zip9.api.common.validators.EnumValidator;
import com.zip9.api.common.validators.EnumsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {EnumValidator.class, EnumsValidator.class})
public @interface EnumConstraints {
    String message() default "Invalid parameter";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();

    boolean ignoreCase() default false;

    boolean nullable() default false;
}
