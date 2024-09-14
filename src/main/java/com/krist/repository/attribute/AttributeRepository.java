package com.krist.repository.attribute;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.krist.entity.attribute.Attribute;
import com.krist.entity.attribute.AttributeValue;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {
    @Query("SELECT a FROM Attribute a WHERE a.categoryGroup.id = :categoryGroupId")
    List<Attribute> findAttributesByCategoryGroupId(Long categoryGroupId);

    @Query("SELECT av FROM AttributeValue av JOIN av.productVariants pv WHERE pv.product.id = :productId")
    List<AttributeValue> findAttributesByProductId(Long productId);
}
