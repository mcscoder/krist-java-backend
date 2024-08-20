package com.krist.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krist.entity.product.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
