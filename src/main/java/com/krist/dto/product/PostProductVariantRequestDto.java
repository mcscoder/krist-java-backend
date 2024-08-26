package com.krist.dto.product;

import java.util.List;

public record PostProductVariantRequestDto(Double price, Integer quantity, Long productId,
        List<Long> attributeValueIds) {
}
