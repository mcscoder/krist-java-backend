package com.krist.service.attribute;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.krist.dto.attribute.AttributeDto;
import com.krist.dto.attribute.AttributeValueDto;
import com.krist.dto.attribute.AttributeWithAttributeValuesDto;
import com.krist.dto.attribute.PostAttributeRequestDto;
import com.krist.entity.attribute.Attribute;
import com.krist.entity.attribute.AttributeValue;
import com.krist.entity.category.CategoryGroup;
import com.krist.exception.custom.NotFoundException;
import com.krist.mapper.attribute.AttributeMapper;
import com.krist.mapper.attribute.AttributeValueMapper;
import com.krist.repository.attribute.AttributeRepository;

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

    public List<AttributeWithAttributeValuesDto> getAttributesWithAttributeValuesByCategoryGroup(
            Long categoryGroupId) {
        return attributeRepository.findAttributesByCategoryGroupId(categoryGroupId).stream().map(
                attribute -> AttributeMapper.INSTANCE.toAttributeWithAttributeValuesDto(attribute))
                .toList();
    }

    public List<AttributeWithAttributeValuesDto> getAttributesWithAttributeValuesByProduct(
            Long productId) {
        List<AttributeValue> attributeValues =
                attributeRepository.findAttributesByProductId(productId);

        List<AttributeWithAttributeValuesDto> attributeWithAttributeValues = new ArrayList<>();

        attributeValues.forEach((attributeValue) -> {
            Integer itemIndex = -1;
            // Loop to check if Attribute is already exists in attributeWithAttributeValues
            // If it exists the itemIndex would be not be -1
            for (Integer i = 0; i < attributeWithAttributeValues.size(); i++) {
                if (attributeWithAttributeValues.get(i).getAttribute().id()
                        .equals(attributeValue.getAttribute().getId())) {
                    itemIndex = i;
                    break;
                }
            }

            if (itemIndex != -1) {
                // The attribute does exists then just need to add a new AttributeValueDto to the
                // attribute its belong to at itemIndex
                AttributeValueDto attributeValueDto =
                        AttributeValueMapper.INSTANCE.toAttributeValueDto(attributeValue);

                attributeWithAttributeValues.get(itemIndex).getAttributeValues()
                        .add(attributeValueDto);
            } else {
                // The attribute does not exists then add a new AttributeWithAttributeValuesDto
                AttributeDto attributeDto =
                        AttributeMapper.INSTANCE.toAttributeDto(attributeValue.getAttribute());
                List<AttributeValueDto> attributeValueDtos = new ArrayList<>(
                        List.of(AttributeValueMapper.INSTANCE.toAttributeValueDto(attributeValue)));
                AttributeWithAttributeValuesDto attributeWithAttributeValuesDto =
                        new AttributeWithAttributeValuesDto(attributeDto, attributeValueDtos);

                attributeWithAttributeValues.add(attributeWithAttributeValuesDto);
            }
        });

        return attributeWithAttributeValues;
    }
}
