package com.krist.controller.product;

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
import com.krist.dto.product.ProductDetailsDto;
import com.krist.dto.product.ProductDto;
import com.krist.dto.product.ProductOverviewListDto;
import com.krist.service.product.ProductService;
import com.krist.util.string.StringParser;

@RestController
@RequestMapping("/public")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDto> postProduct(@RequestBody PostProductRequestDto dto) {
        return ResponseEntity.ok()
                .body(productService.toProductDto(productService.postProduct(dto)));
    }

    @PostMapping("/products")
    public ResponseEntity<List<ProductDto>> postProducts(
            @RequestBody List<PostProductRequestDto> dtos) {
        return ResponseEntity.ok().body(productService.postProducts(dtos).stream()
                .map(product -> productService.toProductDto(product)).toList());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok().body(productService.toProductDto(productService.getProduct(id)));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok().body(productService.getProducts().stream()
                .map(product -> productService.toProductDto(product)).toList());
    }

    @GetMapping("/products/best-sellers")
    public ResponseEntity<ProductOverviewListDto> getBestSellers() {
        return ResponseEntity.ok().body(productService.getBestSellers());
    }

    @GetMapping("/products/category-group/{groupCategoryId}")
    public ResponseEntity<ProductOverviewListDto> getProductOverviewListByFilters(
            @PathVariable Long groupCategoryId,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) String attributes,
            @RequestParam(required = false, defaultValue = "outstanding") String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "6") Integer pageSize) {

        return ResponseEntity.ok()
                .body(productService.getProductOverviewListByFilters(groupCategoryId, categories,
                        StringParser.parseAttributesWithMultipleValues(attributes), sort, page,
                        pageSize));
    }

    @GetMapping("/product/details/{productId}")
    public ResponseEntity<ProductDetailsDto> getProductDetails(@PathVariable Long productId) {
        return ResponseEntity.ok().body(productService.getProductDetailsByProductId(productId));
    }

    @GetMapping("/products/related/{categoryId}")
    public ResponseEntity<ProductOverviewListDto> getRelatedProducts(
            @PathVariable Long categoryId) {
        return ResponseEntity.ok().body(productService.getRelatedProductsByCategory(categoryId));
    }
}
