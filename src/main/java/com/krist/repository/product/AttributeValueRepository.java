package com.krist.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krist.entity.product.AttributeValue;

public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long> {
}
