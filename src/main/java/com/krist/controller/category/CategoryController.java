package com.krist.controller.category;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krist.dto.category.CategoryDto;
import com.krist.dto.category.PostCategoryRequestDto;
import com.krist.service.category.CategoryService;

@RestController
@RequestMapping("/public")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/category")
    public ResponseEntity<CategoryDto> postCategory(@RequestBody PostCategoryRequestDto dto) {
        return ResponseEntity.ok()
                .body(categoryService.toCategoryDto(categoryService.postCategory(dto)));
    }

    @PostMapping("/categories")
    public ResponseEntity<List<CategoryDto>> postCategories(
            @RequestBody List<PostCategoryRequestDto> dtos) {
        return ResponseEntity.ok().body(categoryService.postCategories(dtos).stream()
                .map(category -> categoryService.toCategoryDto(category)).toList());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(categoryService.toCategoryDto(categoryService.getCategory(id)));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return ResponseEntity.ok().body(categoryService.getCategories().stream()
                .map(category -> categoryService.toCategoryDto(category)).toList());
    }

    @GetMapping("/categories/category-group/{categoryGroupId}")
    public ResponseEntity<List<CategoryDto>> getCategoriesByCategoryGroup(
            @PathVariable Long categoryGroupId) {
        return ResponseEntity.ok()
                .body(categoryService.getCategoriesByCategoryGroup(categoryGroupId).stream()
                        .map(category -> categoryService.toCategoryDto(category)).toList());
    }
}
