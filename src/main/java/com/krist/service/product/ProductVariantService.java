package com.krist.service.product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.krist.dto.product.PostProductVariantRequestDto;
import com.krist.entity.product.AttributeValue;
import com.krist.entity.product.Product;
import com.krist.entity.product.ProductVariant;
import com.krist.exception.custom.NotFoundException;
import com.krist.repository.product.ProductVariantRepository;

@Service
public class ProductVariantService {
    private final ProductVariantRepository productVariantRepository;

    public ProductVariantService(ProductVariantRepository productVariantRepository) {
        this.productVariantRepository = productVariantRepository;
    }

    public ProductVariant postProductVariant(PostProductVariantRequestDto dto) {
        Product product = new Product(dto.productId());
        Set<AttributeValue> attributeValues = new HashSet<>();

        for (Long attributeValueId : dto.attributeValueIds()) {
            attributeValues.add(new AttributeValue(attributeValueId));
        }

        ProductVariant productVariant =
                new ProductVariant(dto.price(), dto.quantity(), product, attributeValues);

        return productVariantRepository.save(productVariant);
    }

    public List<ProductVariant> postProductVariants(List<PostProductVariantRequestDto> dtos) {
        List<ProductVariant> productVariants = new ArrayList<>();

        for (PostProductVariantRequestDto dto : dtos) {
            productVariants.add(postProductVariant(dto));
        }

        return productVariants;
    }

    public ProductVariant getProductVariant(Long id) {
        return productVariantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product variant not found"));
    }

    public List<ProductVariant> getProductVariants() {
        return productVariantRepository.findAll();
    }
}
