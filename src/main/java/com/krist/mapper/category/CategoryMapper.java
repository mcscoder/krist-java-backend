package com.krist.mapper.category;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.krist.dto.category.CategoryDto;
import com.krist.entity.category.Category;

@Mapper
public interface CategoryMapper {
    final CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDto toCategoryDto(Category category);
}
