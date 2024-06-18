package com.ra.project.validator.impl;

import com.ra.project.repository.CategoryRepository;
import com.ra.project.repository.ProductRepository;
import com.ra.project.validator.CategoryNameExist;
import com.ra.project.validator.ProductNameExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductExistValidator implements ConstraintValidator<ProductNameExist,String> {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (productRepository==null){
            return true;
        }
        return productRepository.findProductByProductName(s).isEmpty();
    }

}