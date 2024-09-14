package com.krist.dto.product;

import java.util.List;

import com.krist.dto.attribute.AttributeWithAttributeValuesDto;
import com.krist.dto.category.CategoryDto;
import com.krist.dto.category.CategoryGroupDto;

public record ProductDetailsDto(ProductDto product, CategoryGroupDto categoryGroup,
                CategoryDto category, List<AttributeWithAttributeValuesDto> attributes) {
}
