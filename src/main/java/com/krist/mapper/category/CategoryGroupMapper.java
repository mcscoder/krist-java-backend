package com.krist.mapper.category;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.krist.dto.product.CategoryGroupDto;
import com.krist.entity.product.CategoryGroup;

@Mapper
public interface CategoryGroupMapper {
    final CategoryGroupMapper INSTANCE = Mappers.getMapper(CategoryGroupMapper.class);

    @Mapping(target = "image", source = "image.src")
    CategoryGroupDto toCategoryGroupDto(CategoryGroup categoryGroup);
}
