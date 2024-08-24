package com.krist.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krist.entity.product.Attribute;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {
}
