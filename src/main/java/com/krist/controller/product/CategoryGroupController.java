package com.krist.controller.product;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krist.dto.product.CategoryGroupDto;
import com.krist.dto.product.PostCategoryGroupRequestDto;
import com.krist.mapper.category.CategoryGroupMapper;
import com.krist.service.product.CategoryGroupService;

@RestController
@RequestMapping("/public")
public class CategoryGroupController {
    private final CategoryGroupService categoryGroupService;
    private final CategoryGroupMapper categoryGroupMapper;

    public CategoryGroupController(CategoryGroupService categoryGroupService) {
        this.categoryGroupService = categoryGroupService;
        this.categoryGroupMapper = CategoryGroupMapper.INSTANCE;
    }

    @PostMapping("/category-group")
    public ResponseEntity<CategoryGroupDto> postCategoryGroup(
            @RequestBody PostCategoryGroupRequestDto dto) {
        return ResponseEntity.ok().body(categoryGroupMapper
                .toCategoryGroupDto(categoryGroupService.postCategoryGroup(dto)));
    }

    @PostMapping("/category-groups")
    public ResponseEntity<List<CategoryGroupDto>> postCategoryGroups(
            @RequestBody List<PostCategoryGroupRequestDto> dtos) {
        return ResponseEntity.ok()
                .body(categoryGroupService.postCategoryGroups(dtos).stream()
                        .map(categoryGroup -> categoryGroupMapper.toCategoryGroupDto(categoryGroup))
                        .toList());
    }

    @GetMapping("/category-group/{id}")
    public ResponseEntity<CategoryGroupDto> getCategoryGroup(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                categoryGroupMapper.toCategoryGroupDto(categoryGroupService.getCategoryGroup(id)));
    }

    @GetMapping("/category-groups")
    public ResponseEntity<List<CategoryGroupDto>> getCategoryGroups() {
        return ResponseEntity.ok()
                .body(categoryGroupService.getCategoryGroups().stream()
                        .map(categoryGroup -> categoryGroupMapper.toCategoryGroupDto(categoryGroup))
                        .toList());
    }
}
