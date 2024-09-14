package com.krist.dto.product;

import java.util.List;

public record PostProductRequestDto(String title, String name, String description,
        List<Long> imageIds, Long categoryId) {
}
