package com.ra.project.controller;

import com.ra.project.model.dto.request.AddressRequest;
import com.ra.project.model.dto.request.ChangePasswordRequest;
import com.ra.project.model.dto.request.UserEdit;
import com.ra.project.model.dto.response.ResponseData;
import com.ra.project.model.entity.*;
import com.ra.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api.myservice.com/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/cart/list")
    public ResponseEntity<ResponseData<List<String>>> list() {
        List<String> shoppingCart = userService.getShoppingCart();
        return new ResponseEntity<>(new ResponseData<>("success", shoppingCart, HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping("/cart/add")
    public ResponseEntity<ResponseData<String>> addToShoppingCart(@RequestParam("productId") Long productId, @RequestParam("quantity") Long quantity) {
        userService.addToShoppingCart(productId, quantity);
        return new ResponseEntity<>(new ResponseData<>("success", "success", HttpStatus.OK), HttpStatus.OK);
    }

    @PutMapping("/cart/items/{cartItemId}")
    public ResponseEntity<ResponseData<ShoppingCart>> changeQuantity(@PathVariable("cartItemId") Long cartItemId, @RequestParam("quantity") Long quantity) {
        ShoppingCart shoppingCart = userService.changeQuantity(cartItemId, quantity);
        return new ResponseEntity<>(new ResponseData<>("success", shoppingCart, HttpStatus.OK), HttpStatus.OK);
    }

    @DeleteMapping("/cart/items/{cartItemId}")
    public ResponseEntity<?> removeFromShoppingCart(@PathVariable("cartItemId") Long cartItemId) {
        userService.removeFromShoppingCart(cartItemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/cart/clear")
    public ResponseEntity<ResponseData<String>> clearShoppingCart() {
        userService.clearShoppingCart();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/cart/checkout")
    public ResponseEntity<?> checkoutShoppingCart(@RequestParam("receiveAddress") String receiveAddress,
                                                  @RequestParam("receiveName") String receiveName,
                                                  @RequestParam("receivePhone") String receivePhone,
                                                  @RequestParam("note") String note) {
        Orders orders = userService.checkoutCartItem(receiveAddress, receiveName, receivePhone, note);
        return new ResponseEntity<>(new ResponseData<>("success", orders, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/account")
    public ResponseEntity<ResponseData<User>> getAccount() {
        User user = userService.getAccount();
        return new ResponseEntity<>(new ResponseData<>("success", user, HttpStatus.OK), HttpStatus.OK);
    }

    @PutMapping("/user/account")
    public ResponseEntity<ResponseData<User>> editAccount(@RequestBody UserEdit userEdit) {
        User user = userService.editAccount(userEdit);
        return new ResponseEntity<>(new ResponseData<>("success", user, HttpStatus.OK), HttpStatus.OK);
    }

    @PutMapping("/account/change-password")
    public ResponseEntity<ResponseData<String>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(changePasswordRequest);
        return new ResponseEntity<>(new ResponseData<>("success", "success", HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping("/account/addresses")
    public ResponseEntity<ResponseData<Address>> addAddress(@RequestBody AddressRequest addressRequest) {
        Address address = userService.addAddress(addressRequest);
        return new ResponseEntity<>(new ResponseData<>("success", address, HttpStatus.OK), HttpStatus.OK);
    }

    @DeleteMapping("/account/addresses/{addressId}")
    public ResponseEntity<ResponseData<String>> deleteAddress(@PathVariable("addressId") Long addressId) {
        userService.deleteAddress(addressId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/account/addresses")
    public ResponseEntity<ResponseData<List<Address>>> getAddresses() {
        List<Address> addresses = userService.getAddresses();
        return new ResponseEntity<>(new ResponseData<>("success", addresses, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<ResponseData<List<String>>> getAddress(@PathVariable("addressId") Long addressId) {
        List<String> address = userService.getAddress(addressId);
        return new ResponseEntity<>(new ResponseData<>("success", address, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<ResponseData<List<Orders>>> getUserHistory() {
        List<Orders> orders = userService.getUserHistory();
        return new ResponseEntity<>(new ResponseData<>("success", orders, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/history/{serialNumber}")
    public ResponseEntity<ResponseData<Orders>> getUserHistoryBySerialNumber(@PathVariable("serialNumber") String serialNumber) {
        Orders orders = userService.getUserHistoryBySerialNumber(serialNumber);
        return new ResponseEntity<>(new ResponseData<>("success", orders, HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/history/order/{orderStatus}")
    public ResponseEntity<ResponseData<List<Orders>>> getUserHistoryByOrderStatus(@PathVariable("orderStatus") String orderStatus) {
        List<Orders> orders = userService.getUserHistoryByOrderStatus(orderStatus);
        return new ResponseEntity<>(new ResponseData<>("success", orders, HttpStatus.OK), HttpStatus.OK);
    }

    @PutMapping("/history/{orderId}/cancel")
    public ResponseEntity<ResponseData<Orders>> cancelOrder(@PathVariable("orderId") Long orderId) {
           Orders orders = userService.cancelOrders(orderId);
           return new ResponseEntity<>(new ResponseData<>("success", orders, HttpStatus.OK), HttpStatus.OK);
    }
    @PostMapping("/wish-list")
    private ResponseEntity<ResponseData<WishList>> addToWishList(@RequestParam("productId") Long productId){
             WishList wishList = userService.addToWishList(productId);
             return new ResponseEntity<>(new ResponseData<>("success", wishList, HttpStatus.OK), HttpStatus.OK);
    }
    @GetMapping("/wish-list")
    public ResponseEntity<ResponseData<List<WishList>>> getWishList() {
        List<WishList> wishLists = userService.getWishList();
        return new ResponseEntity<>(new ResponseData<>("success",wishLists,HttpStatus.OK),HttpStatus.OK);
    }
    @DeleteMapping("/wish-list/{wishListId}")
    public ResponseEntity<?> deleteWishList(@PathVariable("wishListId") Long wishListId) {
           userService.deleteWishList(wishListId);
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
