package com.krist.service.product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.krist.dto.product.PostProductRequestDto;
import com.krist.entity.common.Image;
import com.krist.entity.product.Attribute;
import com.krist.entity.product.AttributeValue;
import com.krist.entity.product.Category;
import com.krist.entity.product.CategoryGroup;
import com.krist.entity.product.Product;
import com.krist.entity.product.ProductVariant;
import com.krist.exception.custom.NotFoundException;
import com.krist.repository.product.ProductRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final EntityManager entityManager;

    public ProductService(ProductRepository productRepository, EntityManager entityManager) {
        this.productRepository = productRepository;
        this.entityManager = entityManager;
    };

    public Product postProduct(PostProductRequestDto dto) {
        Set<Image> images = new HashSet<>();
        Set<Category> categories = new HashSet<>();

        for (Long imageId : dto.imageIds()) {
            images.add(new Image(imageId));
        }

        for (Long categoryId : dto.categoryIds()) {
            categories.add(new Category(categoryId));
        }

        Product product = new Product(dto.name(), dto.title(), dto.description(), 0, images,
                categories, null);

        return productRepository.save(product);
    }

    public List<Product> postProducts(List<PostProductRequestDto> dtos) {
        List<Product> products = new ArrayList<>();

        for (PostProductRequestDto dto : dtos) {
            products.add(postProduct(dto));
        }

        return products;
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public List<Product> getBestSellers() {
        Pageable pageable = PageRequest.of(0, 8);
        return productRepository.findByOrderBySoldDesc(pageable);
    }

    public List<Product> findProductsByFilters(Long categoryGroupId, List<Long> categoryIds,
            Map<Long, List<Long>> attributes, String sortDirection, Integer pageNumber) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> product = query.from(Product.class);

        // 1. Join with categories
        Join<Product, Category> categoryJoin = product.join("categories", JoinType.LEFT);
        // 2. Join with categoryGroup
        Join<Category, CategoryGroup> categoryGroupJoin =
                categoryJoin.join("categoryGroup", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        // 1. Filter by Category Group
        if (categoryGroupId != null) {
            predicates.add(cb.equal(categoryGroupJoin.get("id"), categoryGroupId));
        }

        // 2. Filter by Categories
        if (categoryIds != null && !categoryIds.isEmpty()) {
            predicates.add(categoryJoin.get("id").in(categoryIds));
        }

        // 3. Filter by Attributes
        attributes.forEach((attributeId, attributeValueIds) -> {
            // 1. Join with productVariants
            Join<Product, ProductVariant> productVariantJoin =
                    product.join("productVariants", JoinType.LEFT);
            // 2. Join with attributeValues
            Join<ProductVariant, AttributeValue> attributeValueJoin =
                    productVariantJoin.join("attributeValues", JoinType.LEFT);
            // 3. Join with attribute
            Join<AttributeValue, Attribute> attributeJoin = attributeValueJoin.join("attribute");

            Predicate attributePredicate = cb.and(cb.equal(attributeJoin.get("id"), attributeId),
                    attributeValueJoin.get("id").in(attributeValueIds));
            predicates.add(attributePredicate);
        });

        // Apply filters
        query.select(product).where(cb.and(predicates.toArray(new Predicate[0])));

        // Sorting logic
        List<Order> orderList = new ArrayList<>();
        if ("price_asc".equalsIgnoreCase(sortDirection)) {
            // 1. Sorting by lowest price
            Subquery<Double> minPriceSubquery = query.subquery(Double.class);
            Root<ProductVariant> productVariantSubRoot =
                    minPriceSubquery.from(ProductVariant.class);
            minPriceSubquery.select(cb.min(productVariantSubRoot.get("price")))
                    .where(cb.equal(productVariantSubRoot.get("product"), product));
            orderList.add(cb.asc(minPriceSubquery));
        } else if ("price_desc".equalsIgnoreCase(sortDirection)) {
            // 2. Sorting by highest price
            Subquery<Double> maxPriceSubquery = query.subquery(Double.class);
            Root<ProductVariant> productVariantSubRoot =
                    maxPriceSubquery.from(ProductVariant.class);
            maxPriceSubquery.select(cb.min(productVariantSubRoot.get("price")))
                    .where(cb.equal(productVariantSubRoot.get("product"), product));
            orderList.add(cb.desc(maxPriceSubquery));
        } else if ("latest".equalsIgnoreCase(sortDirection)) {
            // 3. Sorting by newest (assuming p.id or p.createdAt determines the newest)
            orderList.add(cb.desc(product.get("id"))); // it can be "createdAt" if available
        } else {
            // 4. Sorting by outstanding (by sold)
            orderList.add(cb.desc(product.get("sold")));
        }

        query.orderBy(orderList);

        Integer pageSize = 10;

        TypedQuery<Product> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageNumber * pageSize); // Start position
        typedQuery.setMaxResults(pageSize); // Number of results per page

        // Execute the query with pagination
        return typedQuery.getResultList();
    }

}
