package com.krist.dto.product;

public record ProductOverviewDTO(Long id, String name, String title, Integer sold, String imageSrc,
                Double lowestPrice) {
}
