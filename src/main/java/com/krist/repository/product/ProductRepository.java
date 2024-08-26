package com.krist.repository.product;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.krist.entity.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByOrderBySoldDesc(Pageable pageable);
}
