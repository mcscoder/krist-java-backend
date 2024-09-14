package com.krist.repository.attribute;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krist.entity.attribute.AttributeValue;

public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long> {
}
