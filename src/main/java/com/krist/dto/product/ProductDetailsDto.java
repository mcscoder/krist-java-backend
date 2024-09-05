package com.krist.dto.product;

import java.util.List;

public record ProductDetailsDto(ProductDto product, CategoryGroupDto categoryGroup,
                CategoryDto category, List<AttributeWithAttributeValuesDto> attributes) {
}
