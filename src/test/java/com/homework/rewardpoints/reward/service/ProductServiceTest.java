package com.homework.rewardpoints.reward.service;
import com.homework.rewardpoints.reward.dto.request.CategoryRequest;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ProductServiceTest {
    @InjectMocks
    private  ProductService productService;

    @Mock
    private  ProductRepository productRepository;

    @Mock
    private  CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct_validRequest() {
        ProductRequest request = ProductRequest.builder()
                .productName("Laptop")
                .price(1000.0)
                .categoryRequest(CategoryRequest.builder().categoryName("Electronics").build())
                .build();

        Category category = Category.builder().categoryId(1L).categoryName("Electronics").build();
        Product savedProduct = Product.builder().productName("Laptop").price(1000.0).category(category).build();

        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductResponse response = productService.addProduct(request);

        assertEquals("Laptop", response.getProductName());
        assertEquals(1000, response.getPrice());
        verify(categoryRepository, times(1)).findByCategoryName("Electronics");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testAddProduct_invalidPrice() {
        Category category = Category.builder().categoryId(1L).categoryName("Electronics").build();
        CategoryRequest categoryRequest = CategoryRequest.builder().categoryName("Electronics").build();
        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(Optional.of(category));
        ProductRequest request = ProductRequest.builder()
                .productName("Laptop")
                .price(-100.0)
                .categoryRequest(categoryRequest)
                .build();

        assertThrows(InvalidProductRequestException.class, () -> productService.addProduct(request));
        verifyNoInteractions(productRepository);
    }

    @Test
    void testAddProduct_categoryNotFound() {
        ProductRequest request = ProductRequest.builder()
                .productName("Laptop")
                .price(1000.0)
                .categoryRequest(CategoryRequest.builder().categoryName("Electronics").build())
                .build();

        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> productService.addProduct(request));
        verify(categoryRepository, times(1)).findByCategoryName("Electronics");
        verifyNoInteractions(productRepository);
    }

    @Test
    void testAddProduct_throwsDatabaseOperationException() {
        ProductRequest request = ProductRequest.builder()
                .productName("Laptop")
                .price(1000.0)
                .categoryRequest(CategoryRequest.builder().categoryName("Electronics").build())
                .build();

        Category category = Category.builder().categoryId(1L).categoryName("Electronics").build();

        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(DatabaseOperationException.class, () -> productService.addProduct(request));
        verify(categoryRepository, times(1)).findByCategoryName("Electronics");
        verify(productRepository, times(1)).save(any(Product.class));
    }
}
