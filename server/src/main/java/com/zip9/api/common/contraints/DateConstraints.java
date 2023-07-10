package com.zip9.api.common.contraints;

import com.zip9.api.common.validators.DateValidator;
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
@Constraint(validatedBy = DateValidator.class)
public @interface DateConstraints {
    String message() default "Invalid parameter";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    boolean nullable() default false;
}
