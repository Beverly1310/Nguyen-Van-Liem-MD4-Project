package com.ra.project.controller;

import com.ra.project.model.dto.request.ChangePasswordRequest;
import com.ra.project.model.dto.request.UserEdit;
import com.ra.project.model.dto.response.ResponseData;
import com.ra.project.model.entity.Orders;
import com.ra.project.model.entity.Product;
import com.ra.project.model.entity.ShoppingCart;
import com.ra.project.model.entity.User;
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
         return new ResponseEntity<>(new ResponseData<>("success",shoppingCart, HttpStatus.OK),HttpStatus.OK);
    }
    @PostMapping("/cart/add")
    public ResponseEntity<ResponseData<String>> addToShoppingCart(@RequestParam("productId") Long productId,@RequestParam("quantity") Long quantity) {
            userService.addToShoppingCart(productId,quantity);
            return new ResponseEntity<>(new ResponseData<>("success","success",HttpStatus.OK),HttpStatus.OK);
    }
    @PutMapping("/cart/items/{cartItemId}")
    public ResponseEntity<ResponseData<ShoppingCart>> changeQuantity(@PathVariable("cartItemId") Long cartItemId, @RequestParam("quantity") Long quantity) {
        ShoppingCart shoppingCart = userService.changeQuantity(cartItemId,quantity);
        return new ResponseEntity<>(new ResponseData<>("success",shoppingCart,HttpStatus.OK),HttpStatus.OK);
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
      Orders orders = userService.checkoutCartItem(receiveAddress,receiveName,receivePhone,note);
        return new ResponseEntity<>(new ResponseData<>("success",orders,HttpStatus.OK),HttpStatus.OK);
    }
    @GetMapping("/account")
    public ResponseEntity<ResponseData<User>> getAccount() {
      User user = userService.getAccount();
      return new ResponseEntity<>(new ResponseData<>("success",user,HttpStatus.OK),HttpStatus.OK);
    }
    @PutMapping("/user/account")
    public  ResponseEntity<ResponseData<User>> editAccount(@RequestBody UserEdit userEdit) {
     User user = userService.editAccount(userEdit);
     return new ResponseEntity<>(new ResponseData<>("success",user,HttpStatus.OK),HttpStatus.OK);
    }
    @PutMapping("/account/change-password")
    public ResponseEntity<ResponseData<String>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return new ResponseEntity<>(new ResponseData<>("success","success",HttpStatus.OK),HttpStatus.OK);
    }
}
