package com.ra.project.validator;

import com.ra.project.validator.impl.ConfirmPasswordMatchingValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;

import java.lang.annotation.*;

@Constraint(
        validatedBy = {ConfirmPasswordMatchingValidator.class}
)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfirmPasswordMatching {
    String password();
    String confirmPassword();

    String message() default "Confirm password not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        ConfirmPasswordMatching[] value();
    }
}
