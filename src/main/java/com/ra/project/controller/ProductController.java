package com.ra.project.controller;

import com.ra.project.model.dto.response.ResponseData;
import com.ra.project.model.entity.Product;
import com.ra.project.model.entity.User;
import com.ra.project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api.myservice.com/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/search")
    public ResponseEntity<ResponseData<List<Product>>> searchProduct(@RequestParam("nameOrDescription") String nameOrDescription) {
        List<Product> products = productService.getProductsByNameOrDescription(nameOrDescription);
        return new ResponseEntity<>(new ResponseData<>("success", products, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseData<List<Product>>> getProducts(@RequestParam("page") Integer page,
                                                                   @RequestParam("size") Integer size,
                                                                   @RequestParam("orderBy") String orderBy,
                                                                   @RequestParam("direction") String direction) {
        List<Product> products = productService.getProducts(page, size, orderBy, direction).getContent();
        return new ResponseEntity<>(new ResponseData<>("success", products, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/featured-products")
    public ResponseEntity<ResponseData<List<Product>>> getFeaturedProducts(@RequestParam("limit") Integer limit) {
        List<Product> products = productService.getFeaturedProducts(limit);
        return new ResponseEntity<>(new ResponseData<>("success", products, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/new-products")
    public ResponseEntity<ResponseData<List<Product>>> getNewProducts(@RequestParam("limit") Integer limit) {
        List<Product> products = productService.getNewProducts(limit);
        return new ResponseEntity<>(new ResponseData<>("success", products, HttpStatus.OK), HttpStatus.OK);
    }
    @GetMapping("/best-seller-products")
    public ResponseEntity<ResponseData<List<Product>>> getBestSellerProducts(@RequestParam("limit") Integer limit) {
        List<Product> products = productService.getBestSellerProducts(limit);
        return new ResponseEntity<>(new ResponseData<>("success", products, HttpStatus.OK), HttpStatus.OK);
    }
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<ResponseData<List<Product>>> getProductByCategory(@PathVariable("categoryId") Long categoryId) {
        List<Product> products = productService.getProductByCategory(categoryId);
        return new ResponseEntity<>(new ResponseData<>("success", products, HttpStatus.OK), HttpStatus.OK);
    }
    @GetMapping("/products/{productId}")
    public ResponseEntity<ResponseData<Product>> getProduct(@PathVariable("productId") Long productId) {
        Product product = productService.getProductById(productId);
        return new ResponseEntity<>(new ResponseData<>("success", product, HttpStatus.OK), HttpStatus.OK);
    }

}
