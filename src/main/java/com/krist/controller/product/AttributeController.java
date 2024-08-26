package com.krist.controller.product;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krist.dto.product.PostAttributeRequestDto;
import com.krist.entity.product.Attribute;
import com.krist.service.product.AttributeService;

@RestController
@RequestMapping("/public")
public class AttributeController {
    private final AttributeService attributeService;

    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @PostMapping("/attribute")
    public ResponseEntity<Attribute> postAttribute(@RequestBody PostAttributeRequestDto dto) {
        return ResponseEntity.ok().body(attributeService.postAttribute(dto));
    }

    @PostMapping("/attributes")
    public ResponseEntity<List<Attribute>> postAttributes(
            @RequestBody List<PostAttributeRequestDto> dtos) {
        return ResponseEntity.ok().body(attributeService.postAttributes(dtos));
    }

    @GetMapping("/attribute/{id}")
    public ResponseEntity<Attribute> getAttribute(@PathVariable Long id) {
        return ResponseEntity.ok().body(attributeService.getAttribute(id));
    }

    @GetMapping("/attributes")
    public ResponseEntity<List<Attribute>> getAttributes() {
        return ResponseEntity.ok().body(attributeService.getAttributes());
    }
}
