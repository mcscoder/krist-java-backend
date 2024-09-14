package com.krist.service.product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.krist.dto.product.PostProductVariantRequestDto;
import com.krist.entity.attribute.Attribute;
import com.krist.entity.attribute.AttributeValue;
import com.krist.entity.product.Product;
import com.krist.entity.product.ProductVariant;
import com.krist.exception.custom.NotFoundException;
import com.krist.repository.product.ProductVariantRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class ProductVariantService {
    private final ProductVariantRepository productVariantRepository;
    private final EntityManager entityManager;

    public ProductVariantService(ProductVariantRepository productVariantRepository,
            EntityManager entityManager) {
        this.productVariantRepository = productVariantRepository;
        this.entityManager = entityManager;
    }

    public ProductVariant postProductVariant(PostProductVariantRequestDto dto) {
        Product product = new Product(dto.productId());
        Set<AttributeValue> attributeValues = new HashSet<>();

        for (Long attributeValueId : dto.attributeValueIds()) {
            attributeValues.add(new AttributeValue(attributeValueId));
        }

        ProductVariant productVariant =
                new ProductVariant(dto.price(), dto.quantity(), product, attributeValues);

        return productVariantRepository.save(productVariant);
    }

    public List<ProductVariant> postProductVariants(List<PostProductVariantRequestDto> dtos) {
        List<ProductVariant> productVariants = new ArrayList<>();

        for (PostProductVariantRequestDto dto : dtos) {
            productVariants.add(postProductVariant(dto));
        }

        return productVariants;
    }

    public ProductVariant getProductVariant(Long id) {
        return productVariantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product variant not found"));
    }

    public List<ProductVariant> getProductVariants() {
        return productVariantRepository.findAll();
    }

    public ProductVariant getProductVariantByAttributes(Long productId,
            Map<Long, Long> attributes) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductVariant> query = cb.createQuery(ProductVariant.class);
        Root<ProductVariant> productVariant = query.from(ProductVariant.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(productVariant.get("product").get("id"), productId));

        attributes.forEach((attributeId, attributeValueId) -> {
            // 1. Join with attributeValues
            Join<ProductVariant, AttributeValue> attributeValueJoin =
                    productVariant.join("attributeValues");
            // 2. Join with attribute
            Join<AttributeValue, Attribute> attributeJoin = attributeValueJoin.join("attribute");

            Predicate filterPredicate = cb.and(cb.equal(attributeJoin.get("id"), attributeId),
                    cb.equal(attributeValueJoin.get("id"), attributeValueId));
            predicates.add(filterPredicate);
        });

        query.select(productVariant).where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList().get(0);
    }
}
