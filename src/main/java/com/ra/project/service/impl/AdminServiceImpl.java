package com.ra.project.service.impl;

import com.ra.project.model.cons.OrderStatus;
import com.ra.project.model.cons.RoleName;
import com.ra.project.model.dto.request.CategoryRequest;
import com.ra.project.model.dto.request.ProductRequest;
import com.ra.project.model.entity.*;
import com.ra.project.repository.*;
import com.ra.project.service.AdminService;
import com.ra.project.service.CategoryService;
import com.ra.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public Page<User> getUserWithPagingAndSorting(Integer page, Integer size, String orderBy, String direction) {
        Pageable pageable = null;

        if (orderBy != null && !orderBy.isEmpty()) {
            // co sap xep
            Sort sort = null;
            switch (direction.toUpperCase().trim()) {
                case "ASC":
                    sort = Sort.by(orderBy).ascending();
                    break;
                case "DESC":
                    sort = Sort.by(orderBy).descending();
                    break;
            }
            pageable = PageRequest.of(page - 1, size, sort);
        } else {
            //khong sap xep
            pageable = PageRequest.of(page - 1, size);
        }
        return userRepository.getAll(pageable);
    }

    @Override
    public User changStatus(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
        if (user != null) {
            user.setStatus(!user.getStatus());
            userRepository.save(user);
        }
        return user;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<User> getUserByFullName(String fullName) {
        return userRepository.findByFullNameContaining(fullName);
    }

    @Override
    public Page<Product> getProductWithPagingAndSorting(Integer page, Integer size, String orderBy, String direction) {
        Pageable pageable = null;

        if (orderBy != null && !orderBy.isEmpty()) {
            // co sap xep
            Sort sort = null;
            switch (direction.toUpperCase().trim()) {
                case "ASC":
                    sort = Sort.by(orderBy).ascending();
                    break;
                case "DESC":
                    sort = Sort.by(orderBy).descending();
                    break;
            }
            pageable = PageRequest.of(page - 1, size, sort);
        } else {
            //khong sap xep
            pageable = PageRequest.of(page - 1, size);
        }
        return productRepository.getAll(pageable);
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.getProductById(id).orElseThrow(() -> new NoSuchElementException("Product not found"));
    }

    @Override
    public Product addOrEditProduct(ProductRequest productRequest, Long id) {
        Product product = Product.builder()
                .productName(productRequest.getProductName())
                .description(productRequest.getDescription())
                .unitPrice(productRequest.getUnitPrice())
                .stockQuantity(productRequest.getStockQuantity())
                .image(productRequest.getImage())
                .category(categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> new NoSuchElementException("Category not found")))
                .build();
        if (id != null) {
            productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Product not found"));
            product.setId(id);
        }

        productRepository.save(product);
        return product;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Category> getCategories(Integer page, Integer size, String orderBy, String direction) {
        Pageable pageable = null;

        if (orderBy != null && !orderBy.isEmpty()) {
            // co sap xep
            Sort sort = null;
            switch (direction.toUpperCase().trim()) {
                case "ASC":
                    sort = Sort.by(orderBy).ascending();
                    break;
                case "DESC":
                    sort = Sort.by(orderBy).descending();
                    break;
            }
            pageable = PageRequest.of(page - 1, size, sort);
        } else {
            //khong sap xep
            pageable = PageRequest.of(page - 1, size);
        }
        return categoryRepository.getAll(pageable);
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Category not found"));
    }

    @Override
    public Category addOrEditCategory(CategoryRequest categoryRequest, Long id) {
        Category category = Category.builder()
                .categoryName(categoryRequest.getCategoryName())
                .description(categoryRequest.getDescription())
                .build();
        if (id != null) {
            categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Category not found"));
            category.setId(id);
        }
        categoryRepository.save(category);
        return category;
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public List<Orders> getOrders() {
        return (List<Orders>) orderRepository.findAll();
    }

    @Override
    public List<Orders> getOrdersByStatus(String status) {
        return switch (status.toUpperCase().trim()) {
            case "WAITING" -> orderRepository.getOrdersByStatus(OrderStatus.WAITING);
            case "CONFIRM" -> orderRepository.getOrdersByStatus(OrderStatus.CONFIRM);
            case "DELIVERY" -> orderRepository.getOrdersByStatus(OrderStatus.DELIVERY);
            case "SUCCESS" -> orderRepository.getOrdersByStatus(OrderStatus.SUCCESS);
            case "CANCEL" -> orderRepository.getOrdersByStatus(OrderStatus.CANCEL);
            case "DENIED" -> orderRepository.getOrdersByStatus(OrderStatus.DENIED);
            default -> throw new RuntimeException("Status invalid");
        };
    }

    @Override
    public List<OrderDetail> getOrderDetails(Long orderId) {
        return orderDetailRepository.getOrderDetailsByOrderId(orderId);
    }

    @Override
    public Orders changeOrderStatus(Long orderId, String status) {
        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new NoSuchElementException("Order not found"));
        switch (status.toUpperCase().trim()) {
            case "WAITING" -> order.setStatus(OrderStatus.WAITING);
            case "CONFIRM" -> order.setStatus(OrderStatus.CONFIRM);
            case "DELIVERY" -> order.setStatus(OrderStatus.DELIVERY);
            case "SUCCESS" -> order.setStatus(OrderStatus.SUCCESS);
            case "CANCEL" -> order.setStatus(OrderStatus.CANCEL);
            case "DENIED" -> order.setStatus(OrderStatus.DENIED);
            default -> throw new NoSuchElementException("Status not valid");
        }
        return orderRepository.save(order);
    }

    @Override
    public User addRoleForUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        Set<Role> roles = user.getRoles();
        roles.add(roleRepository.findById(roleId).orElseThrow(() -> new NoSuchElementException("Role not found")));
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public void deleteRoleForUser(Long userId, Long roleId) {
        User user = userRepository.findById(UserServiceImpl.getCurrentUser().getId()).orElseThrow(() -> new NoSuchElementException("User not found"));
        User userDeleteRole = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        Set<Role> roles = user.getRoles();
        if (roles.contains(roleRepository.findRoleByRoleName(RoleName.USER))) {
            throw new RuntimeException("You are not allowed to delete this role");
        } else {
            Set<Role> newRoles = userDeleteRole.getRoles();
            newRoles.remove(roleRepository.findById(roleId).orElseThrow(() -> new NoSuchElementException("Role not found")));
            userDeleteRole.setRoles(newRoles);
            if (userDeleteRole.getRoles().isEmpty()) {
                userDeleteRole.setStatus(false);
            }
            userRepository.save(userDeleteRole);
        }
    }

    @Override
    public Double getSalesRevenueOverTime(String from, String to) {
        LocalDate fromDate = LocalDate.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate toDate = LocalDate.parse(to, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Orders> orders = orderRepository.getOrdersOverTime(fromDate, toDate);
        Double salesRevenueOverTime = orders.stream().filter(order -> OrderStatus.SUCCESS.equals(order.getStatus()))
                .mapToDouble(Orders::getTotalPrice)
                .sum();
        return salesRevenueOverTime;
    }
}
