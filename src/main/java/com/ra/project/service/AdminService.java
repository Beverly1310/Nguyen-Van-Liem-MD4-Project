package com.ra.project.service;

import com.ra.project.model.entity.Product;
import com.ra.project.model.entity.Role;
import com.ra.project.model.entity.User;
import org.springframework.data.domain.Page;
import java.util.List;

public interface AdminService {
    Page<User> getUserWithPagingAndSorting(Integer page,Integer size,String orderBy,String direction);
    User changStatus(Long id);
    List<Role> getAllRoles();
    List<User> getUserByFullName(String fullName);
    Page<Product> getProductWithPagingAndSorting(Integer page, Integer size, String orderBy, String direction);
    Product getProduct(Long id);
}
