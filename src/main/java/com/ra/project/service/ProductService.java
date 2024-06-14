package com.ra.project.service;

import com.ra.project.model.entity.Product;
import com.ra.project.model.entity.Role;
import com.ra.project.model.entity.User;

import java.util.List;

public interface ProductService {
    List<Product> getProductsByNameOrDescription(String nameOrDescription);
    List<Product> getProducts(Integer page,Integer size,String orderBy,String direction);
    List<Product> getFeaturedProducts(Integer limit);
    List<Product> getNewProducts(Integer limit);
    List<Product> getBestSellerProducts(Integer limit);
    List<Product> getProductByCategory(Long categoryId);
    Product getProductById(Long id);

}
