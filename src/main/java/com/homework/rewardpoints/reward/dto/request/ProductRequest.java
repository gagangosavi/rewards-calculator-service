package com.homework.rewardpoints.reward.dto.request;

import com.homework.rewardpoints.reward.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String productName;
    private Integer price;
    private Category category;
}
