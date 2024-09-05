package com.krist.controller.product;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krist.dto.product.AttributeDto;
import com.krist.dto.product.AttributeWithAttributeValuesDto;
import com.krist.dto.product.PostAttributeRequestDto;
import com.krist.service.product.AttributeService;

@RestController
@RequestMapping("/public")
public class AttributeController {
    private final AttributeService attributeService;

    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @PostMapping("/attribute")
    public ResponseEntity<AttributeDto> postAttribute(@RequestBody PostAttributeRequestDto dto) {
        return ResponseEntity.ok().body(attributeService.postAttribute(dto).toDto());
    }

    @PostMapping("/attributes")
    public ResponseEntity<List<AttributeDto>> postAttributes(
            @RequestBody List<PostAttributeRequestDto> dtos) {
        return ResponseEntity.ok().body(attributeService.postAttributes(dtos).stream()
                .map(attribute -> attribute.toDto()).toList());
    }

    @GetMapping("/attribute/{id}")
    public ResponseEntity<AttributeDto> getAttribute(@PathVariable Long id) {
        return ResponseEntity.ok().body(attributeService.getAttribute(id).toDto());
    }

    @GetMapping("/attributes")
    public ResponseEntity<List<AttributeDto>> getAttributes() {
        return ResponseEntity.ok().body(attributeService.getAttributes().stream()
                .map(attribute -> attribute.toDto()).toList());
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
