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
import com.krist.entity.product.ProductVariant;
import com.krist.service.product.ProductVariantService;

@RestController
@RequestMapping("/public")
public class ProductVariantController {
    private final ProductVariantService productVariantService;

    public ProductVariantController(ProductVariantService productVariantService) {
        this.productVariantService = productVariantService;
    }

    @PostMapping("/product-variant")
    public ResponseEntity<ProductVariant> postProductVariant(
            @RequestBody PostProductVariantRequestDto dto) {
        return ResponseEntity.ok().body(productVariantService.postProductVariant(dto));
    }

    @PostMapping("/product-variants")
    public ResponseEntity<List<ProductVariant>> postProductVariants(
            @RequestBody List<PostProductVariantRequestDto> dtos) {
        return ResponseEntity.ok().body(productVariantService.postProductVariants(dtos));
    }

    @GetMapping("/product-variant/{id}")
    public ResponseEntity<ProductVariant> getProductVariant(@PathVariable Long id) {
        return ResponseEntity.ok().body(productVariantService.getProductVariant(id));
    }

    @GetMapping("/product-variants")
    public ResponseEntity<List<ProductVariant>> getProductVariants() {
        return ResponseEntity.ok().body(productVariantService.getProductVariants());
    }
}
