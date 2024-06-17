package com.ra.project.controller;

import com.ra.project.model.cons.OrderStatus;
import com.ra.project.model.dto.request.CategoryRequest;
import com.ra.project.model.dto.request.ProductRequest;
import com.ra.project.model.dto.response.ResponseData;
import com.ra.project.model.entity.*;
import com.ra.project.service.AdminService;
import com.ra.project.service.CategoryService;
import jakarta.persistence.criteria.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api.myservice.com/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/user")
    public ResponseEntity<ResponseData<List<User>>> getAllUsers(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size,
                                                                @RequestParam("orderBy") String orderBy,
                                                                @RequestParam("direction") String direction) {
        List<User> users = adminService.getUserWithPagingAndSorting(page, size, orderBy, direction).getContent();
        return new ResponseEntity<>(new ResponseData<>("success", users, HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping("/users/{userId}")
    public ResponseEntity<ResponseData<User>> changeStatus(@PathVariable("userId") Long userId) {
        User user = adminService.changStatus(userId);
        return new ResponseEntity<>(new ResponseData<>("success", user, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<ResponseData<List<Role>>> getAllRoles() {
        List<Role> roles = adminService.getAllRoles();
        return new ResponseEntity<>(new ResponseData<>("success", roles, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseData<List<User>>> getUserByFullName(@RequestParam("fullName") String fullName) {
        List<User> users = adminService.getUserByFullName(fullName);
        return new ResponseEntity<>(new ResponseData<>("success", users, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<ResponseData<List<Product>>> getAllProducts(@RequestParam("page") Integer page,
                                                                      @RequestParam("size") Integer size,
                                                                      @RequestParam("orderBy") String orderBy,
                                                                      @RequestParam("direction") String direction) {
        List<Product> products = adminService.getProductWithPagingAndSorting(page, size, orderBy, direction).getContent();
        return new ResponseEntity<>(new ResponseData<>("success", products, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ResponseData<Product>> getProductById(@PathVariable("productId") Long productId) {
        Product product = adminService.getProduct(productId);
        return new ResponseEntity<>(new ResponseData<>("success", product, HttpStatus.OK), HttpStatus.OK);
    }

    @PutMapping("/products")
    public ResponseEntity<ResponseData<Product>> addProduct(@RequestBody ProductRequest productRequest) {
        Product product = adminService.addOrEditProduct(productRequest, null);
        return new ResponseEntity<>(new ResponseData<>("success", product, HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping("/products/{productId}")
    public ResponseEntity<ResponseData<Product>> editProduct(@RequestBody ProductRequest productRequest, @PathVariable("productId") Long productId) {
        Product product = adminService.addOrEditProduct(productRequest, productId);
        return new ResponseEntity<>(new ResponseData<>("success", product, HttpStatus.OK), HttpStatus.OK);
    }

    @DeleteMapping("products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") Long productId) {
        adminService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<ResponseData<List<Category>>> getAllCategories(@RequestParam("page") Integer page,
                                                                         @RequestParam("size") Integer size,
                                                                         @RequestParam("orderBy") String orderBy,
                                                                         @RequestParam("direction") String direction) {
        List<Category> categories = adminService.getCategories(page, size, orderBy, direction).getContent();
        return new ResponseEntity<>(new ResponseData<>("success", categories, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<ResponseData<Category>> getCategoryById(@PathVariable("categoryId") Long categoryId) {
        Category category = adminService.getCategory(categoryId);
        return new ResponseEntity<>(new ResponseData<>("success", category, HttpStatus.OK), HttpStatus.OK);
    }

    @PutMapping("/categories")
    public ResponseEntity<ResponseData<Category>> addCategory(@RequestBody CategoryRequest categoryRequest) {
        Category category = adminService.addOrEditCategory(categoryRequest, null);
        return new ResponseEntity<>(new ResponseData<>("success", category, HttpStatus.OK), HttpStatus.OK);
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<ResponseData<Category>> editCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable("categoryId") Long categoryId) {
        Category category = adminService.addOrEditCategory(categoryRequest, categoryId);
        return new ResponseEntity<>(new ResponseData<>("success", category, HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping("/categories/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        adminService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<ResponseData<List<Orders>>> getAllOrders() {
        List<Orders> orders = adminService.getOrders();
        return new ResponseEntity<>(new ResponseData<>("success", orders, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/orders/{orderStatus}")
    public ResponseEntity<ResponseData<List<Orders>>> getOrdersByStatus(@PathVariable("orderStatus") String orderStatus) {
        List<Orders> ordersByStatus = adminService.getOrdersByStatus(orderStatus);
        return new ResponseEntity<>(new ResponseData<>("success", ordersByStatus, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/orders/detail/{orderId}")
    public ResponseEntity<ResponseData<List<OrderDetail>>> getOrderDetail(@PathVariable("orderId") Long orderId) {
        List<OrderDetail> orderDetails = adminService.getOrderDetails(orderId);
        return new ResponseEntity<>(new ResponseData<>("success", orderDetails, HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping("/orders/{orderId}/status")
    public ResponseEntity<ResponseData<Orders>> changeOrderStatus(@PathVariable("orderId") Long orderId, @RequestParam("status") String status) {
        Orders order = adminService.changeOrderStatus(orderId, status);
        return new ResponseEntity<>(new ResponseData<>("success", order, HttpStatus.OK), HttpStatus.OK);
    }
}
