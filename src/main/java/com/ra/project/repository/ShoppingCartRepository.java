package com.ra.project.repository;

import com.ra.project.model.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    void deleteShoppingCartByUserId(Long userId);
    List<ShoppingCart> findShoppingCartByUserId(Long userId);
    Optional<ShoppingCart> findShoppingCartByUserIdAndProductId(Long userId,Long productId);
}
