package com.ra.project.validator.impl;

import com.ra.project.repository.CategoryRepository;
import com.ra.project.validator.CategoryNameExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryExistValidator implements ConstraintValidator<CategoryNameExist,String> {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (categoryRepository==null){
            return true;
        }
        return categoryRepository.findCategoryByCategoryName(s).isEmpty();
    }

}
