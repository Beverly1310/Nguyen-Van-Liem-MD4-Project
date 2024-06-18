package com.ra.project.repository;

import com.ra.project.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c ")
    Page<Category> getAll(Pageable pageable);
    Optional<Category> findCategoryByCategoryName(String categoryName);
}
