package com.krist.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.krist.entity.product.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c from Category c JOIN c.categoryGroup cg WHERE cg.id = :categoryGroupId")
    List<Category> findCategoriesByCategoryGroupId(Long categoryGroupId);
}
