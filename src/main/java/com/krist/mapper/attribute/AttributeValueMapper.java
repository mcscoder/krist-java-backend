package com.krist.mapper.attribute;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.krist.dto.attribute.AttributeValueDto;
import com.krist.entity.attribute.AttributeValue;

@Mapper
public interface AttributeValueMapper {
    final AttributeValueMapper INSTANCE = Mappers.getMapper(AttributeValueMapper.class);

    AttributeValueDto toAttributeValueDto(AttributeValue attributeValue);
}
