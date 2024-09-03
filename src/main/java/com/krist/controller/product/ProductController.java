package com.krist.controller.product;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.krist.dto.product.PostProductRequestDto;
import com.krist.dto.product.ProductOverviewListDto;
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

    @GetMapping("/products/category-group/{groupCategoryId}")
    public ResponseEntity<ProductOverviewListDto> getProductOverviewListByFilters(
            @PathVariable Long groupCategoryId,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) String attributes,
            @RequestParam(required = false, defaultValue = "outstanding") String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "6") Integer pageSize) {
        return ResponseEntity.ok().body(productService.findProductOverviewListByFilters(
                groupCategoryId, categories, parseAttributes(attributes), sort, page, pageSize));
    }

    private Map<Long, List<Long>> parseAttributes(String attributes) {
        Map<Long, List<Long>> filtersMap = new HashMap<>();
        if (attributes != null && !attributes.isEmpty()) {
            String[] filters = attributes.split("_");
            for (String filter : filters) {
                String[] parts = filter.split("-");
                if (parts.length == 2) {
                    Long attributeId = Long.parseLong(parts[0]);
                    List<Long> valueIds = Arrays.stream(parts[1].split(",")).map(Long::parseLong)
                            .collect(Collectors.toList());
                    filtersMap.put(attributeId, valueIds);
                }
            }
        }
        return filtersMap;
    }
}
