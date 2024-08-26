package com.krist.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krist.entity.product.CategoryGroup;

public interface CategoryGroupRepository extends JpaRepository<CategoryGroup, Long> {
    
}
