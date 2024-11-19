package com.homework.rewardpoints.reward.service;

import com.homework.rewardpoints.reward.dto.request.CategoryRequest;
import com.homework.rewardpoints.reward.dto.response.CategoryResponse;
import com.homework.rewardpoints.reward.exception.DatabaseOperationException;
import com.homework.rewardpoints.reward.model.Category;
import com.homework.rewardpoints.reward.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryResponse createCategory(CategoryRequest categoryRequest){
        Category category = Category.builder()
                .categoryName(categoryRequest.getCategoryName())
                .build();
        try{
            categoryRepository.save(category);
        }catch (Exception e){
            log.error("Error saving category: {}", e.getMessage());
            throw new DatabaseOperationException("Failed to save category to the database", e);
        }
        log.info("Category created successfully");
        return category.toCategoryResponse();
    }

    public List<CategoryResponse> getAllCategories(){
        return categoryRepository.findAll()
                .stream()
                .map(Category::toCategoryResponse)
                .collect(Collectors.toList());
    }

    public Set<CategoryResponse> addCategoryInBulk(List<CategoryRequest> categoryRequests){
        Set<Category> categories = new HashSet<>();
        for(CategoryRequest categoryRequest : categoryRequests){
            Category category = Category.builder()
                    .categoryName(categoryRequest.getCategoryName())
                    .build();
            categories.add(category);
        }
        categoryRepository.saveAll(categories);
        log.info("Categories added successfully");
        return categories.stream().map(Category::toCategoryResponse).collect(Collectors.toSet());
    }

}
