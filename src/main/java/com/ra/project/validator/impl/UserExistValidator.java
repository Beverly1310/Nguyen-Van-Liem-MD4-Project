package com.ra.project.validator.impl;

import com.ra.project.repository.UserRepository;
import com.ra.project.validator.UserExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserExistValidator implements ConstraintValidator<UserExist,String> {
    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findUserByUsername(s).isEmpty();
    }
}
