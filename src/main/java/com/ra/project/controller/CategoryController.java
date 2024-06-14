package com.ra.project.controller;

import com.ra.project.model.dto.response.ResponseData;
import com.ra.project.model.entity.Category;
import com.ra.project.repository.CategoryRepository;
import com.ra.project.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api.myservice.com/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<ResponseData<List<Category>>> getCategories() {
        List<Category> categories = categoryService.getCategories();
        return new ResponseEntity<>(new ResponseData<>("success", categories, HttpStatus.OK), HttpStatus.OK);
    }
}
