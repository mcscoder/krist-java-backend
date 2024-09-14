package com.krist.mapper.product;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.krist.dto.product.AttributeWithAttributeValuesDto;
import com.krist.dto.product.CategoryDto;
import com.krist.dto.product.CategoryGroupDto;
import com.krist.dto.product.ProductDetailsDto;
import com.krist.dto.product.ProductDto;
import com.krist.entity.product.Product;
import com.krist.mapper.category.CategoryGroupMapper;
import com.krist.mapper.category.CategoryMapper;

@Mapper
public interface ProductMapper {
    final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDto toProductDto(Product product);

    default ProductDetailsDto toProductDetailsDto(Product product,
            List<AttributeWithAttributeValuesDto> attributes) {
        ProductDto productDto = ProductMapper.INSTANCE.toProductDto(product);
        CategoryDto categoryDto = CategoryMapper.INSTANCE.toCategoryDto(product.getCategory());
        CategoryGroupDto categoryGroupDto = CategoryGroupMapper.INSTANCE
                .toCategoryGroupDto(product.getCategory().getCategoryGroup());

        ProductDetailsDto productDetails =
                new ProductDetailsDto(productDto, categoryGroupDto, categoryDto, attributes);

        return productDetails;
    };
}
