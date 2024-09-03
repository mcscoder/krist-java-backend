package com.krist.dto.product;

import java.util.List;

public record ProductOverviewListDto(List<ProductOverviewDto> productOverviews, Integer maxPageNumber,
        Integer pageNumber, Integer pageSize, Integer numberOfItems) {
}
