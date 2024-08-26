package com.krist.service.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.krist.dto.product.PostCategoryRequestDto;
import com.krist.entity.product.Category;
import com.krist.entity.product.CategoryGroup;
import com.krist.exception.custom.NotFoundException;
import com.krist.repository.product.CategoryRepository;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryGroupService categoryGroupService;

    public CategoryService(CategoryRepository categoryRepository,
            CategoryGroupService categoryGroupService) {
        this.categoryRepository = categoryRepository;
        this.categoryGroupService = categoryGroupService;
    }

    public Category postCategory(PostCategoryRequestDto dto) {
        CategoryGroup categoryGroup = categoryGroupService.getCategoryGroup(dto.categoryGroupId());
        Category category = new Category(dto.name(), categoryGroup);

        return categoryRepository.save(category);
    }

    public List<Category> postCategories(List<PostCategoryRequestDto> dtos) {
        List<Category> categories = new ArrayList<>();

        for (PostCategoryRequestDto dto : dtos) {
            categories.add(postCategory(dto));
        }

        return categories;
    }

    public Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
