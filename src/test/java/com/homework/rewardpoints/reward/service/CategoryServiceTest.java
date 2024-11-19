package com.homework.rewardpoints.reward.service;

import com.homework.rewardpoints.reward.dto.request.CategoryRequest;
import com.homework.rewardpoints.reward.dto.response.CategoryResponse;
import com.homework.rewardpoints.reward.exception.DatabaseOperationException;
import com.homework.rewardpoints.reward.model.Category;
import com.homework.rewardpoints.reward.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory() {
        CategoryRequest request = CategoryRequest.builder().categoryName("Electronics").build();
        Category category = Category.builder().categoryName("Electronics").build();

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponse response = categoryService.createCategory(request);

        assertEquals("Electronics", response.getCategoryName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testGetAllCategories() {
        List<Category> categories = Arrays.asList(
                Category.builder().categoryName("Electronics").build(),
                Category.builder().categoryName("Clothing").build()
        );

        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryResponse> responses = categoryService.getAllCategories();

        assertEquals(2, responses.size());
        assertEquals("Electronics", responses.get(0).getCategoryName());
        assertEquals("Clothing", responses.get(1).getCategoryName());
    }

    @Test
    void testCreateCategory_throwsDatabaseOperationException() {
        CategoryRequest request = CategoryRequest.builder().categoryName("Electronics").build();

        when(categoryRepository.save(any(Category.class))).thenThrow(new RuntimeException("Database error"));

        try {
            categoryService.createCategory(request);
        } catch (DatabaseOperationException e) {
            assertEquals("Failed to save category to the database", e.getMessage());
        }

        verify(categoryRepository, times(1)).save(any(Category.class));
    }
}
