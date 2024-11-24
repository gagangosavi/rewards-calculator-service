package com.homework.rewardpoints.reward.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @NotNull(message = "Category name cannot be null")
    @NotBlank(message = "Category name cannot be blank")
    private String categoryName;
}
