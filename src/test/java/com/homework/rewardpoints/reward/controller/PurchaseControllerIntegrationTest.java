package com.homework.rewardpoints.reward.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.rewardpoints.reward.dto.request.PurchaseRequest;
import com.homework.rewardpoints.reward.dto.request.ProductRequest;
import com.homework.rewardpoints.reward.dto.request.CustomerRequest;
import com.homework.rewardpoints.reward.model.Category;
import com.homework.rewardpoints.reward.model.Customer;
import com.homework.rewardpoints.reward.model.Product;
import com.homework.rewardpoints.reward.repository.CategoryRepository;
import com.homework.rewardpoints.reward.repository.CustomerRepository;
import com.homework.rewardpoints.reward.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    Category category1;

    Product product1;

    Customer customer1;
    @BeforeEach
    void setup() {
        customerRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        Category category = Category.builder()
                .categoryName("Electronics")
                .build();
        category1 = categoryRepository.save(category);

        Product product = Product.builder()
                .price(1000.0)
                .productName("Laptop")
                .category(category1)
                .build();
        product1 = productRepository.save(product);



        Customer customer = Customer.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .contact("1234567890")
                .build();
        customer1 = customerRepository.save(customer);

    }

    @Test
    public void testMakePurchase() throws Exception {
        PurchaseRequest purchaseRequest = new PurchaseRequest( List.of(product1), customer1, LocalDateTime.parse("2024-10-15T12:00:00"));

        mockMvc.perform(post("/api/purchase/makePurchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(purchaseRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.purchaseAmount").value(1000.0))
                .andExpect(jsonPath("$.totalRewardPoints").isNumber());
    }

    @Test
    public void testGetAllPurchases() throws Exception {
        mockMvc.perform(get("/api/purchase/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").isNumber());
    }
}
