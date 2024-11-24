package com.homework.rewardpoints.reward.controller;

import com.homework.rewardpoints.reward.dto.request.CategoryRequest;
import com.homework.rewardpoints.reward.dto.response.CategoryResponse;
import com.homework.rewardpoints.reward.model.Category;
import com.homework.rewardpoints.reward.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public CategoryResponse createCategory(@RequestBody
                                               @Valid CategoryRequest categoryRequest){
        return categoryService.createCategory(categoryRequest);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PostMapping("/bulk-add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addCategoryInBulk(@RequestBody
                                                       List<CategoryRequest> categoryRequests){
        if(categoryRequests.isEmpty()){
            return new ResponseEntity<>("Please provide at least one category to be added",HttpStatus.BAD_REQUEST);
        }
        Set<CategoryResponse> categoryResponses = categoryService.addCategoryInBulk(categoryRequests);
        return new  ResponseEntity<Set<CategoryResponse>>(categoryResponses,HttpStatus.CREATED);
    }
}
