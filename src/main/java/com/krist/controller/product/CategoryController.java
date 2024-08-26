package com.krist.controller.product;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krist.dto.product.PostCategoryRequestDto;
import com.krist.entity.product.Category;
import com.krist.service.product.CategoryService;

@RestController
@RequestMapping("/public")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/category")
    public ResponseEntity<Category> postCategory(@RequestBody PostCategoryRequestDto dto) {
        return ResponseEntity.ok().body(categoryService.postCategory(dto));
    }

    @PostMapping("/categories")
    public ResponseEntity<List<Category>> postCategories(
            @RequestBody List<PostCategoryRequestDto> dtos) {
        return ResponseEntity.ok().body(categoryService.postCategories(dtos));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok().body(categoryService.getCategory(id));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok().body(categoryService.getCategories());
    }

    @GetMapping("/categories/category-group/{categoryGroupId}")
    public ResponseEntity<List<Category>> getCategoriesByCategoryGroup(
            @PathVariable Long categoryGroupId) {
        return ResponseEntity.ok()
                .body(categoryService.getCategoriesByCategoryGroup(categoryGroupId));
    }

}
