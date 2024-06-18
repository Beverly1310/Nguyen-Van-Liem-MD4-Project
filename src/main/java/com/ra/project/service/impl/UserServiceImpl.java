package com.ra.project.service.impl;

import com.ra.project.model.cons.OrderStatus;
import com.ra.project.model.dto.request.AddressRequest;
import com.ra.project.model.dto.request.ChangePasswordRequest;
import com.ra.project.model.dto.request.UserEdit;
import com.ra.project.model.entity.*;
import com.ra.project.repository.*;
import com.ra.project.security.principal.CustomUserDetail;
import com.ra.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    private final AddressRepository addressRepository;
    private final WishListRepository wishListRepository;

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

    public static CustomUserDetail getCurrentUser() {
        return (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

    @Transactional
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
            Product product = productRepository.findById(shoppingCart.getProduct().getId()).orElseThrow(() -> new NoSuchElementException("Product not found"));

            OrderDetail orderDetail = OrderDetail.builder()
                    .name(productRepository.getProductById(shoppingCart.getProduct().getId()).orElseThrow(() -> new NoSuchElementException("Product not found")).getProductName())
                    .orderQuantity(shoppingCart.getOrderQuantity())
                    .unitPrice(shoppingCart.getProduct().getUnitPrice())
                    .id(new OrderDetailId(order, shoppingCart.getProduct()))
                    .build();
            if (product.getStockQuantity() < orderDetail.getOrderQuantity()) {
                throw new RuntimeException("Product has no stock");
            }
            product.setStockQuantity(product.getStockQuantity() - orderDetail.getOrderQuantity());
            orderDetailRepository.save(orderDetail);
            productRepository.save(product);
            shoppingCartRepository.delete(shoppingCart);
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
        if (userEdit.getFullName() != null && !userEdit.getFullName().isBlank()) {
            user.setFullName(userEdit.getFullName());
        }
        if (userEdit.getEmail() != null && !userEdit.getEmail().isBlank()) {
            user.setEmail(userEdit.getEmail());
        }
        if (userEdit.getPhone() != null && !userEdit.getPhone().isBlank()) {
            user.setPhone(userEdit.getPhone());
        }
        if (userEdit.getAvatar() != null && !userEdit.getAvatar().isBlank()) {
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

    @Override
    public Address addAddress(AddressRequest addressRequest) {
        Address address = new Address();
        if (addressRequest.getFullAddress() != null && !addressRequest.getFullAddress().isBlank()) {
            address.setFullAddress(addressRequest.getFullAddress());
        } else {
            address.setFullAddress(getCurrentUser().getAddress());
        }
        if (addressRequest.getPhone() != null && !addressRequest.getPhone().isBlank()) {
            address.setPhone(addressRequest.getPhone());
        } else {
            address.setPhone(getCurrentUser().getPhone());
        }
        if (addressRequest.getReceiveName() != null && !addressRequest.getReceiveName().isBlank()) {
            address.setRecevieName(addressRequest.getReceiveName());
        } else {
            address.setRecevieName(getCurrentUser().getFullName());
        }
        address.setUser(userRepository.findById(getCurrentUser().getId()).orElseThrow(() -> new NoSuchElementException("User not found")));
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }

    @Override
    public List<Address> getAddresses() {
        Long id = getCurrentUser().getId();
        return addressRepository.getAddressByUserId(id);
    }

    @Override
    public List<String> getAddress(Long addressId) {
        return addressRepository.getAddressById(addressId).stream().map(Address::getFullAddress).toList();
    }

    @Override
    public List<Orders> getUserHistory() {
        return orderRepository.getOrdersByUserId(getCurrentUser().getId());
    }

    @Override
    public Orders getUserHistoryBySerialNumber(String serialNumber) {
        return orderRepository.findOrdersBySerialNumber(serialNumber);
    }

    @Override
    public List<Orders> getUserHistoryByOrderStatus(String orderStatus) {
        return switch (orderStatus.toUpperCase().trim()) {
            case "WAITING" -> orderRepository.getOrdersByStatusAndUserId(OrderStatus.WAITING, getCurrentUser().getId());
            case "CONFIRM" -> orderRepository.getOrdersByStatusAndUserId(OrderStatus.CONFIRM, getCurrentUser().getId());
            case "DELIVERY" ->
                    orderRepository.getOrdersByStatusAndUserId(OrderStatus.DELIVERY, getCurrentUser().getId());
            case "SUCCESS" -> orderRepository.getOrdersByStatusAndUserId(OrderStatus.SUCCESS, getCurrentUser().getId());
            case "CANCEL" -> orderRepository.getOrdersByStatusAndUserId(OrderStatus.CANCEL, getCurrentUser().getId());
            case "DENIED" -> orderRepository.getOrdersByStatusAndUserId(OrderStatus.DENIED, getCurrentUser().getId());
            default -> throw new RuntimeException("Invalid order status");
        };
    }

    @Override
    public Orders cancelOrders(Long orderId) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new NoSuchElementException("Order not found"));
        List<OrderDetail> orderDetails = orderDetailRepository.getOrderDetailsByOrderId(orderId);
        if (orders.getStatus() == OrderStatus.WAITING) {
            orders.setStatus(OrderStatus.CANCEL);
            for (OrderDetail orderDetail : orderDetails) {
                Product product = productRepository.findById(orderDetail.getId().getProduct().getId()).orElseThrow(() -> new NoSuchElementException("Product not found"));
                product.setStockQuantity(product.getStockQuantity() + orderDetail.getOrderQuantity());
                productRepository.save(product);
            }
            orderRepository.save(orders);
        } else {
            throw new RuntimeException("Order status is not WAITING");
        }
        return null;
    }

    @Override
    public WishList addToWishList(Long productId) {
        WishList wishList = wishListRepository.getWishListByProductIdAndUserId(productId, getCurrentUser().getId());
        if (wishList == null) {
            wishList = WishList.builder()
                    .product(productRepository.findById(productId).orElseThrow(() -> new NoSuchElementException("Product not found")))
                    .user(userRepository.findById(getCurrentUser().getId()).orElseThrow(() -> new NoSuchElementException("User not found")))
                    .build();
            return wishListRepository.save(wishList);
        }
        return wishList;
    }

    @Override
    public List<WishList> getWishList() {
        return wishListRepository.getWishListByUserId(getCurrentUser().getId());
    }

    @Override
    public void deleteWishList(Long wishListId) {
        wishListRepository.deleteById(wishListId);
    }


}

