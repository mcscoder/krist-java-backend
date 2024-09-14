package com.krist.mapper.product;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.krist.dto.product.ProductVariantDto;
import com.krist.entity.product.ProductVariant;

@Mapper
public interface ProductVariantMapper {
    final ProductVariantMapper INSTANCE = Mappers.getMapper(ProductVariantMapper.class);

    ProductVariantDto toProductVariantDto(ProductVariant productVariant);
}
