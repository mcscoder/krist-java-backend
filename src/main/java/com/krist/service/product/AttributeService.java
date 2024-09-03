package com.krist.service.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.krist.dto.product.PostAttributeRequestDto;
import com.krist.entity.product.Attribute;
import com.krist.entity.product.CategoryGroup;
import com.krist.exception.custom.NotFoundException;
import com.krist.repository.product.AttributeRepository;

@Service
public class AttributeService {
    private final AttributeRepository attributeRepository;

    public AttributeService(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    public Attribute postAttribute(PostAttributeRequestDto dto) {
        CategoryGroup categoryGroup = new CategoryGroup(dto.categoryGroupId());
        Attribute attribute = new Attribute(dto.name(), categoryGroup);

        return attributeRepository.save(attribute);
    }

    public List<Attribute> postAttributes(List<PostAttributeRequestDto> dtos) {
        List<Attribute> attributes = new ArrayList<>();

        for (PostAttributeRequestDto dto : dtos) {
            attributes.add(postAttribute(dto));
        }

        return attributes;
    }

    public Attribute getAttribute(Long id) {
        return attributeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Attribute not found"));
    }

    public List<Attribute> getAttributes() {
        return attributeRepository.findAll();
    }
}
