package com.krist.controller.attribute;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krist.dto.attribute.AttributeValueDto;
import com.krist.dto.attribute.PostAttributeValueRequestDto;
import com.krist.mapper.attribute.AttributeValueMapper;
import com.krist.service.attribute.AttributeValueService;

@RestController
@RequestMapping("/public")
public class AttributeValueController {
    private final AttributeValueService attributeValueService;

    public AttributeValueController(AttributeValueService attributeValueService) {
        this.attributeValueService = attributeValueService;
    }

    @PostMapping("/attribute-value")
    public ResponseEntity<AttributeValueDto> postAttributeValue(
            @RequestBody PostAttributeValueRequestDto dto) {
        return ResponseEntity.ok().body(AttributeValueMapper.INSTANCE
                .toAttributeValueDto((attributeValueService.postAttributeValue(dto))));
    }

    @PostMapping("/attribute-values")
    public ResponseEntity<List<AttributeValueDto>> postAttributeValues(
            @RequestBody List<PostAttributeValueRequestDto> dtos) {
        return ResponseEntity.ok()
                .body(attributeValueService.postAttributeValues(dtos).stream()
                        .map((attributeValue -> AttributeValueMapper.INSTANCE
                                .toAttributeValueDto(attributeValue)))
                        .toList());
    }

    @GetMapping("/attribute-value/{id}")
    public ResponseEntity<AttributeValueDto> getAttributeValue(@PathVariable Long id) {
        return ResponseEntity.ok().body(AttributeValueMapper.INSTANCE
                .toAttributeValueDto(attributeValueService.getAttributeValue(id)));
    }

    @GetMapping("/attribute-values")
    public ResponseEntity<List<AttributeValueDto>> getAttributeValues() {
        return ResponseEntity.ok()
                .body(attributeValueService.getAttributeValues().stream()
                        .map((attributeValue -> AttributeValueMapper.INSTANCE
                                .toAttributeValueDto(attributeValue)))
                        .toList());
    }
}
