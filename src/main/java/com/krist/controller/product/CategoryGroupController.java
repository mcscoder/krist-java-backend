package com.krist.controller.product;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krist.dto.product.PostCategoryGroupRequestDto;
import com.krist.entity.product.CategoryGroup;
import com.krist.service.product.CategoryGroupService;

@RestController
@RequestMapping("/public")
public class CategoryGroupController {
    private final CategoryGroupService categoryGroupService;

    public CategoryGroupController(CategoryGroupService categoryGroupService) {
        this.categoryGroupService = categoryGroupService;
    }

    @PostMapping("/category-group")
    public ResponseEntity<CategoryGroup> postCategoryGroup(
            @RequestBody PostCategoryGroupRequestDto dto) {
        return ResponseEntity.ok().body(categoryGroupService.postCategoryGroup(dto));
    }

    @PostMapping("/category-groups")
    public ResponseEntity<List<CategoryGroup>> postCategoryGroups(
            @RequestBody List<PostCategoryGroupRequestDto> dtos) {
        return ResponseEntity.ok().body(categoryGroupService.postCategoryGroups(dtos));
    }

    @GetMapping("/category-group/{id}")
    public ResponseEntity<CategoryGroup> getCategoryGroup(@PathVariable Long id) {
        return ResponseEntity.ok().body(categoryGroupService.getCategoryGroup(id));
    }

    @GetMapping("/category-groups")
    public ResponseEntity<List<CategoryGroup>> getCategoryGroups() {
        return ResponseEntity.ok().body(categoryGroupService.getCategoryGroups());
    }
}
