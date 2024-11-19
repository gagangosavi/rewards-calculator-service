package com.homework.rewardpoints.reward.controller;

import com.homework.rewardpoints.reward.dto.request.CategoryRequest;
import com.homework.rewardpoints.reward.dto.response.CategoryResponse;
import com.homework.rewardpoints.reward.model.Category;
import com.homework.rewardpoints.reward.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest){
        return categoryService.createCategory(categoryRequest);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PostMapping("/bulk-add")
    @ResponseStatus(HttpStatus.CREATED)
    public Set<CategoryResponse> addCategoryInBulk(@RequestBody List<CategoryRequest> categoryRequests){
        return categoryService.addCategoryInBulk(categoryRequests);
    }
}
