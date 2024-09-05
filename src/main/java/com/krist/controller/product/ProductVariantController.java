package com.krist.controller.product;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krist.dto.product.PostProductVariantRequestDto;
import com.krist.dto.product.ProductVariantDto;
import com.krist.service.product.ProductVariantService;
import com.krist.util.StringParser;

@RestController
@RequestMapping("/public")
public class ProductVariantController {
    private final ProductVariantService productVariantService;

    public ProductVariantController(ProductVariantService productVariantService) {
        this.productVariantService = productVariantService;
    }

    @PostMapping("/product-variant")
    public ResponseEntity<ProductVariantDto> postProductVariant(
            @RequestBody PostProductVariantRequestDto dto) {
        return ResponseEntity.ok().body(productVariantService.postProductVariant(dto).toDto());
    }

    @PostMapping("/product-variants")
    public ResponseEntity<List<ProductVariantDto>> postProductVariants(
            @RequestBody List<PostProductVariantRequestDto> dtos) {
        return ResponseEntity.ok().body(productVariantService.postProductVariants(dtos).stream()
                .map(productVariant -> productVariant.toDto()).toList());
    }

    @GetMapping("/product-variant/{id}")
    public ResponseEntity<ProductVariantDto> getProductVariant(@PathVariable Long id) {
        return ResponseEntity.ok().body(productVariantService.getProductVariant(id).toDto());
    }

    @GetMapping("/product-variants")
    public ResponseEntity<List<ProductVariantDto>> getProductVariants() {
        return ResponseEntity.ok().body(productVariantService.getProductVariants().stream()
                .map(productVariant -> productVariant.toDto()).toList());
    }

    @GetMapping("/product-variant/attributes/{productId}/{attributes}")
    public ResponseEntity<ProductVariantDto> getProductVariantByAttributes(
            @PathVariable Long productId, @PathVariable String attributes) {
        return ResponseEntity
                .ok().body(
                        productVariantService
                                .getProductVariantByAttributes(productId,
                                        StringParser.parseAttributesWithSingleValue(attributes))
                                .toDto());
    }
}
