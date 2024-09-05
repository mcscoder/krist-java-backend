package com.krist.entity.product;

import java.util.List;
import java.util.Set;

import com.krist.dto.common.ImageDto;
import com.krist.dto.product.ProductDto;
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
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String title;

    // Reference docs:
    // https://stackoverflow.com/a/13364085/20232773
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer sold;

    // 1. Images
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_image", joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<Image> images;

    // 2. Categories
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

    // 3. Product Variants
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductVariant> productVariants;

    public Product(Long id) {
        this.id = id;
    }

    public Product(String name, String title, String description, Integer sold, Set<Image> images,
            Set<Category> categories, List<ProductVariant> productVariants) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.sold = sold;
        this.images = images;
        this.categories = categories;
        this.productVariants = productVariants;
    }

    @Override
    public ProductDto toDto() {
        return new ProductDto(id, name, title, description, sold,
                images.stream().map(image -> new ImageDto(image.getId(), image.getSrc())).toList());
    }
}
