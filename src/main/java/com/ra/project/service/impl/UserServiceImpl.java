package com.ra.project.service.impl;

import com.ra.project.model.cons.OrderStatus;
import com.ra.project.model.dto.request.ChangePasswordRequest;
import com.ra.project.model.dto.request.UserEdit;
import com.ra.project.model.entity.*;
import com.ra.project.repository.*;
import com.ra.project.security.config.JWTAuthenticationEntryPoint;
import com.ra.project.security.jwt.JWTAuthenticationFilter;
import com.ra.project.security.jwt.JWTProvider;
import com.ra.project.security.principal.CustomUserDetail;
import com.ra.project.service.UserService;
import jakarta.persistence.criteria.Order;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public List<String> getShoppingCart() {
        Long id = getCurrentUser().getId();
        return userRepository.getShoppingCart(id).stream()
                .map(Product::getProductName)
                .toList();
    }

    @Override
    public void addToShoppingCart(Long productId, Long quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserIdAndProductId(getCurrentUser().getId(), productId).orElse(null);
        if (shoppingCart == null) {
            shoppingCart = ShoppingCart.builder()
                    .orderQuantity(quantity)
                    .user(userRepository.findById(getCurrentUser().getId()).orElseThrow(() -> new NoSuchElementException("User not found")))
                    .product(productRepository.findById(productId).orElseThrow(() -> new NoSuchElementException("Product not found")))
                    .build();
        } else {
            shoppingCart.setOrderQuantity(shoppingCart.getOrderQuantity() + quantity);
        }
        shoppingCartRepository.save(shoppingCart);
    }

    private static CustomUserDetail getCurrentUser() {
        return  (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public ShoppingCart changeQuantity(Long cartItemId, Long quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartItemId).orElseThrow(() -> new NoSuchElementException("ShoppingCart not found"));
        shoppingCart.setOrderQuantity(quantity);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;
    }

    @Override
    public void removeFromShoppingCart(Long cartItemId) {
        shoppingCartRepository.deleteById(cartItemId);
    }

    @Override
    public void clearShoppingCart() {
        shoppingCartRepository.deleteShoppingCartByUserId(getCurrentUser().getId());
    }

    @Override
    public Orders checkoutCartItem(String receiveAddress, String receiveName, String receivePhone, String note) {
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findShoppingCartByUserId(getCurrentUser().getId());
        CustomUserDetail currentUser = getCurrentUser();
        Double totalPrice = userRepository.getShoppingCart(currentUser.getId()).stream()
                .map(totalUnitPrice -> {
                    return totalUnitPrice.getUnitPrice() * totalUnitPrice.getStockQuantity();
                })
                .reduce(0D, Double::sum);
        Orders order = Orders.builder()
                .user(userRepository.findById(currentUser.getId()).orElseThrow(() -> new NoSuchElementException("User not found")))
                .status(OrderStatus.WAITING)
                .totalPrice(totalPrice)
                .build();
        if (receiveAddress == null || receiveAddress.isBlank()) {
            order.setReceiveAddress(receiveAddress);
        } else {
            order.setReceiveAddress(currentUser.getAddress());
        }
        if (receiveName == null || receiveName.isBlank()) {
            order.setReceiveName(receiveName);
        } else {
            order.setReceiveName(currentUser.getAddress());
        }
        if (receivePhone == null || receivePhone.isBlank()) {
            order.setReceivePhone(receivePhone);
        } else {
            order.setReceivePhone(currentUser.getAddress());
        }
        for (ShoppingCart shoppingCart : shoppingCarts) {
            OrderDetail orderDetail = OrderDetail.builder()
                    .name(productRepository.getProductById(shoppingCart.getProduct().getId()).orElseThrow(() -> new NoSuchElementException("Product not found")).getProductName())
                    .orderQuantity(shoppingCart.getOrderQuantity())
                    .unitPrice(shoppingCart.getProduct().getUnitPrice())
                    .id(new OrderDetailId(order, shoppingCart.getProduct()))
                    .build();
            Product product = productRepository.findById(shoppingCart.getProduct().getId()).orElseThrow(() -> new NoSuchElementException("Product not found"));
            if (product.getStockQuantity() == 0) {
                throw new RuntimeException("Product has no stock");
            } else {
                product.setStockQuantity(product.getStockQuantity() - orderDetail.getOrderQuantity());
            }
            orderDetailRepository.save(orderDetail);
        }
        return orderRepository.save(order);
    }

    @Override
    public User getAccount() {
        return userRepository.findById(getCurrentUser().getId()).orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @Override
    public User editAccount(UserEdit userEdit) {
        User user = userRepository.findById(getCurrentUser().getId()).orElseThrow(() -> new NoSuchElementException("User not found"));
        if (userEdit.getFullName()!=null && !userEdit.getFullName().isBlank()) {
            user.setFullName(userEdit.getFullName());
        }
        if (userEdit.getEmail()!=null && !userEdit.getEmail().isBlank()) {
            user.setEmail(userEdit.getEmail());
        }
        if (userEdit.getPhone()!=null && !userEdit.getPhone().isBlank()) {
            user.setPhone(userEdit.getPhone());
        }
        if (userEdit.getAvatar()!=null && !userEdit.getAvatar().isBlank()) {
            user.setAvatar(userEdit.getAvatar());
        }
        user.setUpdatedAt(userEdit.getUpdatedAt());
        userRepository.save(user);
        return null;
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findById(getCurrentUser().getId()).orElseThrow(() -> new NoSuchElementException("User not found"));
        user.setPassword(changePasswordRequest.getNewPassword());
        userRepository.save(user);
    }
}

