package com.ra.project.validator.impl;

import com.ra.project.service.UserService;
import com.ra.project.service.impl.UserServiceImpl;
import com.ra.project.validator.OldPasswordMatching;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class OldPasswordMatchingValidator implements ConstraintValidator<OldPasswordMatching, String> {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String oldPassword = UserServiceImpl.getCurrentUser().getPassword();
        return passwordEncoder.matches(s, oldPassword);
    }
}
