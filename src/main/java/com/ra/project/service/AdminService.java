package com.ra.project.service;

import com.ra.project.model.dto.request.CategoryRequest;
import com.ra.project.model.dto.request.ProductRequest;
import com.ra.project.model.entity.*;
import org.springframework.data.domain.Page;
import java.util.List;

public interface AdminService {
    Page<User> getUserWithPagingAndSorting(Integer page,Integer size,String orderBy,String direction);
    User changStatus(Long id);
    List<Role> getAllRoles();
    List<User> getUserByFullName(String fullName);
    Page<Product> getProductWithPagingAndSorting(Integer page, Integer size, String orderBy, String direction);
    Product getProduct(Long id);
    Product addOrEditProduct(ProductRequest productRequest,Long id);
    void deleteProduct(Long id);
    Page<Category> getCategories(Integer page, Integer size, String orderBy, String direction);
    Category getCategory(Long id);
    Category addOrEditCategory(CategoryRequest categoryRequest, Long id);
    void deleteCategory(Long categoryId);
    List<Orders> getOrders();
    List<Orders> getOrdersByStatus(String status);
    List<OrderDetail> getOrderDetails(Long orderId);
    Orders changeOrderStatus(Long orderId,String status);
    User addRoleForUser(Long userId, Long roleId);
    void deleteRoleForUser(Long userId, Long roleId);
    Double getSalesRevenueOverTime(String from, String to);

}
