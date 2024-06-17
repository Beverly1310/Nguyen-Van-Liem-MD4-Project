package com.ra.project.service;

import com.ra.project.model.dto.request.ChangePasswordRequest;
import com.ra.project.model.dto.request.UserEdit;
import com.ra.project.model.entity.Orders;
import com.ra.project.model.entity.Product;
import com.ra.project.model.entity.ShoppingCart;
import com.ra.project.model.entity.User;
import com.ra.project.security.principal.CustomUserDetail;
import jakarta.persistence.criteria.Order;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserService {
    List<String> getShoppingCart();
    void addToShoppingCart(Long productId, Long quantity);
    ShoppingCart changeQuantity(Long cartItemId, Long quantity);
    void removeFromShoppingCart(Long cartItemId);
    void clearShoppingCart();
    Orders checkoutCartItem(String receiveAddress, String receiveName, String receivePhone, String note);
    User getAccount();
    User editAccount(UserEdit userEdit);
    void changePassword(ChangePasswordRequest changePasswordRequest);

}
