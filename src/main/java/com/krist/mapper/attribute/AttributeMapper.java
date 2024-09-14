package com.krist.mapper.attribute;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.krist.dto.attribute.AttributeDto;
import com.krist.dto.attribute.AttributeValueDto;
import com.krist.dto.attribute.AttributeWithAttributeValuesDto;
import com.krist.entity.attribute.Attribute;

@Mapper
public interface AttributeMapper {
    final AttributeMapper INSTANCE = Mappers.getMapper(AttributeMapper.class);

    AttributeDto toAttributeDto(Attribute attribute);

    default AttributeWithAttributeValuesDto toAttributeWithAttributeValuesDto(Attribute attribute) {
        AttributeDto attributeDto = AttributeMapper.INSTANCE.toAttributeDto(attribute);
        List<AttributeValueDto> attributeWithAttributeValuesDtos = attribute.getAttributeValues()
                .stream().map((attributeValue) -> AttributeValueMapper.INSTANCE
                        .toAttributeValueDto(attributeValue))
                .toList();
        return new AttributeWithAttributeValuesDto(attributeDto, attributeWithAttributeValuesDtos);
    }
}
