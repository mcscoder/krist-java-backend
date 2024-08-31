package com.krist.entity.product;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.krist.entity.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Attribute extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // 1. Attribute values
    @OneToMany(mappedBy = "attribute")
    @JsonManagedReference
    private List<AttributeValue> attributeValues;

    // 2. Category group
    @ManyToOne
    @JoinColumn(name = "category_group_id")
    @JsonBackReference
    private CategoryGroup categoryGroup;

    public Attribute(Long id) {
        this.id = id;
    }

    public Attribute(String name, CategoryGroup categoryGroup) {
        this.name = name;
        this.categoryGroup = categoryGroup;
    }
}
