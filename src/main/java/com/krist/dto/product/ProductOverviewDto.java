package com.krist.dto.product;

public record ProductOverviewDto(Long id, String name, String title, String description, int sold,
                String image, Double lowestPrice) {
}
