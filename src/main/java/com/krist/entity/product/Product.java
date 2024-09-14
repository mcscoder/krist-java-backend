package com.krist.entity.product;

import java.util.List;
import java.util.Set;

import com.krist.entity.common.BaseEntity;
import com.krist.entity.common.Image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String title;

    // Reference docs:
    // https://stackoverflow.com/a/13364085/20232773
    @Column(columnDefinition = "TEXT", nullable = false)
    String description;

    @Column(nullable = false)
    Integer sold;

    // 1. Images
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_image", joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    Set<Image> images;

    // 2. Category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;

    // 3. Product Variants
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    List<ProductVariant> productVariants;

    public Product(Long id) {
        this.id = id;
    }

    public Product(String name, String title, String description, Integer sold, Set<Image> images,
            Category category) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.sold = sold;
        this.images = images;
        this.category = category;
    }
}
