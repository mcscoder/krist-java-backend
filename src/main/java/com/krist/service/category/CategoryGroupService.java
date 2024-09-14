package com.krist.service.category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.krist.dto.category.PostCategoryGroupRequestDto;
import com.krist.entity.category.CategoryGroup;
import com.krist.entity.common.Image;
import com.krist.exception.custom.NotFoundException;
import com.krist.repository.category.CategoryGroupRepository;

@Service
public class CategoryGroupService {
    private final CategoryGroupRepository categoryGroupRepository;

    public CategoryGroupService(CategoryGroupRepository categoryGroupRepository) {
        this.categoryGroupRepository = categoryGroupRepository;
    }

    public CategoryGroup postCategoryGroup(PostCategoryGroupRequestDto dto) {
        Image image = new Image(dto.imageId());
        CategoryGroup categoryGroup = new CategoryGroup(dto.name(), image);

        return categoryGroupRepository.save(categoryGroup);
    }

    public List<CategoryGroup> postCategoryGroups(List<PostCategoryGroupRequestDto> dtos) {
        List<CategoryGroup> categoryGroups = new ArrayList<>();

        for (PostCategoryGroupRequestDto dto : dtos) {
            categoryGroups.add(postCategoryGroup(dto));
        }

        return categoryGroups;
    }

    public CategoryGroup getCategoryGroup(Long id) {
        return categoryGroupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category group not found"));
    }

    public List<CategoryGroup> getCategoryGroups() {
        return categoryGroupRepository.findAll();
    }
}
