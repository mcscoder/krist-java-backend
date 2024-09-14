package com.krist.service.product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.krist.dto.attribute.AttributeWithAttributeValuesDto;
import com.krist.dto.product.PostProductRequestDto;
import com.krist.dto.product.ProductDetailsDto;
import com.krist.dto.product.ProductDto;
import com.krist.dto.product.ProductOverviewDto;
import com.krist.dto.product.ProductOverviewListDto;
import com.krist.entity.attribute.Attribute;
import com.krist.entity.attribute.AttributeValue;
import com.krist.entity.category.Category;
import com.krist.entity.category.CategoryGroup;
import com.krist.entity.common.Image;
import com.krist.entity.product.Product;
import com.krist.entity.product.ProductVariant;
import com.krist.exception.custom.NotFoundException;
import com.krist.mapper.product.ProductMapper;
import com.krist.repository.product.ProductRepository;
import com.krist.service.attribute.AttributeService;

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
public class ProductService implements ProductMapper {
    private final ProductRepository productRepository;
    private final EntityManager entityManager;
    private final AttributeService attributeService;

    public ProductService(ProductRepository productRepository, EntityManager entityManager,
            AttributeService attributeService) {
        this.productRepository = productRepository;
        this.entityManager = entityManager;
        this.attributeService = attributeService;
    };

    public Product postProduct(PostProductRequestDto dto) {
        Set<Image> images = new HashSet<>();
        Category category = new Category(dto.categoryId());

        for (Long imageId : dto.imageIds()) {
            images.add(new Image(imageId));
        }

        Product product =
                new Product(dto.name(), dto.title(), dto.description(), 0, images, category);

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

    public ProductOverviewListDto getBestSellers() {
        return getProductOverviewListByFilters(null, null, null, null, 0, 8);
    }

    public ProductOverviewListDto getProductOverviewListByFilters(Long categoryGroupId,
            List<Long> categoryIds, Map<Long, List<Long>> attributes, String sortDirection,
            Integer pageNumber, Integer pageSize) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // Using JPA Projection
        CriteriaQuery<ProductOverviewDto> query = cb.createQuery(ProductOverviewDto.class);
        Root<Product> product = query.from(Product.class);

        // 1. Join with category
        Join<Product, Category> categoryJoin = product.join("category", JoinType.LEFT);
        // 2. Join with categoryGroup
        Join<Category, CategoryGroup> categoryGroupJoin =
                categoryJoin.join("categoryGroup", JoinType.LEFT);
        // 3. Join with images
        Join<Product, Image> imageJoin = product.join("images", JoinType.LEFT);

        // List to hold dynamic predicates for filtering
        List<Predicate> predicates = new ArrayList<>();

        // 1. Filter by Category Group
        if (categoryGroupId != null) {
            predicates.add(cb.equal(categoryGroupJoin.get("id"), categoryGroupId));
        }

        // 2. Filter by Categories
        if (categoryIds != null && !categoryIds.isEmpty()) {
            predicates.add(cb.equal(categoryJoin.get("id"), categoryIds.get(0)));
            Logger.getGlobal().info(categoryIds.get(0).toString());
            // predicates.add(categoryJoin.get("id").in(categoryIds));
        }

        // 3. Filter by Attributes and their corresponding values
        if (attributes != null) {
            attributes.forEach((attributeId, attributeValueIds) -> {
                // 1. Join with productVariants
                Join<Product, ProductVariant> productVariantJoin =
                        product.join("productVariants", JoinType.LEFT);
                // 2. Join with attributeValues
                Join<ProductVariant, AttributeValue> attributeValueJoin =
                        productVariantJoin.join("attributeValues", JoinType.LEFT);
                // 3. Join with attribute
                Join<AttributeValue, Attribute> attributeJoin =
                        attributeValueJoin.join("attribute");

                // Predicate to filter by specific attribute and its values
                Predicate attributePredicate =
                        cb.and(cb.equal(attributeJoin.get("id"), attributeId),
                                attributeValueJoin.get("id").in(attributeValueIds));
                predicates.add(attributePredicate);
            });
        }

        // Subquery to calculate the lowest price among all products variants
        Subquery<Double> lowestPriceSubQuery = query.subquery(Double.class);
        Root<ProductVariant> productVariantSubRoot = lowestPriceSubQuery.from(ProductVariant.class);
        lowestPriceSubQuery.select(cb.min(productVariantSubRoot.get("price")))
                .where(cb.equal(productVariantSubRoot.get("product"), product));

        // Construct the main query to select specific fields required by the DTO
        query.distinct(true).select(cb.construct(ProductOverviewDto.class, product.get("id"),
                product.get("name"), product.get("title"), product.get("description"),
                product.get("sold"), imageJoin.get("src"), lowestPriceSubQuery.getSelection()))
                .where(cb.and(predicates.toArray(new Predicate[0])));

        // Sorting logic based on provided sort direction
        List<Order> orderList = new ArrayList<>();
        if ("price_asc".equalsIgnoreCase(sortDirection)) {
            // 1. Sorting by lowest price first
            orderList.add(cb.asc(lowestPriceSubQuery));
        } else if ("price_desc".equalsIgnoreCase(sortDirection)) {
            // 2. Sorting by highest price first
            orderList.add(cb.desc(lowestPriceSubQuery));
        } else if ("latest".equalsIgnoreCase(sortDirection)) {
            // 3. Sorting by newest (assuming p.id or p.createdAt determines the newest)
            orderList.add(cb.desc(product.get("id"))); // it can be "createdAt" if available
        } else {
            // Default case set by controller
            // 4. Sorting by outstanding (by sold)
            orderList.add(cb.desc(product.get("sold")));
        }

        // Add a default sorting order to ensure consistent results
        orderList.add(cb.asc(product.get("id"))); // Default order by product ID
        query.orderBy(orderList);

        TypedQuery<ProductOverviewDto> typedQuery = entityManager.createQuery(query);

        // Get total count of filtered items (for pagination purpose)
        Integer numberOfItems = typedQuery.getResultList().size();

        // Get the total of page that needs to contain all of items
        Integer maxPageNumber = (numberOfItems - 1) / pageSize;

        // Limit the item will be returned
        typedQuery.setFirstResult(pageNumber * pageSize); // Start position
        typedQuery.setMaxResults(pageSize); // Number of results per page

        // Execute the query with pagination
        return new ProductOverviewListDto(typedQuery.getResultList(), maxPageNumber, pageNumber,
                pageSize, numberOfItems);
    }

    public ProductDetailsDto getProductDetailsByProductId(Long productId) {
        Product product = getProduct(productId);
        List<AttributeWithAttributeValuesDto> attributes =
                attributeService.getAttributesWithAttributeValuesByProduct(productId);
        ProductDetailsDto productDetails =
                ProductMapper.INSTANCE.toProductDetailsDto(product, attributes);

        return productDetails;
    }

    public ProductOverviewListDto getRelatedProductsByCategory(Long categoryId) {
        return getProductOverviewListByFilters(null, List.of(categoryId), null, null, 0, 4);
    }

    @Override
    public ProductDto toProductDto(Product product) {
        return INSTANCE.toProductDto(product);
    }
}
