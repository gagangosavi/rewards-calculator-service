package com.homework.rewardpoints.reward.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.rewardpoints.reward.dto.request.CategoryRequest;
import com.homework.rewardpoints.reward.dto.request.ProductRequest;
import com.homework.rewardpoints.reward.model.Category;
import com.homework.rewardpoints.reward.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;
    @BeforeEach
    void setup() {
        categoryRepository.deleteAll();
        Category electronics = Category.builder()
                .categoryName("Beauty Products")
                .build();
        Category category1 = categoryRepository.save(electronics);
        Category clothing = Category.builder()
                .categoryName("Clothing")
                .build();
        Category category2 = categoryRepository.save(clothing);
    }


    @Test
    public void testAddProduct() throws Exception {
        ProductRequest productRequest = new ProductRequest("Shampoo", 1000.0, CategoryRequest.builder().categoryName("Beauty Products").build());

        mockMvc.perform(post("/api/product/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productName").value("Shampoo"))
                .andExpect(jsonPath("$.price").value(1000.0));
    }

    @Test
    public void testAddProductValidationError() throws Exception {
        ProductRequest invalidRequest = new ProductRequest("", -1.0,null);

        mockMvc.perform(post("/api/product/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.productName").value("Product name must contain minimum of 1 and maximum of 100 characters"))
                .andExpect(jsonPath("$.errors.price").value("Price must be a positive value"))
                .andExpect(jsonPath("$.errors.categoryRequest").value("Category cannot be null"));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/api/product/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").isNumber());
    }

    @Test
    public void testAddProductsInBulk() throws Exception {
        List<ProductRequest> productRequests = Arrays.asList(
                new ProductRequest("Shirt", 1000.0,CategoryRequest.builder().categoryName("Clothing").build()),
                new ProductRequest("Shampoo", 500.0,CategoryRequest.builder().categoryName("Beauty Products").build())
        );

        mockMvc.perform(post("/api/product/bulk-add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequests)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].productName").value("Shampoo"))
                .andExpect(jsonPath("$[1].productName").value("Shirt"));
    }
}
