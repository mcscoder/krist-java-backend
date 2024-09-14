package com.krist.controller.attribute;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krist.dto.attribute.AttributeDto;
import com.krist.dto.attribute.AttributeWithAttributeValuesDto;
import com.krist.dto.attribute.PostAttributeRequestDto;
import com.krist.mapper.attribute.AttributeMapper;
import com.krist.service.attribute.AttributeService;

@RestController
@RequestMapping("/public")
public class AttributeController {
    private final AttributeService attributeService;

    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @PostMapping("/attribute")
    public ResponseEntity<AttributeDto> postAttribute(@RequestBody PostAttributeRequestDto dto) {
        return ResponseEntity.ok()
                .body(AttributeMapper.INSTANCE.toAttributeDto(attributeService.postAttribute(dto)));
    }

    @PostMapping("/attributes")
    public ResponseEntity<List<AttributeDto>> postAttributes(
            @RequestBody List<PostAttributeRequestDto> dtos) {
        return ResponseEntity.ok().body(attributeService.postAttributes(dtos).stream()
                .map(attribute -> AttributeMapper.INSTANCE.toAttributeDto(attribute)).toList());
    }

    @GetMapping("/attribute/{id}")
    public ResponseEntity<AttributeDto> getAttribute(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(AttributeMapper.INSTANCE.toAttributeDto(attributeService.getAttribute(id)));
    }

    @GetMapping("/attributes")
    public ResponseEntity<List<AttributeDto>> getAttributes() {
        return ResponseEntity.ok().body(attributeService.getAttributes().stream()
                .map(attribute -> AttributeMapper.INSTANCE.toAttributeDto(attribute)).toList());
    }

    @GetMapping("/attributes/attribute-values/category-group/{categoryGroupId}")
    public ResponseEntity<List<AttributeWithAttributeValuesDto>> getAttributesWithAttributeValuesByCategoryGroup(
            @PathVariable Long categoryGroupId) {
        return ResponseEntity.ok().body(
                attributeService.getAttributesWithAttributeValuesByCategoryGroup(categoryGroupId));
    }

    @GetMapping("/attributes/attribute-values/product/{productId}")
    public ResponseEntity<List<AttributeWithAttributeValuesDto>> getAttributesWithAttributeValuesByProduct(
            @PathVariable Long productId) {
        return ResponseEntity.ok()
                .body(attributeService.getAttributesWithAttributeValuesByProduct(productId));
    }
}
