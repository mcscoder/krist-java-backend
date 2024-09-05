package com.krist.entity.product;

import java.util.Set;

import com.krist.dto.product.AttributeValueDto;
import com.krist.entity.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class AttributeValue extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // 1. Attribute
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

    // 2. ProductVariant
    @ManyToMany(mappedBy = "attributeValues", fetch = FetchType.LAZY)
    private Set<ProductVariant> productVariants;

    public AttributeValue(Long id) {
        this.id = id;
    }

    public AttributeValue(String name, Attribute attribute) {
        this.name = name;
        this.attribute = attribute;
    }

    @Override
    public AttributeValueDto toDto() {
        return new AttributeValueDto(id, name);
    }
}
