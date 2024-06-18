package com.ra.project.repository;

import com.ra.project.model.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Long> {
   WishList getWishListByProductIdAndUserId(Long productId, Long userId);
   List<WishList> getWishListByUserId(Long userId);
}
