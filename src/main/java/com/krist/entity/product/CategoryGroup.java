package com.krist.entity.product;

import java.util.List;

import com.krist.dto.product.CategoryGroupDto;
import com.krist.entity.common.BaseEntity;
import com.krist.entity.common.Image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class CategoryGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // 1. Categories
    @OneToMany(mappedBy = "categoryGroup", fetch = FetchType.LAZY)
    private List<Category> categories;

    // 2. Attributes
    @OneToMany(mappedBy = "categoryGroup", fetch = FetchType.LAZY)
    private List<Attribute> attributes;

    // 3. Image
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    public CategoryGroup(Long id) {
        this.id = id;
    }

    public CategoryGroup(String name, Image image) {
        this.name = name;
        this.image = image;
    }

    @Override
    public CategoryGroupDto toDto() {
        return new CategoryGroupDto(id, name, image.getSrc());
    }
}
