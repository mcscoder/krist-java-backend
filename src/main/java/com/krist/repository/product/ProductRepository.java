package com.krist.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krist.entity.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
