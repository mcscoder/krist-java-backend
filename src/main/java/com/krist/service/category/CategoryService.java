package com.krist.service.category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.krist.dto.category.CategoryDto;
import com.krist.dto.category.PostCategoryRequestDto;
import com.krist.entity.category.Category;
import com.krist.entity.category.CategoryGroup;
import com.krist.exception.custom.NotFoundException;
import com.krist.mapper.category.CategoryMapper;
import com.krist.repository.category.CategoryRepository;

@Service
public class CategoryService implements CategoryMapper {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category postCategory(PostCategoryRequestDto dto) {
        CategoryGroup categoryGroup = new CategoryGroup(dto.categoryGroupId());
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

    public List<Category> getCategoriesByCategoryGroup(Long categoryGroupId) {
        return categoryRepository.findCategoriesByCategoryGroupId(categoryGroupId);
    }

    public CategoryDto toCategoryDto(Category category) {
        return INSTANCE.toCategoryDto(category);
    }
}
