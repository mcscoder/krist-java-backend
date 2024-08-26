package com.krist.service.product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.krist.dto.product.PostProductRequestDto;
import com.krist.entity.common.Image;
import com.krist.entity.product.Category;
import com.krist.entity.product.Product;
import com.krist.exception.custom.NotFoundException;
import com.krist.repository.product.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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

        Product product =
                new Product(dto.name(), dto.title(), dto.description(), 0, images, categories, null);

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

    public List<Product> getProductsByCategoryGroup(Long categoryGroupId) {
        return productRepository.findProductsByCategoryGroupId(categoryGroupId);
    }
}
