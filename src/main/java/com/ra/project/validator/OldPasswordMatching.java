package com.ra.project.validator;

import com.ra.project.validator.impl.CategoryExistValidator;
import com.ra.project.validator.impl.OldPasswordMatchingValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(
        validatedBy = {OldPasswordMatchingValidator.class}
)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OldPasswordMatching {
    String message() default "Old password does not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        OldPasswordMatching[] value();
    }
}
