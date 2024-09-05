package com.krist.dto.product;

import java.util.List;

import com.krist.dto.common.ImageDto;

public record ProductDto(Long id, String name, String title, String description, Integer sold,
        List<ImageDto> images) {
}
