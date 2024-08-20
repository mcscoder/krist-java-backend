package com.krist.service.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.krist.dto.product.PostCategoryGroupRequestDto;
import com.krist.entity.product.CategoryGroup;
import com.krist.exception.custom.NotFoundException;
import com.krist.repository.product.CategoryGroupRepository;

@Service
public class CategoryGroupService {
    private final CategoryGroupRepository categoryGroupRepository;

    public CategoryGroupService(CategoryGroupRepository categoryGroupRepository) {
        this.categoryGroupRepository = categoryGroupRepository;
    }

    public CategoryGroup postCategoryGroup(PostCategoryGroupRequestDto dto) {
        CategoryGroup categoryGroup = new CategoryGroup(dto.name());

        return categoryGroupRepository.save(categoryGroup);
    }

    public List<CategoryGroup> postCategoryGroups(List<PostCategoryGroupRequestDto> dtos) {
        List<CategoryGroup> categoryGroups = new ArrayList<>();

        for (PostCategoryGroupRequestDto dto : dtos) {
            categoryGroups.add(postCategoryGroup(dto));
        }

        return categoryGroupRepository.saveAll(categoryGroups);
    }

    public CategoryGroup getCategoryGroup(Long id) {
        return categoryGroupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category group not found"));
    }

    public List<CategoryGroup> getCategoryGroups() {
        return categoryGroupRepository.findAll();
    }
}
