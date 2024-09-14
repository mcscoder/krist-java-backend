package com.krist.mapper.category;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.krist.dto.product.CategoryDto;
import com.krist.entity.product.Category;

@Mapper
public interface CategoryMapper {
    final CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDto toCategoryDto(Category category);
}
