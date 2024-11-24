package com.homework.rewardpoints.reward.dto.request;

import com.homework.rewardpoints.reward.model.Category;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotNull(message = "Product Name cannot be null")
    @Size(min = 1,max = 100, message = "Product name must contain minimum of 1 and maximum of 100 characters")
    private String productName;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be a positive value")
    private Double price;

    @NotNull(message = "Category cannot be null")
    private CategoryRequest categoryRequest;
}
