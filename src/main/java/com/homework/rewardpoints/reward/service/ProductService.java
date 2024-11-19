package com.homework.rewardpoints.reward.service;

import com.homework.rewardpoints.reward.dto.request.ProductRequest;
import com.homework.rewardpoints.reward.dto.response.ProductResponse;
import com.homework.rewardpoints.reward.exception.DatabaseOperationException;
import com.homework.rewardpoints.reward.exception.category.CategoryNotFoundException;
import com.homework.rewardpoints.reward.exception.product.InvalidProductRequestException;
import com.homework.rewardpoints.reward.model.Category;
import com.homework.rewardpoints.reward.model.Product;
import com.homework.rewardpoints.reward.repository.CategoryRepository;
import com.homework.rewardpoints.reward.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    public ProductResponse addProduct(ProductRequest productRequest){
        Category category = categoryRepository.findById(productRequest.getCategory().getCategoryId()).orElseThrow(() ->new CategoryNotFoundException("No such category"));
        if(productRequest.getPrice() < 0.0){
            throw new InvalidProductRequestException("Price cannot be negative");
        }
        Product product = Product.builder()
                .productName(productRequest.getProductName())
                .price(productRequest.getPrice())
                .category(category)
                .build();

        try{
            productRepository.save(product);
        }catch (Exception e){
            log.error("Error saving product: {}", e.getMessage());
            throw new DatabaseOperationException("Failed to save product to the database", e);
        }
        log.info("Product added successfully");
        return product.toProductResponse();
    }

    public List<ProductResponse> getAllProducts(){
        return productRepository.findAll()
                .stream()
                .map(Product::toProductResponse)
                .collect(Collectors.toList());
    }

    public Set<ProductResponse> addProductsInBulk(List<ProductRequest> productRequests){
        Set<Product> products = new HashSet<>();
        for(ProductRequest productRequest : productRequests){
            Category category = categoryRepository.findById(productRequest.getCategory().getCategoryId()).orElseThrow(() ->new CategoryNotFoundException("No such category"));
            if(productRequest.getPrice() < 0.0){
                throw new InvalidProductRequestException("Price cannot be negative");
            }
            Product product = Product.builder()
                    .productName(productRequest.getProductName())
                    .price(productRequest.getPrice())
                    .category(category)
                    .build();
            products.add(product);
        }
        productRepository.saveAll(products);
        log.info("Products created successfully");
        return products.stream().map(Product::toProductResponse).collect(Collectors.toSet());
    }
}
