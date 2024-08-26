package com.krist.controller.product;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krist.dto.product.PostAttributeValueRequestDto;
import com.krist.entity.product.AttributeValue;
import com.krist.service.product.AttributeValueService;


@RestController
@RequestMapping("/public")
public class AttributeValueController {
    private final AttributeValueService attributeValueService;

    public AttributeValueController(AttributeValueService attributeValueService) {
        this.attributeValueService = attributeValueService;
    }

    @PostMapping("/attribute-value")
    public ResponseEntity<AttributeValue> postAttributeValue(
            @RequestBody PostAttributeValueRequestDto dto) {
        return ResponseEntity.ok().body(attributeValueService.postAttributeValue(dto));
    }

    @PostMapping("/attribute-values")
    public ResponseEntity<List<AttributeValue>> postAttributeValues(@RequestBody List<PostAttributeValueRequestDto> dtos) {
        return ResponseEntity.ok().body(attributeValueService.postAttributeValues(dtos));
    }
    
    @GetMapping("/attribute-value/{id}")
    public ResponseEntity<AttributeValue> getAttributeValue(@PathVariable Long id) {
        return ResponseEntity.ok().body(attributeValueService.getAttributeValue(id));
    }
    
    @GetMapping("/attribute-values")
    public ResponseEntity<List<AttributeValue>> getAttributeValues() {
        return ResponseEntity.ok().body(attributeValueService.getAttributeValues());
    }
}
