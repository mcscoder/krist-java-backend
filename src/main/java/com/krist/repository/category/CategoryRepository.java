package com.krist.repository.category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.krist.entity.category.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c from Category c WHERE c.categoryGroup.id = :categoryGroupId")
    List<Category> findCategoriesByCategoryGroupId(Long categoryGroupId);
}
