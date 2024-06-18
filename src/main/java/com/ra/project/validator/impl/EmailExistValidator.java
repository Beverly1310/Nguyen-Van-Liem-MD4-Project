package com.ra.project.validator.impl;

import com.ra.project.model.entity.User;
import com.ra.project.repository.UserRepository;
import com.ra.project.validator.EmailExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailExistValidator implements ConstraintValidator<EmailExist, String> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (userRepository==null){
            return true;
        }
        return !userRepository.getAll().stream().map(User::getEmail).toList().contains(s);
    }
}
