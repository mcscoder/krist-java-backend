package com.krist.repository.product;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.krist.entity.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByOrderBySoldDesc(Pageable pageable);

    // @Query("""
    //         SELECT DISTINCT p FROM Product p
    //         JOIN p.categories c
    //         JOIN c.categoryGroup cg
    //         JOIN p.productVariants pr
    //         JOIN pr.attributeValues av
    //         WHERE cg.id = :categoryGroupId
    //         AND (:categoryIds IS NULL OR c.id IN :categoryIds)
    //         AND (:attributeValueIds IS NULL OR av.id IN :attributeValueIds)
    //         ORDER BY
    //             CASE WHEN :sortDirection = 'price_asc' THEN
    //                 (SELECT MIN(variant.price) FROM ProductVariant variant WHERE variant.product = p)
    //             END ASC,
    //             CASE WHEN :sortDirection = 'price_desc' THEN
    //                 (SELECT MIN(variant.price) FROM ProductVariant variant WHERE variant.product = p)
    //             END DESC,
    //             CASE WHEN :sortDirection = 'latest' THEN p.id END DESC,
    //             CASE WHEN :sortDirection = 'outstanding' THEN p.sold END DESC,
    //             p.id ASC
    //         """)
    // List<Product> findProductsByFilters(@Param("categoryGroupId") Long categoryGroupId,
    //         @Param("categoryIds") List<Long> categoryIds,
    //         @Param("attributeValueIds") List<Long> attributeValueIds,
    //         @Param("sortDirection") String sortDirection);
}
