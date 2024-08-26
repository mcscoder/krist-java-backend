package com.krist.repository.product;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.krist.entity.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByOrderBySoldDesc(Pageable pageable);

    // Reference docs:
    // https://www.baeldung.com/jpa-join-types#2-explicit-inner-join-with-single-valued-association
    @Query("SELECT p FROM Product p JOIN p.categories c JOIN c.categoryGroup cg WHERE cg.id = :categoryGroupId")
    List<Product> findProductsByCategoryGroupId(@Param("categoryGroupId") Long categoryGroupId);

}
