package com.krist.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krist.entity.product.ProductVariant;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
}
