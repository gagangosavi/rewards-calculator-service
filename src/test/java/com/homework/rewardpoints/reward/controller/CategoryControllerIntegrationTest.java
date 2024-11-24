package com.homework.rewardpoints.reward.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.rewardpoints.reward.dto.request.CategoryRequest;
import com.homework.rewardpoints.reward.model.Category;
import com.homework.rewardpoints.reward.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp(){
        categoryRepository.deleteAll();
    }

    @Test
    public void testCreateCategory() throws Exception {
        // Given
        CategoryRequest categoryRequest = new CategoryRequest("Electronics");

        // When
        mockMvc.perform(post("/api/category/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryName").value("Electronics"));
    }

    @Test
    public void testCreateCategoryValidationError() throws Exception {
        // Given
        CategoryRequest invalidRequest = new CategoryRequest(""); // Invalid input

        // When
        mockMvc.perform(post("/api/category/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.categoryName").value("Category name cannot be blank"));
    }

    @Test
    public void testGetAllCategories() throws Exception {
        // When
        mockMvc.perform(get("/api/category/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").isNumber()); // Ensures a list is returned
    }

    @Test
    public void testAddCategoriesInBulk() throws Exception {
        // Given
        List<CategoryRequest> categoryRequests = Arrays.asList(
                new CategoryRequest("Books"),
                new CategoryRequest("Clothing")
        );

        // When
        mockMvc.perform(post("/api/category/bulk-add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequests)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].categoryName").value("Books"))
                .andExpect(jsonPath("$[1].categoryName").value("Clothing"));
    }
}

