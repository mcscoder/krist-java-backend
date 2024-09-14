package com.krist.dto.attribute;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttributeWithAttributeValuesDto {
    private AttributeDto attribute;
    private List<AttributeValueDto> attributeValues;
}
