package com.krist.service.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.krist.dto.product.PostAttributeValueRequestDto;
import com.krist.entity.product.Attribute;
import com.krist.entity.product.AttributeValue;
import com.krist.exception.custom.NotFoundException;
import com.krist.repository.product.AttributeValueRepository;

@Service
public class AttributeValueService {
    private final AttributeValueRepository attributeValueRepository;

    public AttributeValueService(AttributeValueRepository attributeValueRepository) {
        this.attributeValueRepository = attributeValueRepository;
    }

    public AttributeValue postAttributeValue(PostAttributeValueRequestDto dto) {
        Attribute attribute = new Attribute(dto.attributeId());
        AttributeValue attributeValue = new AttributeValue(dto.name(), attribute);

        return attributeValueRepository.save(attributeValue);
    }

    public List<AttributeValue> postAttributeValues(List<PostAttributeValueRequestDto> dtos) {
        List<AttributeValue> attributeValues = new ArrayList<>();

        for (PostAttributeValueRequestDto dto : dtos) {
            attributeValues.add(postAttributeValue(dto));
        }

        return attributeValues;
    }

    public AttributeValue getAttributeValue(Long id) {
        return attributeValueRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Attribute value not found"));
    }

    public List<AttributeValue> getAttributeValues() {
        return attributeValueRepository.findAll();
    }
}
