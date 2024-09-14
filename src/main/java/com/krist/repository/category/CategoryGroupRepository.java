package com.krist.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krist.entity.category.CategoryGroup;

public interface CategoryGroupRepository extends JpaRepository<CategoryGroup, Long> {
    
}
