package com.ra.project.service;

import com.ra.project.model.cons.OrderStatus;
import com.ra.project.model.dto.request.AddressRequest;
import com.ra.project.model.dto.request.ChangePasswordRequest;
import com.ra.project.model.dto.request.UserEdit;
import com.ra.project.model.entity.*;
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
    Address addAddress(AddressRequest addressRequest);
    void deleteAddress(Long addressId);
    List<Address> getAddresses();
    List<String> getAddress(Long addressId);
    List<Orders> getUserHistory();
   Orders getUserHistoryBySerialNumber(String serialNumber);
   List<Orders> getUserHistoryByOrderStatus(String orderStatus);
   Orders cancelOrders(Long orderId);
   WishList addToWishList(Long productId);
   List<WishList> getWishList();
   void deleteWishList(Long wishListId);
}
