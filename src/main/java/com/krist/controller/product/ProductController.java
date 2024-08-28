package com.krist.controller.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.krist.dto.product.PostProductRequestDto;
import com.krist.entity.product.Product;
import com.krist.service.product.ProductService;

@RestController
@RequestMapping("/public")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseEntity<Product> postProduct(@RequestBody PostProductRequestDto dto) {
        return ResponseEntity.ok().body(productService.postProduct(dto));
    }

    @PostMapping("/products")
    public ResponseEntity<List<Product>> postProducts(
            @RequestBody List<PostProductRequestDto> dtos) {
        return ResponseEntity.ok().body(productService.postProducts(dtos));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok().body(productService.getProduct(id));
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok().body(productService.getProducts());
    }

    @GetMapping("/best-sellers")
    public ResponseEntity<List<Product>> getBestSellers() {
        return ResponseEntity.ok().body(productService.getBestSellers());
    }

    @GetMapping("/products/category-group/{categoryGroupId}")
    public ResponseEntity<List<Product>> getProductsByCategoryGroup(
            @PathVariable Long categoryGroupId, @RequestParam(required = false) String categories) {
        List<Long> categoryIds = new ArrayList<>();
        for (String categoryId : categories.split("-")) {
            categoryIds.add(Long.valueOf(categoryId));
        }

        return ResponseEntity
                .ok(productService.getProductsByCategoryGroup(categoryGroupId, categoryIds));
    }

    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok().body(productService.getProductsByCategory(categoryId));
    }
}
